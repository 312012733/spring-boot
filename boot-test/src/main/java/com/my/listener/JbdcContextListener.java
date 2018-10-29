package com.my.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class JbdcContextListener implements ServletContextListener
{
    
    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        try
        {
            Class.forName("com.utils.JDBCUtils");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        
        // new JDBCUtils().getClass();
        
        // JDBCUtils.class.toString();//静态常量属性的访问，不会触发类加载。。。。。
        
        // System.out.println(this.getClass().getName() +
        // "------------contextInitialized----------invoke-----");
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {
        // System.out.println(this.getClass().getName()+"------------contextDestroyed----------invoke-----");
    }
    
}
