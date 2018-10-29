package com.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.my.bean.Student;
import com.my.bean2.User;

public class SpringTest
{
    
    public static void main(String[] args)
    {
        // IOC/DI
        
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        
        // AnnotationConfigApplicationContext context = new
        // AnnotationConfigApplicationContext("com");
        
        User u1 = context.getBean("user", User.class);
        User u2 = context.getBean("user", User.class);
        User u3 = context.getBean("user", User.class);
        
        System.out.println(u1 + "   " + u1.hashCode());
        System.out.println(u2 + "   " + u2.hashCode());
        System.out.println(u3 + "   " + u3.hashCode());
        
        Student s1 = context.getBean("student", Student.class);
        
        System.out.println(s1 + "   " + s1.hashCode());
        
        context.close();
    }
    
}
