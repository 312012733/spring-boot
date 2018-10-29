package com.my.dao;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.my.bean.Student;
import com.my.vo.Page;
import com.my.vo.StudentDTO;
import com.utils.HibernateUtils;

@Repository
public class StudentDaoImpl implements IStudentDao
{
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private HibernateUtils hibernateUtils;
    
    @Override
    public Page<Student> findStudentByPage(Page<Student> page, StudentDTO condition)
    {
        Map<String, Object> conditionMap = new HashMap<>();
        String condtionSql = "";
        
        if (null != condition)
        {
            if (!StringUtils.isEmpty(condition.getName()))
            {
                condtionSql += "and s.student_name like :name ";
                conditionMap.put("name", "%" + condition.getName() + "%");
            }
            
            if (null != condition.getAge())
            {
                condtionSql += "and s.age = :age ";
                conditionMap.put("age", condition.getAge());
            }
            
            if (null != condition.getGender())
            {
                condtionSql += "and s.gender = :gender ";
                conditionMap.put("gender", condition.getGender());
            }
        }
        
        String listSql = "SELECT * FROM t_student  s where 1 = 1 " + condtionSql + " ORDER BY s.create_time DESC";
        String countSql = "SELECT count(*) FROM t_student s where 1 = 1 " + condtionSql;
        
        return hibernateUtils.findByPage(listSql, countSql, page, conditionMap, Student.class);
    }
    
    @Override
    public Student findeStudentById(String stuId)
    {
        return sessionFactory.getCurrentSession().find(Student.class, stuId);
    }
    
    @Override
    public void addStudent(Student stu)
    {
        sessionFactory.getCurrentSession().save(stu);
    }
    
    @Override
    public void updateStudent(Student stu)
    {
        sessionFactory.getCurrentSession().update(stu);
    }
    
    @Override
    public void deleteStudent(Student stu)
    {
        sessionFactory.getCurrentSession().delete(stu);
    }
}
