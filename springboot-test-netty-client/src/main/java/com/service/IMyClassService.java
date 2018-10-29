package com.service;

import java.util.List;

import com.bean.MyClass;
import com.my.netty.spring.annotation.RemotApi;

@RemotApi
public interface IMyClassService
{
    List<MyClass> findMyClasses();
}
