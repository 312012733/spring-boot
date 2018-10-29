package com.my.netty.server.autoconfig;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import com.my.netty.server.NettyServer;
import com.my.netty.server.NettyServer.MyChannelInitializer;
import com.my.netty.server.config.NettyServerBean;
import com.my.netty.server.handler.NettyServerRpcHandler;

@Configuration
@EnableConfigurationProperties(NettyServerBean.class)
@ConditionalOnClass(NettyServer.class)
@ConditionalOnBean(CuratorFramework.class)
@ConditionalOnProperty(prefix = "netty.server", name =
{ "port", "name" }, matchIfMissing = false)
@AutoConfigureAfter(com.my.netty.zookeeper.autoconfig.CuratorAutoConfiguration.class)
public class NettyServerAutoConfiguration
{
    @Autowired
    private NettyServerBean nettyServerBean;
    @Autowired
    private ApplicationContext context;
    
    @Bean
    @ConditionalOnMissingBean
    public NettyServer nettyServer(CuratorFramework client)
    {
        NettyServerRpcHandler nettyServerRpcHandle = new NettyServerRpcHandler(context);
        
        MyChannelInitializer myChannelInitializer = new MyChannelInitializer(nettyServerRpcHandle);
        
        NettyServer nettyServer = new NettyServer(nettyServerBean, myChannelInitializer, client);
        
        return nettyServer;
    }
    
    @Bean
    @ConditionalOnBean(NettyServer.class)
    public ApplicationListener<ContextRefreshedEvent> applicationListener(NettyServer nettyServer)
    {
        return new ApplicationListener<ContextRefreshedEvent>()
        {
            
            @Override
            public void onApplicationEvent(ContextRefreshedEvent event)
            {
                new Thread(nettyServer).start();
            }
        };
    }
    
}
