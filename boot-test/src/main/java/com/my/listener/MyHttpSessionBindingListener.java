package com.my.listener;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * 会话绑定 他不需要注册
 * 
 * session.setAttribute("userCount", new MyHttpSessionBindingListener()); // //
 * new Thread() // { // public void run() // { // try // { //
 * Thread.sleep(10000); // } // catch (InterruptedException e) // { //
 * e.printStackTrace(); // } // // session.removeAttribute("userCount"); // };
 * // }.start();
 */

public class MyHttpSessionBindingListener implements HttpSessionBindingListener
{
    private static int count;
    
    @Override
    public void valueBound(HttpSessionBindingEvent event)
    {
        count++;
        System.out.println("在线人数加1.。。。count=" + count);
    }
    
    @Override
    public void valueUnbound(HttpSessionBindingEvent event)
    {
        count--;
        System.out.println("在线人数减1.。。。count=" + count);
    }
    
}
