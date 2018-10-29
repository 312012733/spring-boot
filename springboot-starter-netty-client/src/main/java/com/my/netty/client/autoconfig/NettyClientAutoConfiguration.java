package com.my.netty.client.autoconfig;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.my.netty.client.NettyClient;
import com.my.netty.clinet.config.NettyClientBean;

@Configuration
@EnableConfigurationProperties(NettyClientBean.class)
@ConditionalOnClass(NettyClient.class)
@ConditionalOnBean(CuratorFramework.class)
@AutoConfigureAfter(com.my.netty.zookeeper.autoconfig.CuratorAutoConfiguration.class)
public class NettyClientAutoConfiguration
{
    @Autowired
    private NettyClientBean nettyClientBean;
    // @Autowired
    // private ApplicationContext context;
    
    @Bean
    @ConditionalOnMissingBean
    public NettyClient nettyClient(CuratorFramework curator, ApplicationContext context)
    { 
        NettyClient nettyClient = new NettyClient(curator, nettyClientBean, context);
        
        return nettyClient;
    }
}
