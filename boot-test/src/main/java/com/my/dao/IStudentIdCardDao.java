package com.my.dao;

import com.my.bean.StudentIdCard;

public interface IStudentIdCardDao
{
    
    StudentIdCard findeStuIdCardById(String idcardId);
    
    StudentIdCard findStuIdCardByStuId(String stuId);
    
    void addStudentIdCard(StudentIdCard idCard);
    
    void deleteStudentIdCard(StudentIdCard idCard);
    
}
