package com.my.netty.zookeeper.autoconfig;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.my.netty.zookeeper.config.ZookeeperBean;

@Configuration
@EnableConfigurationProperties(ZookeeperBean.class)
// @ConditionalOnClass(NettyServer.class)
@ConditionalOnProperty(prefix = "netty.server", name =
{ "zookeeper" }, matchIfMissing = false)
public class CuratorAutoConfiguration
{
    @Autowired
    private ZookeeperBean zookeeperBean;
    
    @Bean
    @ConditionalOnMissingBean
    public CuratorFramework curatorFramework()
    {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperBean.getZookeeper()[0], retryPolicy);
        client.start();
        
        return client;
    }
    
}
