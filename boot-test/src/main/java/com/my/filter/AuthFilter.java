package com.my.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthFilter implements Filter
{
    private static final List<String> WHILE_URI = new ArrayList<>();
    private static final List<String> LOGIN_URI = new ArrayList<>();
    
    static
    {
        WHILE_URI.add("/js/lib/*.js");// .+\\.js
        WHILE_URI.add("/generateCheckCode");
        
        LOGIN_URI.add("/login.html");
        LOGIN_URI.add("/login");
        
        WHILE_URI.addAll(LOGIN_URI);
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        // System.out.println(this.getClass().getName()+"-----init-------");
    }
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fc) throws IOException, ServletException
    {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpResp = (HttpServletResponse) resp;
        
        try
        {
            
            if (true)
            {
                fc.doFilter(req, resp);// 执行请求（静态资源或servlet）
                return;
            }
            
            String contextPath = httpReq.getContextPath();
            String uri = httpReq.getRequestURI().substring(contextPath.length());
            
            System.out.println("uri:" + uri);
            
            if (!inWhiteUri(uri))
            {
                if (!doAuth(httpReq))
                {
                    throw new SecurityException("you have no login");
                }
            }
            else
            {
                // TODO 考虑重复登录的问题
                if (inLoginUri(uri) && doAuth(httpReq))
                {
                    // 重定向到主页面
                    httpResp.sendRedirect(contextPath + "/page/main.html");
                    return;
                }
            }
            
            fc.doFilter(req, resp);// 执行请求（静态资源或servlet）
        }
        catch (Exception e)
        {
            e.printStackTrace();
            httpResp.sendError(403, e.getMessage());
        }
    }
    
    private static boolean containtUrl(List<String> uriList, String targetUri)
    {
        for (String uri : uriList)
        {
            String regex = uri.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".+");
            
            if (targetUri.matches(regex))
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean inLoginUri(String uri)
    {
        return containtUrl(LOGIN_URI, uri);
    }
    
    private boolean inWhiteUri(String uri)
    {
        return containtUrl(WHILE_URI, uri);
    }
    
    private boolean doAuth(HttpServletRequest httpReq)
    {
        HttpSession session = httpReq.getSession();
        
        Object user = session.getAttribute(session.getId());
        
        if (null == user)
        {
            return false;
        }
        
        return true;
    }
    
    @Override
    public void destroy()
    {
        // System.out.println(this.getClass().getName()+"-----destroy-------");
    }
    
    // public static void main(String[] args)
    // {
    // // System.out.println("fsdfs2.13.47.823742..js".matches(".+\\.js"));
    //
    // containtUrl(WHILE_URI, "/js/lib1/a.js");
    // }
}
