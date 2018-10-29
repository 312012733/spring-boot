package com.my.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.my.bean.Student;
import com.my.bean.StudentIdCard;

@Repository
public class StudentIdCardDaoImpl implements IStudentIdCardDao
{
    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public StudentIdCard findeStuIdCardById(String idcardId)
    {
        return sessionFactory.getCurrentSession().get(StudentIdCard.class, idcardId);
    }
    
    @Override
    public StudentIdCard findStuIdCardByStuId(String stuId)
    {
        Student stu = sessionFactory.getCurrentSession().get(Student.class, stuId);
        
        return null == stu ? null : stu.getStudentIdCard();
    }
    
    @Override
    public void addStudentIdCard(StudentIdCard idCard)
    {
        sessionFactory.getCurrentSession().save(idCard);
    }
    
    @Override
    public void deleteStudentIdCard(StudentIdCard idCard)
    {
        sessionFactory.getCurrentSession().delete(idCard);
    }
}
