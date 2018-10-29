package com.service;

import com.bean.User;
import com.my.netty.spring.annotation.RemotApi;

@RemotApi
public interface IUserService
{
    
    User findUserByUsernameAndPassword(String userName, String password) throws Exception;
    
}
