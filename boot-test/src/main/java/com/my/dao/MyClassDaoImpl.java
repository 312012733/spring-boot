package com.my.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.my.bean.MyClass;
import com.utils.HibernateUtils;

@Repository
public class MyClassDaoImpl implements IMyClassDao
{
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private HibernateUtils hibernateUtils;
    
    @Override
    public List<MyClass> findAllClasses()
    {
        String sql = " SELECT * FROM t_class c ";
        
        return hibernateUtils.findEntits(sql, null, MyClass.class);
    }
    
    @Override
    public MyClass findClassById(String classId)
    {
        return sessionFactory.getCurrentSession().get(MyClass.class, classId);
    }
}
