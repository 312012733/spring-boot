package com.service;

import com.bean.Student;
import com.my.netty.spring.annotation.RemotApi;
import com.vo.Page;
import com.vo.StudentDTO;
@RemotApi
public interface IStudentService
{
    
    void addStudent(StudentDTO stuDTO);
    
    Page<Student> findStudentsByPage(Page<Student> page, Student condition);
    
    StudentDTO findStudentById(String stuId);
    
    void updateStudent(StudentDTO stuDTO);
    
    void delStudent(String stuId);
    
    void batchDelStudents(String[] ids);
    
    void bindStuIdcard(String stuId);
    
    void unBindStuIdcard(String stuId);
}
