package com.utils;

import java.util.HashMap;
import java.util.Map;

import com.my.bean.Student;
import com.my.bean2.User;

public class MappingUtils
{
    private static final Map<String, String> STUDENT_MAPPING = new HashMap<>();
    private static final Map<String, String> USER_MAPPING = new HashMap<>();
    
    static
    {
        STUDENT_MAPPING.put("id", "pk_student_id");
        STUDENT_MAPPING.put("name", "student_name");
        STUDENT_MAPPING.put("age", "age");
        STUDENT_MAPPING.put("gender", "gender");
        STUDENT_MAPPING.put("createTime", "create_time");
        STUDENT_MAPPING.put("lastModifyTime", "last_modify_time");
        
        USER_MAPPING.put("id", "pk_id");
        USER_MAPPING.put("username", "user_name");
        USER_MAPPING.put("password", "password");
    }
    
    public static String getColumNameByFieldName(String fieldName, Class<?> entityType)
    {
        String columName = null;
        
        if (entityType.getName().equals(Student.class.getName()))
        {
            columName = STUDENT_MAPPING.get(fieldName);
        }
        else if (entityType.getName().equals(User.class.getName()))
        {
            columName = USER_MAPPING.get(fieldName);
        }
        
        return null == columName ? fieldName : columName;
    }
    
}
