package com.test.event;

import org.springframework.context.ApplicationEvent;

public class DemoEvent extends ApplicationEvent
{
    private static final long serialVersionUID = -2587320801005533606L;
    
    private String msg;
    
    public DemoEvent(Object source, String msg)
    {
        super(source);
        this.msg = msg;
    }
    
    public String getMsg()
    {
        return msg;
    }
    
    public void setMsg(String msg)
    {
        this.msg = msg;
    }
    
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }
    
}
