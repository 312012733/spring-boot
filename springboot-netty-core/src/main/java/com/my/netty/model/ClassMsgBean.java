package com.my.netty.model;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ClassMsgBean
{
    private String className;
    private String method;
    private String[] argsType;
    private Object[] args;
    
    public ClassMsgBean()
    {
    }
    
    public ClassMsgBean(Class<?> targetClass, Method targetMethod, Object[] args)
    {
        this.className = targetClass.getName();
        this.method = targetMethod.getName();
        
        if (null != args)
        {
            this.args = args;
            this.argsType = new String[args.length];
            Class<?>[] parameterTypes = targetMethod.getParameterTypes();
            
            for (int i = 0; i < args.length; i++)
            {
                argsType[i] = parameterTypes[i].getName();
            }
        }
    }
    
    public String getClassName()
    {
        return className;
    }
    
    public void setClassName(String className)
    {
        this.className = className;
    }
    
    public String getMethod()
    {
        return method;
    }
    
    public void setMethod(String method)
    {
        this.method = method;
    }
    
    public Object[] getArgs()
    {
        return args;
    }
    
    public void setArgs(Object[] args)
    {
        this.args = args;
    }
    
    public String[] getArgsType()
    {
        return argsType;
    }
    
    public void setArgsType(String[] argsType)
    {
        this.argsType = argsType;
    }
    
    @Override
    public String toString()
    {
        return "ClassMsgBean [className=" + className + ", method=" + method + ", argsType=" + Arrays.toString(argsType)
                + ", args=" + Arrays.toString(args) + "]";
    }
    
}
