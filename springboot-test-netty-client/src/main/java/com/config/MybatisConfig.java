package com.config;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@MapperScan(basePackages =
{ "com.dao", "com.dao2" })
public class MybatisConfig
{
    
    @Bean
    @ConfigurationProperties("service.c3p0")
    public DataSource dataSource()
    {
        return new ComboPooledDataSource();
        
        // DataSource ds =
        // DataSourceBuilder.create().type(ComboPooledDataSource.class).build();
        // return ds;
    }
}
