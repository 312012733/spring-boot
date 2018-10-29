package com.test.el;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

@Configuration
@ComponentScan("com.test.el")
// @PropertySource("classpath:com/test/el/test.properties")
@PropertySources(value =
{ @PropertySource("classpath:com/test/el/test.properties"), @PropertySource("classpath:com/test/el/test2.properties") })
public class ElConfig
{
    @Value("I Love You !")
    private String normal;
    
    @Value("#{systemProperties['os.name']}")
    private String osName;
    
    @Value("#{ T(java.lang.Math).random() * 100.0}")
    private double randomNumber;
    
    @Value("#{demoService.another}")
    private String fromAnother;
    
    @Value("classpath:com/test/el/test.properties")
    private Resource testFile;
    
    @Value("http://www.baidu.com")
    private Resource testUrl;
    
    @Value("${book.name}")
    private String bookName;
    
    @Autowired
    private Environment environment;
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigure()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
    @Override
    public String toString()
    {
        try
        {
            return "ElConfig [normal=" + normal + ", osName=" + osName + ", randomNumber=" + randomNumber
                    + ", fromAnother=" + fromAnother + ", testFile="
                    + IOUtils.toString(testFile.getInputStream(), "utf-8") + ", testUrl="
                    + IOUtils.toString(testUrl.getInputStream(), "utf-8") + ", bookName=" + bookName + ", environment="
                    + environment.getProperty("book.author") + "]";
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
}
