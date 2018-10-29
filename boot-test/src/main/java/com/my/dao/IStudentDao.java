package com.my.dao;

import com.my.bean.Student;
import com.my.vo.Page;
import com.my.vo.StudentDTO;

public interface IStudentDao
{
    Page<Student> findStudentByPage(Page<Student> page, StudentDTO condition);
    
    Student findeStudentById(String stuId);
    
    void addStudent(Student stu);
    
    void updateStudent(Student stu);
    
    void deleteStudent(Student stuId);
    
    // void batchDeleteStudenst(String[] stuIds);
    
}
