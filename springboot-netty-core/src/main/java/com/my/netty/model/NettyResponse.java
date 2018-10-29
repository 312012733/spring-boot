package com.my.netty.model;

public class NettyResponse
{
    
    private Long id;
    
    private Object content;
    
    public NettyResponse()
    {
    }
    
    public NettyResponse(Long id, Object content)
    {
        this.id = id;
        this.content = content;
    }
    
    public void setContent(Object content)
    {
        this.content = content;
    }
    
    public Object getContent()
    {
        return content;
    }
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    @Override
    public String toString()
    {
        return "NettyResponse [id=" + id + ", content=" + content + "]";
    }
    
}
