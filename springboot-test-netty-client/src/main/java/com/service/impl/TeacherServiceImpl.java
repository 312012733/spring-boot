package com.service.impl;

import java.util.List;

import com.bean.Teacher;
import com.dao.ITeacherDao;
import com.service.ITeacherService;

public class TeacherServiceImpl implements ITeacherService
{
    private ITeacherDao teacherDao;
    
    public List<Teacher> findTeachers()
    {
        return teacherDao.findTeachers();
    }
    
    public List<Teacher> findExcludTeachersByStuId(String stuId)
    {
        return teacherDao.findTeachersByExcludStuIds(stuId);
    }
    
}
