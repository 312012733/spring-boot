
package com.my.netty.spring.support;

import org.springframework.beans.factory.FactoryBean;

public class RemotApiFactoryBean<T> extends RemotApiSupport<T> implements FactoryBean<T>
{
    
    private Class<T> remotApiInterface;
    
    public RemotApiFactoryBean()
    {
        // intentionally empty
    }
    
    public RemotApiFactoryBean(Class<T> mapperInterface)
    {
        this.remotApiInterface = mapperInterface;
    }
    
    @Override
    public T getObject() throws Exception
    {
        return getRemotApi(this.remotApiInterface);
    }
    
    @Override
    public Class<T> getObjectType()
    {
        return this.remotApiInterface;
    }
    
    @Override
    public boolean isSingleton()
    {
        return true;
    }
    
    // ------------- mutators --------------
    
    public Class<T> getRemotApiInterface()
    {
        return remotApiInterface;
    }
    
    public void setRemotApiInterface(Class<T> remotApiInterface)
    {
        this.remotApiInterface = remotApiInterface;
    }
    
}
