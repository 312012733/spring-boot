package com.service.impl;

import org.springframework.context.ApplicationContext;

import com.bean.User;
import com.dao2.IUserDao;
import com.service.IUserService;

public class UserServiceImpl implements IUserService
{
    private IUserDao userDao;
    
    public UserServiceImpl(ApplicationContext context)
    {
        userDao = context.getBean(IUserDao.class);
    }
    
    public User findUserByUsernameAndPassword(String userName, String password) throws Exception
    {
        return userDao.findUserByUsernameAndPassword(userName, password);
    }
    
}
