package com.utils;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalUtils
{
    public static final String JDBC_CONNECTION_KEY = "jdbc_connection";
    
    private static final ThreadLocal<Map<String, Object>> CONTEXT = new ThreadLocal<>();
    
    public static void put(String key, Object value)
    {
        Map<String, Object> map = getMap();
        
        map.put(key, value);
    }
    
    public static Object get(String key)
    {
        Map<String, Object> map = getMap();
        
        return map.get(key);
    }
    
    public static void remove(String key)
    {
        Map<String, Object> map = getMap();
        map.remove(key);
    }
    
    private static Map<String, Object> getMap()
    {
        Map<String, Object> map = CONTEXT.get();
        
        if (null == map)
        {
            map = new HashMap<>();
            CONTEXT.set(map);
        }
        
        return map;
    }
    
}
