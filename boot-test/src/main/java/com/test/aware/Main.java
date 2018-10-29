package com.test.aware;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main
{
    public Main()
    {
    }
    
    public static void main(String[] args)
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AwareConfig.class);
        
        AwareService service = context.getBean(AwareService.class);
        
        service.outputResult();
        
        context.close();
    }
}
