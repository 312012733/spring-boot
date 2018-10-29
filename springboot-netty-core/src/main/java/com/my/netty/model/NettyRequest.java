package com.my.netty.model;

import java.util.concurrent.atomic.AtomicLong;

public class NettyRequest
{
    private static final AtomicLong incrementKey = new AtomicLong();
    
    private Long id;
    
    private ClassMsgBean classMsgBean;
    
    public NettyRequest()
    {
        this.id = incrementKey.incrementAndGet();
    }
    
    public Long getId()
    {
        return id;
    }
    
    public ClassMsgBean getClassMsgBean()
    {
        return classMsgBean;
    }
    
    public void setClassMsgBean(ClassMsgBean classMsgBean)
    {
        this.classMsgBean = classMsgBean;
    }
    
}
