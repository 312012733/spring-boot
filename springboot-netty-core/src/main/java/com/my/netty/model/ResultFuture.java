package com.my.netty.model;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class ResultFuture
{
    private static final Logger LOG = LoggerFactory.getLogger(ResultFuture.class);
    
    private static final ConcurrentHashMap<Long, ResultFuture> RESULT_FUTURE_CACHE = new ConcurrentHashMap<>();
    
    private Lock lock;
    private Condition condition;
    
    private Object result;
    
    public ResultFuture(NettyRequest request)
    {
        Assert.notNull(request, "nettyRequest must be not null");
        
        lock = new ReentrantLock();
        condition = lock.newCondition();
        RESULT_FUTURE_CACHE.put(request.getId(), this);
    }
    
    public Object get()
    {
        try
        {
            lock.lock();
            
            while (null == result)
            {
                condition.await();
            }
        }
        catch (Exception e)
        {
            LOG.error("", e);
        }
        finally
        {
            lock.unlock();
        }
        
        return result;
    }
    
    public static void buildResult(NettyResponse response)
    {
        Long id = response.getId();
        ResultFuture resultFuture = (ResultFuture) RESULT_FUTURE_CACHE.get(id);
        
        Assert.notNull(resultFuture, "resultFuture must be not null. id is " + id);
        
        try
        {
            resultFuture.lock.lock();
            resultFuture.result = response.getContent();
            resultFuture.condition.signalAll();
        }
        catch (Exception e)
        {
            LOG.error("", e);
        }
        finally
        {
            resultFuture.lock.unlock();
        }
        
    }
    
}
