package com.my.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.repository.support.CustomRepositoryImpl;

@Configuration
@EnableJpaRepositories(basePackages = "com.my.repository", repositoryBaseClass = CustomRepositoryImpl.class)
public class CustomRepositoryConfig
{
}
