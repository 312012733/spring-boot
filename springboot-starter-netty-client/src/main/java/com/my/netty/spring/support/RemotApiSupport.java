package com.my.netty.spring.support;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import com.my.netty.client.NettyClient;

public class RemotApiSupport<T>
{
    @Autowired
    private NettyClient client;
    
    @SuppressWarnings("unchecked")
    protected T getRemotApi(Class<T> remoteApiInterface)
    {
        Enhancer enhance = new Enhancer();
        
        enhance.setInterfaces(new Class[]
        { remoteApiInterface });
        
        enhance.setCallback(new MethodInterceptor()
        {
            
            @Override
            public Object intercept(Object source, Method methhod, Object[] args, MethodProxy proxy) throws Throwable
            {
                return client.send(remoteApiInterface, methhod, args);
            }
        });
        
        return (T) enhance.create();
    }
}
