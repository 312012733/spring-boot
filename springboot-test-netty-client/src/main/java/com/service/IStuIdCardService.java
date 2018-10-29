package com.service;

import com.bean.StudentIdCard;
import com.my.netty.spring.annotation.RemotApi;
@RemotApi
public interface IStuIdCardService
{
    StudentIdCard findIdCardByStuId(String stuId);
    
    void save(StudentIdCard studentIdCard);
    
    void update(StudentIdCard studentIdCard);
    
    void delete(String idCardId);
    
    void batDelete(String[] ids);
    
}
