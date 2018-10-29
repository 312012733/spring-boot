package com.my.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.my.bean2.User;
import com.my.service.IUserService;
import com.my.vo.ErrorHanlder;
import com.my.vo.UserDTO;
import com.utils.GenerateCheckCodeUtils;
import com.utils.GenerateCheckCodeUtils.CallBack;
import com.utils.LogUtils;

@Controller
public class UserController
{
    @Autowired
    private IUserService userService;
    
    @RequestMapping(path = "/user/generateCheckCode")
    public void generateCheckCode(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        try
        {
            OutputStream output = resp.getOutputStream(); // 获得可以向客户端返回图片的输出流
            
            // 创建验证码图片并返回图片上的字符串
            GenerateCheckCodeUtils.createAndSend(new CallBack()
            {
                @Override
                public void doSomething(String checkCode)
                {
                    LogUtils.debug("checkCode:" + checkCode);
                    req.getSession().setAttribute("checkCode", checkCode);
                }
                
            }, output);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            // 设置 请求失败的 状态 及 原因
            resp.setStatus(400);
        }
    }
    
    // @RequestMapping(value = "/user_login", method = RequestMethod.GET)
    // public String login2(UserDTO userParam, HttpServletRequest req,
    // HttpServletResponse resp) throws IOException
    // {
    // User user = userService.login(userParam.getUsername(),
    // userParam.getPassword());
    //
    // if (null == user)
    // {
    // return "redirect:/error.html";
    // }
    //
    // return "forward:/success.html";
    // }
    
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    // public ResponseEntity<Object> login(String username, String password,
    // @RequestParam(name="checkCode") String checkCode, HttpServletRequest req,
    // HttpServletResponse resp) throws IOException
    public ResponseEntity<Object> login(UserDTO userParam, HttpServletRequest req, HttpServletResponse resp)
            throws IOException
    {
        try
        {
            // 业务的处理
            HttpSession session = req.getSession();// 获取会话 等价于
            
            if (null == userParam.getCheckCode() || !userParam.getCheckCode().equals(session.getAttribute("checkCode")))
            {
                throw new SecurityException("check code is error. ");
            }
            
            User user = userService.login(userParam.getUsername(), userParam.getPassword());
            
            if (null == user)
            {
                throw new SecurityException("username or password is error. ");
            }
            
            session.setAttribute(session.getId(), user);
            
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
