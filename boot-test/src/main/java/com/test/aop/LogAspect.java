package com.test.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect
{
    @Pointcut("@annotation(com.test.aop.Action)")
    public void annotationPointCut()
    {
    }
    
    @Around(value = "annotationPointCut()")
    public void around1(ProceedingJoinPoint joinPoint)
    {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Action action = method.getAnnotation(Action.class);
        
        try
        {
            System.out.println(LogAspect.class.getSimpleName() + "...around1.. before....invoke..." + action.name());
            
            joinPoint.proceed(joinPoint.getArgs());
            
            System.out.println(LogAspect.class.getSimpleName() + "...around1.. after....invoke..." + action.name());
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
    
    @Around(value = "execution(* com.test.aop.*Service.*(..))")
    public void around2(ProceedingJoinPoint joinPoint)
    {
        // MethodSignature signature = (MethodSignature)
        // joinPoint.getSignature();
        // Method method = signature.getMethod();
        // Action action = method.getAnnotation(Action.class);
        
        try
        {
            System.out.println(LogAspect.class.getSimpleName() + "...around2.. before....invoke...");
            
            joinPoint.proceed(joinPoint.getArgs());
            
            System.out.println(LogAspect.class.getSimpleName() + "...around2.. after....invoke...");
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
    
    // @Around(value = "execution(* com.test.aop.*Service.*(..)) &&
    // @annotation(com.test.aop.Action)")
    @Around(value = "execution(* com.test.aop.*Service.*(..)) && annotationPointCut()")
    public void around3(ProceedingJoinPoint joinPoint)
    {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Action action = method.getAnnotation(Action.class);
        
        try
        {
            System.out.println(LogAspect.class.getSimpleName() + "...around3.. before....invoke..." + action.name());
            
            joinPoint.proceed(joinPoint.getArgs());
            
            System.out.println(LogAspect.class.getSimpleName() + "...around3.. after....invoke..." + action.name());
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
    
    @Around(value = "execution(* com.test.aop.*Service.*(..)) && @annotation(action123)")
    public void around4(ProceedingJoinPoint joinPoint, Action action123)
    {
        // MethodSignature signature = (MethodSignature)
        // joinPoint.getSignature();
        // Method method = signature.getMethod();
        // Action action = method.getAnnotation(Action.class);
        
        try
        {
            System.out.println(LogAspect.class.getSimpleName() + "...around4.. before....invoke..." + action123.name());
            
            joinPoint.proceed(joinPoint.getArgs());
            
            System.out.println(LogAspect.class.getSimpleName() + "...around4.. after....invoke..." + action123.name());
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
}
