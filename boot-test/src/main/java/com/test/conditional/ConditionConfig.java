package com.test.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.test.conditional")
public class ConditionConfig
{
    @Bean
    @Conditional(WindowsConditon.class)
    public ListService windowsListService()
    {
        return new WindowsListService();
    }
    
    @Bean
    @Conditional(LinuxConditon.class)
    public ListService linuxListService()
    {
        return new LinuxListService();
    }
}
