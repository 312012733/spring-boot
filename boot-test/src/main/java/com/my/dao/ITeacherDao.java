package com.my.dao;

import java.util.List;

import com.my.bean.Teacher;

public interface ITeacherDao
{
    Teacher findTeacherById(String teacherId);
    
    List<Teacher> findAllTeachers();
    
    List<Teacher> findExcludeTeachersByStuId(String stuId);
}
