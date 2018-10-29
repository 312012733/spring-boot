package com.my.netty.client.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.my.netty.model.NettyResponse;
import com.my.netty.model.ResultFuture;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientRpcHandler extends ChannelInboundHandlerAdapter
{
    private static final Logger LOG = LoggerFactory.getLogger(NettyClientRpcHandler.class);
    
    private static final String HEART_BEAT = "heart-beat";
    
    public static final String NETTY_MSG_END = "\r\n";
    
    private Bootstrap bootstrap;
    private CallBack callBack;
    
    public NettyClientRpcHandler(Bootstrap bootstrap, CallBack callBack)
    {
        this.bootstrap = bootstrap;
        this.callBack = callBack;
    }
    
    public static interface CallBack
    {
        boolean isReConnect(String host, Integer port);
        
        void reConnect(String host, Integer port, ChannelFuture channelFuture);
        
        void removeConnect(String host, Integer port);
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        if (HEART_BEAT.equals(msg.toString()))
        {
            // ctx.channel().writeAndFlush(HEART_BEAT + NETTY_MSG_END);
            return;
        }
        
        LOG.info("【--------------netty-client receive({}):\n{}\n】 ", ctx.channel().remoteAddress(), msg);
        
        NettyResponse response = JSONObject.parseObject(msg.toString(), NettyResponse.class);
        ResultFuture.buildResult(response);
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        LOG.info("【--------------channelActive.{}】", ctx.channel().remoteAddress());
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception
    {
        if (ctx.channel().remoteAddress() == null)
        {
            LOG.error("【--------------channelInactive--ctx.channel.remoteAddress is null 】");
            return;
        }
        
        String remoteAddress = ctx.channel().remoteAddress().toString();
        LOG.info("【--------------channelInactive.{}】", ctx.channel().remoteAddress());
        
        String[] remoteAddressAry = remoteAddress.substring(1).split(":");
        String host = remoteAddressAry[0];
        Integer port = Integer.parseInt(remoteAddressAry[1]);
        
        // 开始重连
        if (!callBack.isReConnect(host, port))
        {
            LOG.info("【--------------isReConnect false.{}:{}】", host, port);
            return;
        }
        
        LOG.info("【--------------reConnect begin.{}:{}】", host, port);
        
        ChannelFuture channelFuture = bootstrap.connect(host, port);
        
        channelFuture.addListener(new ChannelFutureListener()
        {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception
            {
                if (future.isSuccess())
                {
                    LOG.info("【--------------reconnect success.{}:{}】", host, port);
                    callBack.reConnect(host, port, channelFuture);
                }
                else if (true)// TODO 重连次数
                {
                    LOG.info("【--------------reconnect fail.{}:{}】", host, port);
                    callBack.removeConnect(host, port);
                }
                // else
                // {
                // LOG.info("【--------------reconnect fail.{}:{}】", host, port);
                // channelFuture.channel().pipeline().fireChannelInactive();
                // }
            }
        });
        
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        String remoteAddress = ctx.channel().remoteAddress().toString();
        LOG.error("【--------------exceptionCaught." + remoteAddress + "】", cause);
        
        String[] remoteAddressAry = remoteAddress.substring(1).split(":");
        String host = remoteAddressAry[0];
        Integer port = Integer.parseInt(remoteAddressAry[1]);
        callBack.removeConnect(host, port);
        
        ctx.channel().close();
    }
}
