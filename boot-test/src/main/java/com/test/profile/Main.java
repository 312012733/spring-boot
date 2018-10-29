package com.test.profile;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main
{
    public Main()
    {
    }
    
    public static void main(String[] args)
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        
        context.getEnvironment().setActiveProfiles("prod");
        context.register(ProfileConfig.class);
        context.refresh();
        
        DemoBean deomoBean = context.getBean(DemoBean.class);
        
        System.out.println(deomoBean.getContent());
        context.close();
    }
}
