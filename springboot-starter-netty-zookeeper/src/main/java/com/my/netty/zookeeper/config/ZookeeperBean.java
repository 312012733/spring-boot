package com.my.netty.zookeeper.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("netty.server")
public class ZookeeperBean
{
    private String[] zookeeper =
    {};
    
    public ZookeeperBean()
    {
    }
    
    public String[] getZookeeper()
    {
        return zookeeper;
    }
    
    public void setZookeeper(String[] zookeeper)
    {
        this.zookeeper = zookeeper;
    }
    
}
