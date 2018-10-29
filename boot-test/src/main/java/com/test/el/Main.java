package com.test.el;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main
{
    
    public static void main(String[] args)
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ElConfig.class);
        
        ElConfig config = context.getBean(ElConfig.class);
        
        System.out.println(config);
        
        context.close();
    }
    
}
