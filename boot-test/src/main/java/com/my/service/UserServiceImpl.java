package com.my.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.bean2.User;
import com.my.dao.IUserDao;

@Service
public class UserServiceImpl implements IUserService
{
    // private static final Logger LOG =
    // Logger.getLogger(UserServiceImpl.class);
    
    @Autowired
    private IUserDao userDao;
    
    // @Autowired
    // private SessionFactory sessionFactory;
    
    @Transactional
    @Override
    public User login(String username, String password)
    {
        // Session session = sessionFactory.getCurrentSession();
        // Transaction tx = session.beginTransaction();
        //
        // try
        // {
        User user = userDao.findUser(username, password);
        //
        // tx.commit();
        //
        return user;
        // }
        // catch (Exception e)
        // {
        // tx.rollback();
        // throw e;
        // }
    }
}
