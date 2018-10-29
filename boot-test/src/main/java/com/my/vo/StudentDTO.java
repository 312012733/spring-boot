package com.my.vo;

import java.util.Arrays;
import java.util.List;

import com.my.bean.MyClass;
import com.my.bean.Student;
import com.my.bean.StudentIdCard;
import com.my.bean.Teacher;

public class StudentDTO
{
    private String id;
    
    private String name;
    
    private Integer age;
    
    private Boolean gender;// 默认是 男
    
    private Long createTime;
    
    private Long lastModifyTime;
    
    private StudentIdCard studentIdCard;
    
    private MyClass myClass;
    
    private String[] teacherIds;
    
    private List<Teacher> teachers;
    
    private List<Teacher> excludeTeachers;
    
    public StudentDTO()
    {
    }
    
    public Student toStudent()
    {
        Student stu = new Student();
        
        stu.setAge(this.getAge());
        stu.setCreateTime(this.getCreateTime());
        stu.setGender(this.getGender());
        stu.setId(this.getId());
        stu.setLastModifyTime(this.getLastModifyTime());
        stu.setMyClass(this.getMyClass());
        stu.setName(this.getName());
        stu.setStudentIdCard(this.getStudentIdCard());
        // stu.setTeachers(this.getTeachers());
        
        return stu;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Integer getAge()
    {
        return age;
    }
    
    public void setAge(Integer age)
    {
        this.age = age;
    }
    
    public Boolean getGender()
    {
        return gender;
    }
    
    public void setGender(Boolean gender)
    {
        this.gender = gender;
    }
    
    public Long getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Long createTime)
    {
        this.createTime = createTime;
    }
    
    public Long getLastModifyTime()
    {
        return lastModifyTime;
    }
    
    public void setLastModifyTime(Long lastModifyTime)
    {
        this.lastModifyTime = lastModifyTime;
    }
    
    public StudentIdCard getStudentIdCard()
    {
        return studentIdCard;
    }
    
    public void setStudentIdCard(StudentIdCard studentIdCard)
    {
        this.studentIdCard = studentIdCard;
    }
    
    public MyClass getMyClass()
    {
        return myClass;
    }
    
    public void setMyClass(MyClass myClass)
    {
        this.myClass = myClass;
    }
    
    public List<Teacher> getTeachers()
    {
        return teachers;
    }
    
    public void setTeachers(List<Teacher> teachers)
    {
        this.teachers = teachers;
    }
    
    public List<Teacher> getExcludeTeachers()
    {
        return excludeTeachers;
    }
    
    public void setExcludeTeachers(List<Teacher> excludeTeachers)
    {
        this.excludeTeachers = excludeTeachers;
    }
    
    public String[] getTeacherIds()
    {
        return teacherIds;
    }
    
    public void setTeacherIds(String[] teacherIds)
    {
        this.teacherIds = teacherIds;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((age == null) ? 0 : age.hashCode());
        result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
        result = prime * result + ((excludeTeachers == null) ? 0 : excludeTeachers.hashCode());
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lastModifyTime == null) ? 0 : lastModifyTime.hashCode());
        result = prime * result + ((myClass == null) ? 0 : myClass.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((studentIdCard == null) ? 0 : studentIdCard.hashCode());
        result = prime * result + Arrays.hashCode(teacherIds);
        result = prime * result + ((teachers == null) ? 0 : teachers.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StudentDTO other = (StudentDTO) obj;
        if (age == null)
        {
            if (other.age != null)
                return false;
        }
        else if (!age.equals(other.age))
            return false;
        if (createTime == null)
        {
            if (other.createTime != null)
                return false;
        }
        else if (!createTime.equals(other.createTime))
            return false;
        if (excludeTeachers == null)
        {
            if (other.excludeTeachers != null)
                return false;
        }
        else if (!excludeTeachers.equals(other.excludeTeachers))
            return false;
        if (gender == null)
        {
            if (other.gender != null)
                return false;
        }
        else if (!gender.equals(other.gender))
            return false;
        if (id == null)
        {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (lastModifyTime == null)
        {
            if (other.lastModifyTime != null)
                return false;
        }
        else if (!lastModifyTime.equals(other.lastModifyTime))
            return false;
        if (myClass == null)
        {
            if (other.myClass != null)
                return false;
        }
        else if (!myClass.equals(other.myClass))
            return false;
        if (name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (studentIdCard == null)
        {
            if (other.studentIdCard != null)
                return false;
        }
        else if (!studentIdCard.equals(other.studentIdCard))
            return false;
        if (!Arrays.equals(teacherIds, other.teacherIds))
            return false;
        if (teachers == null)
        {
            if (other.teachers != null)
                return false;
        }
        else if (!teachers.equals(other.teachers))
            return false;
        return true;
    }
    
    @Override
    public String toString()
    {
        return "StudentDTO [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", createTime="
                + createTime + ", lastModifyTime=" + lastModifyTime + ", studentIdCard=" + studentIdCard + ", myClass="
                + myClass + ", teacherIds=" + Arrays.toString(teacherIds) + ", teachers=" + teachers
                + ", excludeTeachers=" + excludeTeachers + "]";
    }
    
}
