package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.my.netty.client.NettyClient;

@SpringBootApplication
public class NettyClientTestApplication
{
    
    public static void main(String[] args)
    {
        ConfigurableApplicationContext context = SpringApplication.run(NettyClientTestApplication.class, args);
        //
        // NettyClient c = context.getBean(NettyClient.class);
        //
        // for (int i = 0; i < 1; i++)
        // {
        // // Thread.sleep(100);
        // new Mythread(c, i).start();
        // }
    }
    
    public static class Mythread extends Thread
    {
        
        NettyClient c;
        int i;
        
        public Mythread(NettyClient c, int i)
        {
            this.c = c;
            this.i = i;
        }
        
        @Override
        public void run()
        {
            // c.send("send=====" + i);
        }
    }
}
