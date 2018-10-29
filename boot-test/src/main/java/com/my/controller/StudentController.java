package com.my.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.my.bean.Student;
import com.my.service.IStudentService;
import com.my.vo.ErrorHanlder;
import com.my.vo.Page;
import com.my.vo.StudentDTO;

@Controller
// @RestController
public class StudentController
{
    @Autowired
    private IStudentService studentService;
    
    @RequestMapping(path = "/stus", method = RequestMethod.GET)
    public ResponseEntity<Object> findByPage(StudentDTO stuParam, Page<Student> pageParam) throws IOException
    {
        try
        {
            // 业务的处理
            Page<Student> page = studentService.findStudentByPage(pageParam, stuParam);
            
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            // 设置 请求失败的 状态 及 原因
            return new ResponseEntity<>(new ErrorHanlder(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        
    }
    
    @RequestMapping(path = "/{stuId}/stu", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable String stuId) throws IOException
    {
        try
        {
            StudentDTO student = studentService.findeStudentById(stuId);
            
            return new ResponseEntity<>(student, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            // 设置 请求失败的 状态 及 原因
            return new ResponseEntity<>(new ErrorHanlder(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(path = "/stu", method = RequestMethod.POST)
    public ResponseEntity<Object> add(@RequestBody StudentDTO stuParam) throws IOException
    {
        
        try
        {
            // stuParam.setTeacherIds(teacherIds);
            studentService.addStudent(stuParam);
            
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            // 设置 请求失败的 状态 及 原因
            return new ResponseEntity<>(new ErrorHanlder(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(path = "/{stuId}/stu", method = RequestMethod.PUT)
    // @ResponseBody
    public ResponseEntity<Object> update(@PathVariable String stuId, @RequestBody StudentDTO stuParam,
            HttpServletRequest req) throws IOException
    {
        
        try
        {
            
            stuParam.setId(stuId);
            studentService.updateStudent(stuParam);
            
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            // 设置 请求失败的 状态 及 原因
            return new ResponseEntity<>(new ErrorHanlder(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(path = "/{stuId}/stu", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable String stuId) throws IOException
    {
        try
        {
            studentService.deleteStudent(stuId);
            
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            // 设置 请求失败的 状态 及 原因
            return new ResponseEntity<>(new ErrorHanlder(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(path = "/stus", method = RequestMethod.DELETE)
    public ResponseEntity<Object> batchDelete(HttpServletRequest req) throws IOException
    {
        
        try
        {
            // 业务的处理
            String[] stuIds = req.getParameterValues("stuId[]");
            
            studentService.batchDeleteStudenst(stuIds);
            
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            // 设置 请求失败的 状态 及 原因
            return new ResponseEntity<>(new ErrorHanlder(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        
    }
    
    @RequestMapping(path = "/stu/{stuId}/binding/idcard", method = RequestMethod.POST)
    public ResponseEntity<Object> bindIdCard(@PathVariable String stuId) throws IOException
    {
        
        try
        {
            // 业务的处理
            studentService.bindingIdcard(stuId);
            
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            // 设置 请求失败的 状态 及 原因
            return new ResponseEntity<>(new ErrorHanlder(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(path = "/stu/{stuId}/unbinding/idcard", method = RequestMethod.POST)
    public ResponseEntity<Object> unbindIdCard(@PathVariable String stuId) throws IOException
    {
        try
        {
            // 业务的处理
            studentService.unbindingIdcard(stuId);
            
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            // 设置 请求失败的 状态 及 原因
            return new ResponseEntity<>(new ErrorHanlder(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
}
