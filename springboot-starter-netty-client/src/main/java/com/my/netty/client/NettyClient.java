package com.my.netty.client;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.my.netty.client.handler.NettyClientRpcHandler;
import com.my.netty.clinet.config.NettyClientBean;
import com.my.netty.model.ClassMsgBean;
import com.my.netty.model.NettyRequest;
import com.my.netty.model.ResultFuture;
import com.my.netty.zookeeper.utils.ZookeeperUtils;
import com.my.netty.zookeeper.utils.ZookeeperUtils.CallBack;
import com.my.netty.zookeeper.utils.ZookeeperUtils.ServerInfo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyClient
{
    public static final String NETTY_MSG_END = "\r\n";
    
    private static final Logger LOG = LoggerFactory.getLogger(NettyClient.class);
    
    // private ChannelFuture channelFuture; // (5)
    private static final Map<String, List<ChannelFutureWarp>> channelFutureMap = new ConcurrentHashMap<>();
    
    private CuratorFramework curator;
    private Bootstrap bootstrap;
    // private NettyClientBean nettyClientBean;
    // private ApplicationContext context;
    
    public NettyClient(CuratorFramework curator, NettyClientBean nettyClientBean, ApplicationContext context)
    {
        // Assert.notNull(nettyClientBean, "nettyClientBean must be not null.");
        // Assert.notNull(context, "ApplicationContext must be not null.");
        Assert.notNull(curator, "CuratorFramework must be not null.");
        
        // this.context = context;
        // this.nettyClientBean = nettyClientBean;
        this.curator = curator;
        
        init();
    }
    
    private class MyChannelInitializer extends ChannelInitializer<SocketChannel>
    {
        @Override // (4)
        public void initChannel(SocketChannel ch) throws Exception
        {
            Charset charset = Charset.forName("UTF-8");
            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(65535, Delimiters.lineDelimiter()[0]));
            ch.pipeline().addLast(new StringDecoder(charset));
            ch.pipeline().addLast(new NettyClientRpcHandler(bootstrap, new MyNettyCallBack()));
            ch.pipeline().addLast(new StringEncoder(charset));
        }
    }
    
    // @PostConstruct
    private void init()
    {
        
        Assert.isNull(bootstrap, "bootstrap has ready.");
        
        // String host = nettyClientBean.getServerHost();
        // int port = nettyClientBean.getServerPort();
        
        bootstrap = new Bootstrap(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        
        try
        {
            bootstrap.group(workerGroup); // (2)
            bootstrap.option(ChannelOption.SO_KEEPALIVE, false); // (4)
            bootstrap.channel(NioSocketChannel.class); // (3)
            bootstrap.handler(new MyChannelInitializer());
            
            // Start the client.
            // channelFuture = bootstrap.connect(host, port).sync(); // (5)
            
            Map<String, List<ServerInfo>> allServerInfo = ZookeeperUtils.discoverAllServerInfo(curator,
                    new MyZookeeperCallBack());
            
            LOG.info("【--------------intiChannelFuture begin. 】");
            
            intiChannelFuture(bootstrap, allServerInfo);
            
            LOG.info("【--------------intiChannelFuture end. 】");
            
            LOG.info("【--------------netty-client ready success. 】");
            // LOG.info("【netty-client ready success. port:{} ,host:{}】", port,
            // host);
            
        }
        catch (Exception e)
        {
            LOG.error("", e);
        }
        
    }
    
    private void intiChannelFuture(Bootstrap bootstrap, Map<String, List<ServerInfo>> allServerInfo) throws Exception
    {
        LOG.info("【--------------allServerInfo : \n{}\n】", JSONObject.toJSONString(allServerInfo, true));
        
        for (Entry<String, List<ServerInfo>> entry : allServerInfo.entrySet())
        {
            String name = entry.getKey();
            List<ServerInfo> serverInfoList = entry.getValue();
            
            List<ChannelFutureWarp> channelFutureList = channelFutureMap.get(name) == null ? new ArrayList<>()
                    : channelFutureMap.get(name);
            
            for (ServerInfo serverInfo : serverInfoList)
            {
                String host = serverInfo.getHost();
                Integer port = serverInfo.getPort();
                String id = ChannelFutureWarp.buildId(host, port);
                
                if (repeatDiscover(id, channelFutureList))
                {
                    LOG.info("【--------------repeatDiscover.{}】", id);
                    continue;
                }
                
                ChannelFuture channelFuture = bootstrap.connect(serverInfo.getHost(), serverInfo.getPort()).sync(); // (5);
                
                ChannelFutureWarp channelFutureWarp = new ChannelFutureWarp(id, name, host, port, channelFuture);
                channelFutureList.add(channelFutureWarp);
                
                LOG.info("【--------------addNewDiscover.{}】", id);
            }
            
            channelFutureMap.put(name, channelFutureList);
        }
    }
    
    private ChannelFutureWarp findChannelFutureWarp(String id, List<ChannelFutureWarp> channelFutureWarpList)
    {
        if (null == channelFutureWarpList)
        {
            return null;
        }
        
        for (ChannelFutureWarp channelFutureWarp : channelFutureWarpList)
        {
            if (channelFutureWarp.getId().equals(id))
            {
                return channelFutureWarp;
            }
        }
        
        return null;
    }
    
    private boolean repeatDiscover(String id, List<ChannelFutureWarp> channelFutureWarpList)
    {
        return findChannelFutureWarp(id, channelFutureWarpList) != null;
    }
    
    private class MyNettyCallBack implements com.my.netty.client.handler.NettyClientRpcHandler.CallBack
    {
        
        @Override
        public boolean isReConnect(String host, Integer port)
        {
            String id = ChannelFutureWarp.buildId(host, port);
            
            Set<Entry<String, List<ChannelFutureWarp>>> entrySet = channelFutureMap.entrySet();
            
            for (Entry<String, List<ChannelFutureWarp>> entry : entrySet)
            {
                List<ChannelFutureWarp> channelFutureWarpList = entry.getValue();
                
                ChannelFutureWarp channelFutureWarp = findChannelFutureWarp(id, channelFutureWarpList);
                
                if (null != channelFutureWarp)
                {
                    
                    return true;
                }
            }
            
            return false;
        }
        
        @Override
        public void reConnect(String host, Integer port, ChannelFuture channelFuture)
        {
            String id = ChannelFutureWarp.buildId(host, port);
            
            Set<Entry<String, List<ChannelFutureWarp>>> entrySet = channelFutureMap.entrySet();
            
            for (Entry<String, List<ChannelFutureWarp>> entry : entrySet)
            {
                List<ChannelFutureWarp> channelFutureWarpList = entry.getValue();
                
                ChannelFutureWarp channelFutureWarp = findChannelFutureWarp(id, channelFutureWarpList);
                
                if (null != channelFutureWarp)
                {
                    LOG.info("【--------------channelFuture update.{}】", id);
                    
                    channelFutureWarp.setChannelFuture(channelFuture);
                    
                    return;
                }
            }
        }
        
        @Override
        public void removeConnect(String host, Integer port)
        {
            String id = ChannelFutureWarp.buildId(host, port);
            
            Set<Entry<String, List<ChannelFutureWarp>>> entrySet = channelFutureMap.entrySet();
            
            for (Entry<String, List<ChannelFutureWarp>> entry : entrySet)
            {
                List<ChannelFutureWarp> channelFutureWarpList = entry.getValue();
                
                ChannelFutureWarp channelFutureWarp = findChannelFutureWarp(id, channelFutureWarpList);
                
                if (null != channelFutureWarp)
                {
                    LOG.info("【--------------channelFuture removed.{}】", id);
                    channelFutureWarpList.remove(channelFutureWarp);
                    return;
                }
            }
        }
        
    }
    
    private class MyZookeeperCallBack implements CallBack
    {
        
        @Override
        public void doBack(Map<String, List<ServerInfo>> allServerInfo)
        {
            try
            {
                LOG.info("【--------------update allServerInfo begin.】");
                
                intiChannelFuture(bootstrap, allServerInfo);
                
                LOG.info("【--------------update allServerInfo end.】");
            }
            catch (Exception e)
            {
                LOG.error("", e);
            }
        }
    }
    
    @SuppressWarnings("unused")
    private static class ChannelFutureWarp
    {
        String id;
        String applicationName;
        String host;
        Integer port;
        private ChannelFuture channelFuture;
        
        public ChannelFutureWarp(String id, String applicationName, String host, Integer port,
                ChannelFuture channelFuture)
        {
            this.id = id;
            this.applicationName = applicationName;
            this.host = host;
            this.port = port;
            this.channelFuture = channelFuture;
        }
        
        public static String buildId(String host, Integer port)
        {
            return host + ":" + port;
        }
        
        public String getId()
        {
            return id;
        }
        
        public String getApplicationName()
        {
            return applicationName;
        }
        
        public void setApplicationName(String applicationName)
        {
            this.applicationName = applicationName;
        }
        
        public String getHost()
        {
            return host;
        }
        
        public void setHost(String host)
        {
            this.host = host;
        }
        
        public Integer getPort()
        {
            return port;
        }
        
        public void setPort(Integer port)
        {
            this.port = port;
        }
        
        public void setId(String id)
        {
            this.id = id;
        }
        
        public ChannelFuture getChannelFuture()
        {
            return channelFuture;
        }
        
        public void setChannelFuture(ChannelFuture channelFuture)
        {
            this.channelFuture = channelFuture;
        }
        
        @Override
        public String toString()
        {
            return "ChannelFutureWarp [id=" + id + ", applicationName=" + applicationName + ", host=" + host + ", port="
                    + port + ", channelFuture=" + channelFuture + "]";
        }
        
    }
    
    // public <T, R> R send(T content)
    // {
    //
    // NettyRequest<T> request = new NettyRequest<>(content);
    // ResultFuture<R> resultFuture = new ResultFuture<>(request);
    //
    // String msgJson = JSONObject.toJSONString(request);
    // channelFuture.channel().writeAndFlush(msgJson + NETTY_MSG_END);
    //
    // // LOG.info("【netty-client send:{}】", msgJson);
    //
    // R result = resultFuture.get();
    //
    // LOG.info("【netty-client recieve:{}】", result);
    //
    // return result;
    // }
    
    public Object send(Class<?> target, Method method, Object[] args)
    {
        NettyRequest request = new NettyRequest();
        request.setClassMsgBean(new ClassMsgBean(target, method, args));
        
        String requestJson = JSONObject.toJSONString(request, true);
        
        ChannelFuture channelFuture = channelFutureMap.get("springboot-test-netty-server").get(0).getChannelFuture();// TODO
        channelFuture.channel().writeAndFlush(requestJson + NETTY_MSG_END);
        
        // LOG.info("【netty-client send:\n{}\n】", requestJson);
        
        ResultFuture resultFuture = new ResultFuture(request);
        Object content = resultFuture.get();
        
        Class<?> returnType = method.getReturnType();
        
        if (content instanceof JSONObject)
        {
            content = ((JSONObject) content).toJavaObject(returnType);
        }
        else if (content instanceof JSONArray)
        {
            // content = ((JSONArray) content).toJavaList(returnType);
        }
        else
        {
            // content = returnType.cast(content);
        }
        
        LOG.info("【netty-client recieve-content({}):\n{}\n】", channelFuture.channel().remoteAddress(),
                content + "   \n" + content.getClass().getName());
        
        return content;
    }
    
}
