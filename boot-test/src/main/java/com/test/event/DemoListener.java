package com.test.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DemoListener implements ApplicationListener<DemoEvent>
{
    
    @Override
    public void onApplicationEvent(DemoEvent event)
    {
        System.out.println("收到消息：" + event.getMsg() + "---" + event.getSource());
    }
}
