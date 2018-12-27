package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableAutoConfiguration
public class SpringbootMybatisDemoApplication extends SpringBootServletInitializer
{
    
    // public static void main(String[] args) throws NoSuchFieldException,
    // SecurityException, NoSuchMethodException
    // {
    // ConfigurableApplicationContext context =
    // SpringApplication.run(SpringbootMybatisDemoApplication.class, args);
    //
    // }
    
    public static void main(String[] args)
    {
        SpringApplication.run(SpringbootMybatisDemoApplication.class, args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(SpringbootMybatisDemoApplication.class);
    }
}
