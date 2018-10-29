package com.my.service;

import java.util.List;

import com.my.bean.Teacher;

public interface ITeacherService
{
    Teacher findTeacherById(String teacherId);
    
    List<Teacher> findAllTeachers();
    
    List<Teacher> findExcludeTeachersByStuId(String stuId);
}
