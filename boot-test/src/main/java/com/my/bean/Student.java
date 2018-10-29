package com.my.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.my.vo.StudentDTO;

@Entity
@Table(name = "t_student")
public class Student
{
    
    @Id
    @Column(name = "pk_student_id")
    private String id;
    
    @Column(name = "student_name")
    private String name;
    
    @Column(name = "age")
    private Integer age;
    
    @Column(name = "gender")
    private Boolean gender;// 默认是 男
    
    @Column(name = "create_time")
    private Long createTime;
    
    @Column(name = "last_modify_time")
    private Long lastModifyTime;
    
    @OneToOne(fetch = FetchType.LAZY) // N+1
    @JoinColumn(name = "fk_id_card")
    @Cascade(value = CascadeType.SAVE_UPDATE)
    @Fetch(value = FetchMode.JOIN) // 会影响lazy,绝对控制查询策略
    private StudentIdCard studentIdCard;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_class_id")
    @Cascade(value = CascadeType.SAVE_UPDATE)
    @Fetch(value = FetchMode.JOIN) // 会影响lazy,绝对控制查询策略
    private MyClass myClass;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_student_teacher", joinColumns =
    { @JoinColumn(name = "pk_student_id") }, inverseJoinColumns =
    { @JoinColumn(name = "pk_teacher_id") })
    @Cascade(value = CascadeType.SAVE_UPDATE)
    private List<Teacher> teachers = new ArrayList<>();
    
    public Student()
    {
    }
    
    public Student(String id, String name, Integer age, Boolean gender)
    {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
    
    public StudentDTO toStudentDTO()
    {
        StudentDTO dto = new StudentDTO();
        
        dto.setAge(this.getAge());
        dto.setCreateTime(this.getCreateTime());
        dto.setGender(this.getGender());
        dto.setId(this.getId());
        dto.setLastModifyTime(this.getLastModifyTime());
        dto.setMyClass(this.getMyClass());
        dto.setName(this.getName());
        dto.setStudentIdCard(this.getStudentIdCard());
        dto.setTeachers(this.getTeachers());
        
        return dto;
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
    
    public void setLastModifyTime(Long lastModifyTime)
    {
        this.lastModifyTime = lastModifyTime;
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
    
    @Override
    public String toString()
    {
        return "Student [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", createTime="
                + createTime + ", lastModifyTime=" + lastModifyTime + ", studentIdCard=" + studentIdCard + ", myClass="
                + myClass + "]";
    }
    
}
