package com.my.netty.clinet.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("netty.client")
public class NettyClientBean
{
    private String serverHost = "";
    private Integer serverPort = 0;
    
    public NettyClientBean()
    {
    }
    
    public String getServerHost()
    {
        return serverHost;
    }
    
    public void setServerHost(String serverHost)
    {
        this.serverHost = serverHost;
    }
    
    public Integer getServerPort()
    {
        return serverPort;
    }
    
    public void setServerPort(Integer serverPort)
    {
        this.serverPort = serverPort;
    }
    
}
