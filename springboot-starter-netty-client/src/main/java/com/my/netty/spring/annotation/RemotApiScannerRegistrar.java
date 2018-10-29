package com.my.netty.spring.annotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.my.netty.spring.support.ClassPathRemotScanner;
import com.my.netty.spring.support.RemotApiFactoryBean;

public class RemotApiScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware
{
    
    private ResourceLoader resourceLoader;
    
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader)
    {
        this.resourceLoader = resourceLoader;
    }
    
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry)
    {
        AnnotationAttributes annoAttrs = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(RemotApiScan.class.getName()));
        ClassPathRemotScanner scanner = new ClassPathRemotScanner(registry);
        
        // this check is needed in Spring 3.1
        if (resourceLoader != null)
        {
            scanner.setResourceLoader(resourceLoader);
        }
        
        Class<? extends Annotation> annotationClass = annoAttrs.getClass("annotationClass");
        if (!Annotation.class.equals(annotationClass))
        {
            scanner.setAnnotationClass(annotationClass);
        }
        
        Class<?> markerInterface = annoAttrs.getClass("markerInterface");
        if (!Class.class.equals(markerInterface))
        {
            scanner.setMarkerInterface(markerInterface);
        }
        
        Class<? extends BeanNameGenerator> generatorClass = annoAttrs.getClass("nameGenerator");
        if (!BeanNameGenerator.class.equals(generatorClass))
        {
            scanner.setBeanNameGenerator(BeanUtils.instantiateClass(generatorClass));
        }
        
        Class<? extends RemotApiFactoryBean<?>> remotApiFactoryBeanClass = annoAttrs.getClass("factoryBean");
        if (!RemotApiFactoryBean.class.equals(remotApiFactoryBeanClass))
        {
            scanner.setRemotApiFactoryBean(BeanUtils.instantiateClass(remotApiFactoryBeanClass));
        }
        
        List<String> basePackages = new ArrayList<String>();
        for (String pkg : annoAttrs.getStringArray("value"))
        {
            if (StringUtils.hasText(pkg))
            {
                basePackages.add(pkg);
            }
        }
        for (String pkg : annoAttrs.getStringArray("basePackages"))
        {
            if (StringUtils.hasText(pkg))
            {
                basePackages.add(pkg);
            }
        }
        for (Class<?> clazz : annoAttrs.getClassArray("basePackageClasses"))
        {
            basePackages.add(ClassUtils.getPackageName(clazz));
        }
        scanner.registerFilters();
        scanner.doScan(StringUtils.toStringArray(basePackages));
    }
    
}
