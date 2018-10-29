package com.test.event;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main
{
    public Main()
    {
    }
    
    public static void main(String[] args)
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EventConfig.class);
        
        context.publishEvent(new DemoEvent("source", "hello application event..."));
        
        context.close();
    }
}
