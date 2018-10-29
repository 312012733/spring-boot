package com.my.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class TempFilter2 implements Filter
{
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        System.out.println(this.getClass().getName() + "-----init-------");
    }
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fc) throws IOException, ServletException
    {
        // HttpSession session = req.getSession();//根据 coolie 中 JSESSIONID
        // 属性，去找session
        //
        // Object user = session.getAttribute(session.getId());
        //
        // if (null == user)
        // {
        // throw new SecurityException("you have no login");
        // }
        System.out.println(this.getClass().getName() + "-----doFilter-1------");
        
        fc.doFilter(req, resp);// 先执行下一个过滤器，最后执行请求（静态资源或servlet）
        
        System.out.println(this.getClass().getName() + "-----doFilter-2-----");
    }
    
    @Override
    public void destroy()
    {
        
        System.out.println(this.getClass().getName() + "-----destroy-------");
    }
}
