package com.my.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.bean.MyClass;
import com.my.dao.IMyClassDao;

@Service
public class MyClassServiceImpl implements IMyClassService
{
    @Autowired
    private IMyClassDao myClassDao;
    
    @Transactional(readOnly = true)
    @Override
    public List<MyClass> findAllClasses()
    {
        return myClassDao.findAllClasses();
    }
    
}
