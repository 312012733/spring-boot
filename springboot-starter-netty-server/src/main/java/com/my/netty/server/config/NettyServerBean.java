package com.my.netty.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("netty.server")
public class NettyServerBean
{
    
    private String name = "";
    
    private Integer port = 0;
    
    private String[] zookeeper =
    {};
    
    public NettyServerBean()
    {
    }
    
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String[] getZookeeper()
    {
        return zookeeper;
    }
    
    public void setZookeeper(String[] zookeeper)
    {
        this.zookeeper = zookeeper;
    }
    
    public Integer getPort()
    {
        return port;
    }
    
    public void setPort(Integer port)
    {
        this.port = port;
    }
    
}
