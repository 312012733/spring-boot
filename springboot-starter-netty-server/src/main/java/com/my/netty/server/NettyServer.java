package com.my.netty.server;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.my.netty.server.config.NettyServerBean;
import com.my.netty.server.handler.NettyServerRpcHandler;
import com.my.netty.zookeeper.utils.ZookeeperUtils;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class NettyServer implements Runnable
{
    private static final Logger LOG = LoggerFactory.getLogger(NettyServer.class);
    
    private NettyServerBean nettyServerBean;
    
    private ChannelFuture channelFuture;
    
    private MyChannelInitializer myChannelInitializer;
    
    private CuratorFramework curator;
    
    public NettyServer(NettyServerBean nettyServerBean, MyChannelInitializer myChannelInitializer,
            CuratorFramework curator)
    {
        Assert.notNull(nettyServerBean, "nettyClientBean must be not null.");
        Assert.notNull(myChannelInitializer, "ChannelInitializer must be not null ");
        Assert.notNull(curator, "CuratorFramework must be not null ");
        
        this.curator = curator;
        this.nettyServerBean = nettyServerBean;
        this.myChannelInitializer = myChannelInitializer;
    }
    
    public static class MyChannelInitializer extends ChannelInitializer<SocketChannel>
    {
        private NettyServerRpcHandler nettyServerRpcHandler;
        
        public MyChannelInitializer(NettyServerRpcHandler nettyServerRpcHandler)
        {
            this.nettyServerRpcHandler = nettyServerRpcHandler;
        }
        
        @Override // (4)
        public void initChannel(SocketChannel ch) throws Exception
        {
            Charset charset = Charset.forName("UTF-8");
            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(65535, Delimiters.lineDelimiter()[0]));
            ch.pipeline().addLast(new StringDecoder(charset));
            ch.pipeline().addLast(new IdleStateHandler(30, 20, 10,TimeUnit.SECONDS));
            ch.pipeline().addLast(nettyServerRpcHandler);
            ch.pipeline().addLast(new StringEncoder(charset));
        }
    }
    
    @Override
    public void run()
    {
        int port = nettyServerBean.getPort();
        String applicationName = nettyServerBean.getName();
        
        Assert.isNull(channelFuture, "netty-server has started... port is " + port);
        
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        
        try
        {
            ServerBootstrap serverBootstrap = new ServerBootstrap(); // (2)
            
            serverBootstrap.group(bossGroup, workerGroup)//
                    .option(ChannelOption.SO_BACKLOG, 128) // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, false)// (6)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(myChannelInitializer);//
            
            channelFuture = serverBootstrap.bind(port).sync(); // (7)
            
            ZookeeperUtils.registServer(curator, applicationName, port);
            
            LOG.info("【--------------netty-server ready success. port:{}】", port);
            
            channelFuture.channel().closeFuture().sync();
            
            LOG.info("【--------------netty-server socket is shutdown . port:{}】");
        }
        catch (Exception e)
        {
            LOG.error("", e);
        }
        finally
        {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
    
}
