package com.test.conditional;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main
{
    public Main()
    {
    }
    
    public static void main(String[] args)
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConditionConfig.class);
        
        ListService service = context.getBean(ListService.class);
        
        System.out.println(context.getEnvironment().getProperty("os.name") + "系统下的列表命令为：" + service.showListCmd());
        
        context.close();
    }
}
