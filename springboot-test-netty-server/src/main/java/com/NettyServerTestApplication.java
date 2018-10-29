package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class NettyServerTestApplication
{
    
    public static void main(String[] args)
    {
        ConfigurableApplicationContext context = SpringApplication.run(NettyServerTestApplication.class, args);
  
        try
        {
//           User user =  context.getBean(IUserService.class).findUserByUsernameAndPassword(null, null);
//           System.out.println(user);
            
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
    }
}
