package com.my.service;

import com.my.bean.Student;
import com.my.vo.Page;
import com.my.vo.StudentDTO;

public interface IStudentService
{
    Page<Student> findStudentByPage(Page<Student> page, StudentDTO condition);
    
    StudentDTO findeStudentById(String stuId);
    
    void addStudent(StudentDTO stu);
    
    void updateStudent(StudentDTO stu);
    
    void deleteStudent(String stuId);
    
    void batchDeleteStudenst(String[] stuIds);
    
    void bindingIdcard(String stuId);
    
    void unbindingIdcard(String stuId);
}
