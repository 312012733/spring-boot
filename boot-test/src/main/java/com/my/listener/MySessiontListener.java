package com.my.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MySessiontListener implements HttpSessionListener
{
    
    @Override
    public void sessionCreated(HttpSessionEvent se)
    {
    }
    
    @Override
    public void sessionDestroyed(HttpSessionEvent se)
    {
    }
    
}
