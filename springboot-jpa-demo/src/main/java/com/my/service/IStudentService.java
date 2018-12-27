package com.my.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.my.bean.Student;
import com.my.vo.StudentDTO;

public interface IStudentService
{
    
    void addStudent(StudentDTO stuDTO);
    
    Page<Student> findStudentsByPage(Pageable pageable, Student condition);
    
    StudentDTO findStudentById(String stuId);
    
    void updateStudent(StudentDTO stuDTO);
    
    void delStudent(String stuId);
    
    void batchDelStudents(String[] ids);
    
    void bindStuIdcard(String stuId);
    
    void unBindStuIdcard(String stuId);
}
