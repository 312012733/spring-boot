package com.my.dao;

import java.util.List;

import com.my.bean.MyClass;

public interface IMyClassDao
{
    List<MyClass> findAllClasses();
    
    MyClass findClassById(String classId);
}
