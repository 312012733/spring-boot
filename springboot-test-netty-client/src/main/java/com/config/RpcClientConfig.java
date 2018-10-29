package com.config;

import com.my.netty.spring.annotation.RemotApi;
import com.my.netty.spring.annotation.RemotApiScan;

@RemotApiScan(basePackages = "com", annotationClass = RemotApi.class)
public class RpcClientConfig
{
    
}
