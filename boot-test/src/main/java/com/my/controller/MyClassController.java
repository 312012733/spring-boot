package com.my.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.my.bean.MyClass;
import com.my.service.IMyClassService;
import com.my.vo.ErrorHanlder;

@Controller
public class MyClassController
{
    
    @Autowired
    private IMyClassService myClassService;
    
    @RequestMapping(path = "/myClasses", method = RequestMethod.GET)
    public ResponseEntity<Object> findAll() throws IOException
    {
        
        try
        {
            
            List<MyClass> classList = myClassService.findAllClasses();
            
            return new ResponseEntity<>(classList, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            // 设置 请求失败的 状态 及 原因
            return new ResponseEntity<>(new ErrorHanlder(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
}
