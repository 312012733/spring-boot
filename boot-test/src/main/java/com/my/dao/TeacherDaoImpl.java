package com.my.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.my.bean.Teacher;
import com.utils.HibernateUtils;

@Repository
public class TeacherDaoImpl implements ITeacherDao
{
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private HibernateUtils hibernateUtils;
    
    @Transactional(readOnly = true)
    @Override
    public Teacher findTeacherById(String teacherId)
    {
        return sessionFactory.getCurrentSession().get(Teacher.class, teacherId);
    }
    
    @Override
    public List<Teacher> findAllTeachers()
    {
        String sql = "SELECT * FROM t_teacher t";
        
        return hibernateUtils.findEntits(sql, null, Teacher.class);
    }
    
    @Override
    public List<Teacher> findExcludeTeachersByStuId(String stuId)
    {
        String sql = "  SELECT * FROM t_teacher t1 where t1.pk_teacher_id NOT IN ( "
                + " SELECT t.pk_teacher_id FROM t_teacher t LEFT JOIN t_student_teacher st ON t.pk_teacher_id = st.pk_teacher_id"
                + " where st.pk_student_id = :stuId ) order by t1.pk_teacher_id asc";
        
        Map<String, Object> condition = new HashMap<>();
        condition.put("stuId", stuId);
        
        return hibernateUtils.findEntits(sql, condition, Teacher.class);
    }
}
