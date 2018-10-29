package com.my.service;

import java.util.List;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.my.bean.MyClass;
import com.my.bean.Student;
import com.my.bean.StudentIdCard;
import com.my.bean.Teacher;
import com.my.dao.IMyClassDao;
import com.my.dao.IStudentDao;
import com.my.dao.IStudentIdCardDao;
import com.my.dao.ITeacherDao;
import com.my.vo.Page;
import com.my.vo.StudentDTO;

@Service
public class StudentServiceImpl implements IStudentService
{
    @Autowired
    private IStudentDao studentDao;
    @Autowired
    private ITeacherDao teacherDao;
    @Autowired
    private IMyClassDao myClassDao;
    @Autowired
    private IStudentIdCardDao idCardDao;
    @Autowired
    private SessionFactory sessionFactory;
    
    @Transactional
    @Override
    public Page<Student> findStudentByPage(Page<Student> pageParam, StudentDTO condition)
    {
        return studentDao.findStudentByPage(pageParam, condition);
    }
    
    @Transactional
    @Override
    public StudentDTO findeStudentById(String stuId)
    {
        
        Student stu = studentDao.findeStudentById(stuId);
        
        if (null == stu)
        {
            throw new SecurityException("stu is not found. stuId:" + stuId);
        }
        
        List<Teacher> excludeTeachers = teacherDao.findExcludeTeachersByStuId(stuId);
        
        StudentDTO stuDTO = stu.toStudentDTO();
        
        stuDTO.setExcludeTeachers(excludeTeachers);
        
        return stuDTO;
        
    }
    
    @Transactional
    @Override
    public void addStudent(StudentDTO stuDto)
    {
        
        stuDto.setCreateTime(System.currentTimeMillis());
        stuDto.setLastModifyTime(System.currentTimeMillis());
        stuDto.setId(UUID.randomUUID().toString());
        
        Student stu = stuDto.toStudent();
        
        MyClass classDB = null;
        
        if (null != stuDto.getMyClass() && !StringUtils.isEmpty(stuDto.getMyClass().getId()))
        {
            String classId = stuDto.getMyClass().getId();
            classDB = myClassDao.findClassById(classId);
            
            if (null == classDB)
            {
                throw new SecurityException("myClass is not found. clasId:" + classId);
            }
        }
        
        stu.setMyClass(classDB);
        
        // 设置与老师关系
        if (null != stuDto.getTeacherIds() && stuDto.getTeacherIds().length > 0)
        {
            for (String teacherId : stuDto.getTeacherIds())
            {
                Teacher t = teacherDao.findTeacherById(teacherId);
                
                if (t == null)
                {
                    throw new SecurityException("teacher is not found . teacherId:" + teacherId);
                }
                
                stu.getTeachers().add(t);
            }
            
        }
        
        studentDao.addStudent(stu);
    }
    
    @Transactional
    @Override
    public void updateStudent(StudentDTO stuDto)
    {
        if (null == stuDto || null == stuDto.getId())
        {
            throw new SecurityException("student request param is null.");
        }
        
        Student stuDB = studentDao.findeStudentById(stuDto.getId());
        
        if (null == stuDB)
        {
            throw new SecurityException("student is not found. stuId:" + stuDto.getId());
        }
        
        MyClass classDB = null;
        
        if (null != stuDto.getMyClass() && !StringUtils.isEmpty(stuDto.getMyClass().getId()))
        {
            String classId = stuDto.getMyClass().getId();
            classDB = myClassDao.findClassById(classId);
            
            if (null == classDB)
            {
                throw new SecurityException("myClass is not found. clasId:" + classId);
            }
        }
        
        stuDB.setMyClass(classDB);
        
        stuDB.setName(stuDto.getName());
        stuDB.setAge(stuDto.getAge());
        stuDB.setLastModifyTime(System.currentTimeMillis());
        
        // 设置与老师关系
        if (null != stuDto.getTeacherIds() && stuDto.getTeacherIds().length > 0)
        {
            for (Teacher t : stuDB.getTeachers())
            {
                t.getStudents().remove(stuDB);
            }
            
            stuDB.getTeachers().clear();
            
            for (String teacherId : stuDto.getTeacherIds())
            {
                Teacher t = teacherDao.findTeacherById(teacherId);
                
                if (t == null)
                {
                    throw new SecurityException("teacher is not found . teacherId:" + teacherId);
                }
                
                stuDB.getTeachers().add(t);
            }
        }
        
        studentDao.updateStudent(stuDB);
    }
    
    @Transactional
    @Override
    public void deleteStudent(String stuId)
    {
        delete(stuId, studentDao, idCardDao);
    }
    
    @Transactional
    @Override
    public void batchDeleteStudenst(String[] stuIds)
    {
        if (null == stuIds || stuIds.length <= 0)
        {
            throw new SecurityException("stuIds is null. ");
        }
        
        for (String stuId : stuIds)
        {
            delete(stuId, studentDao, idCardDao);
        }
    }
    
    @Transactional
    @Override
    public void bindingIdcard(String stuId)
    {
        
        // 1.检查参数
        Student stuDB = studentDao.findeStudentById(stuId);
        
        if (null == stuDB)
        {
            throw new SecurityException("student is not found. stuId:" + stuId);
        }
        
        if (null != stuDB.getStudentIdCard())
        {
            throw new SecurityException("studentIdCard has binding. ");
        }
        
        // 2.生成一个学生证
        StudentIdCard idCard = new StudentIdCard(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        
        // 3.设置关系
        stuDB.setStudentIdCard(idCard);
        studentDao.updateStudent(stuDB);
    }
    
    @Transactional
    @Override
    public void unbindingIdcard(String stuId)
    {
        // 1.检查参数
        Student stuDB = studentDao.findeStudentById(stuId);
        
        if (null == stuDB)
        {
            throw new SecurityException("student is not found. stuId:" + stuId);
        }
        
        if (null == stuDB.getStudentIdCard())
        {
            throw new SecurityException("studentIdCard has not binding. ");
        }
        
        StudentIdCard idCard = stuDB.getStudentIdCard();
        
        // 2.解除关系
        stuDB.getStudentIdCard().setStudent(null);
        stuDB.setStudentIdCard(null);
        studentDao.updateStudent(stuDB);
        
        // 3.删除一个学生证
        idCardDao.deleteStudentIdCard(idCard);
    }
    
    private void delete(String stuId, IStudentDao studentDao, IStudentIdCardDao idCardDao) throws SecurityException
    {
        Student stuDB = studentDao.findeStudentById(stuId);
        
        if (null == stuDB)
        {
            throw new SecurityException("student is not found. stuId:" + stuId);
        }
        
        if (null != stuDB.getTeachers() && !stuDB.getTeachers().isEmpty())
        {
            for (Teacher t : stuDB.getTeachers())
            {
                t.getStudents().remove(stuDB);
            }
            
            stuDB.getTeachers().clear();
        }
        
        if (null != stuDB.getStudentIdCard())
        {
            StudentIdCard idcard = stuDB.getStudentIdCard();
            idcard.setStudent(null);
            stuDB.setStudentIdCard(null);
            
            idCardDao.deleteStudentIdCard(idcard);
        }
        
        studentDao.deleteStudent(stuDB);
    }
}
