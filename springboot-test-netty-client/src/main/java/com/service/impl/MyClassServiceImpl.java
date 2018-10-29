package com.service.impl;

import java.util.List;

import com.bean.MyClass;
import com.dao.IMyClassDao;
import com.service.IMyClassService;

public class MyClassServiceImpl implements IMyClassService
{
    private IMyClassDao classDao;
    
    public List<MyClass> findMyClasses()
    {
        return classDao.findMyClasses();
    }
    
}
