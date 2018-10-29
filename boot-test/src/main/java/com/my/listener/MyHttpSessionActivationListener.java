package com.my.listener;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;

/**
 * 用来做 会话 迁移
 */
public class MyHttpSessionActivationListener implements HttpSessionActivationListener
{
    
    @Override
    public void sessionDidActivate(HttpSessionEvent se)
    {
    }
    
    @Override
    public void sessionWillPassivate(HttpSessionEvent se)
    {
    }
    
}
