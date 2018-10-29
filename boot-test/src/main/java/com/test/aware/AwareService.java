package com.test.aware;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class AwareService implements BeanNameAware, ResourceLoaderAware
{
    private ResourceLoader resourceLoader;
    private String beanName;
    
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader)
    {
        this.resourceLoader = resourceLoader;
    }
    
    @Override
    public void setBeanName(String name)
    {
        this.beanName = name;
    }
    
    public void outputResult()
    {
        System.out.println("bean 的 名称：" + this.beanName);
        
        Resource resource = resourceLoader.getResource("classpath:jdbc.properties");
        
        try
        {
            System.out.println(IOUtils.toString(resource.getInputStream(), "utf-8"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
}
