package com.feigege.hello;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hello")
public class HelloServiceProperties
{
    
    private String msg = "world";
    
    public HelloServiceProperties()
    {
    }
    
    public String getMsg()
    {
        return msg;
    }
    
    public void setMsg(String msg)
    {
        this.msg = msg;
    }
    
    @Override
    public String toString()
    {
        return "HelloServiceProperties [msg=" + msg + "]";
    }
    
}
