package com.test.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class WindowsConditon implements Condition
{
    
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata)
    {
        return context.getEnvironment().getProperty("os.name").contains("Windows");
    }
}
