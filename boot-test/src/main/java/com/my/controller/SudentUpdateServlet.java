package com.my.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.my.vo.StudentDTO;

//@WebServlet(urlPatterns="/5576b37c-fa04-4e68-b8d8-809268ca91bd/stu")
public class SudentUpdateServlet extends HttpServlet
{
    private static final long serialVersionUID = -8626086723005624429L;
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        InputStream in = req.getInputStream();
        
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        
        int len = -1;
        byte[] buf = new byte[1024];
        
        while ((len = in.read(buf)) > 0)
        {
            bao.write(buf, 0, len);
        }
        
        String jsonStr = new String(bao.toByteArray(), "utf-8");
        
        StudentDTO temp = JSONObject.parseObject(jsonStr, StudentDTO.class);
        
        System.out.println(temp);
    }
    
}
