package com.my.listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

public class MyServletSessionAttrListener implements HttpSessionAttributeListener
{
    
    @Override
    public void attributeAdded(HttpSessionBindingEvent se)
    {
        System.out.println(se.getName() + "----------------------" + se.getValue());
    }
    
    @Override
    public void attributeRemoved(HttpSessionBindingEvent se)
    {
    }
    
    @Override
    public void attributeReplaced(HttpSessionBindingEvent se)
    {
    }
    
}
