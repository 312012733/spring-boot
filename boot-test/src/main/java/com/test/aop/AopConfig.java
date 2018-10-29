package com.test.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.test.aop")
@EnableAspectJAutoProxy
public class AopConfig
{
    @Pointcut("@annotation(com.test.aop.Action)")
    public void annotationPointCut()
    {
    }
    
    @Around(value = "annotationPointCut")
    public void around(ProceedingJoinPoint joinPoint)
    {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Action action = method.getAnnotation(Action.class);
        
        try
        {
            System.out.println(AopConfig.class.getSimpleName() + "...around.. before....invoke..." + action.name());
            
            joinPoint.proceed(joinPoint.getArgs());
            
            System.out.println(AopConfig.class.getSimpleName() + "...around.. after....invoke..." + action.name());
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
    
    @Around(value = "execution(* com.test.aop.*Service.*(..))")
    public void around2(ProceedingJoinPoint joinPoint)
    {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Action action = method.getAnnotation(Action.class);
        
        try
        {
            System.out.println(AopConfig.class.getSimpleName() + "...around2.. before....invoke..." + action.name());
            
            joinPoint.proceed(joinPoint.getArgs());
            
            System.out.println(AopConfig.class.getSimpleName() + "...around2.. after....invoke..." + action.name());
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
}
