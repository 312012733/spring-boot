package com.test.mvc;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.my.filter.TempFilter;

public class WebInitializer implements WebApplicationInitializer
{
    
    // @Override
    public void onStartup(ServletContext servletContext) throws ServletException
    {
        // XmlWebApplicationContext ctx = new XmlWebApplicationContext();
        // ctx.setConfigLocation("classpath:springmvc.xml");
        
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(MyMvcConfig.class);
        
        ctx.setServletContext(servletContext);
        
        Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
        
        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);
        
        servletContext.addFilter("tempFilter", TempFilter.class)
                .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");
        
        servletContext.addListener(com.my.listener.JbdcContextListener.class);
    }
}
