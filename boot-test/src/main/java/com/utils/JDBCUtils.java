package com.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JDBCUtils
{
    private static final String JDBC_URL = "url";
    private static final String JDBC_DRIVER = "jdbc.driverClassName";
    private static final String JDBC_USER_NAME = "jdbc.username";
    private static final String JDBC_PASSWORD = "jdbc.password";
    
    private static String url;
    private static String driver;
    private static String username;
    private static String password;
    
    static
    {
        try
        {
            System.out.println("--------static------invoke----------");
            Properties prop = new Properties();
            prop.load(JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties"));
            
            url = prop.getProperty(JDBC_URL);
            driver = prop.getProperty(JDBC_DRIVER);
            username = prop.getProperty(JDBC_USER_NAME);
            password = prop.getProperty(JDBC_PASSWORD);
            
            Class.forName(driver);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static Connection getCurrentConnection() throws Exception
    {
        Connection connection = (Connection) ThreadLocalUtils.get(ThreadLocalUtils.JDBC_CONNECTION_KEY);
        
        if (null == connection)
        {
            connection = openConnection();
            
            ThreadLocalUtils.put(ThreadLocalUtils.JDBC_CONNECTION_KEY, connection);
        }
        
        return connection;
    }
    
    public static Connection openConnection() throws Exception
    {
        Connection connection = DriverManager.getConnection(url, username, password);
        
        connection.setAutoCommit(false);
        
        return connection;
    }
    
    public static void execute(Connection connection, String sql, List<Object> params) throws Exception
    {
        PreparedStatement preparedStatement = getPreparedStatement(connection, sql, params);
        execute(preparedStatement);
    }
    
    public static <T> List<T> findList(Connection connection, String sql, List<Object> params, Class<T> entityType)
            throws Exception
    {
        PreparedStatement preparedStatement = getPreparedStatement(connection, sql, params);
        ResultSet resultSet = execute(preparedStatement);
        List<T> resultList = buildResultSet(resultSet, entityType);
        return resultList;
    }
    
    public static Object uniquResult(Connection connection, String sql, List<Object> params) throws Exception
    {
        PreparedStatement preparedStatement = getPreparedStatement(connection, sql, params);
        ResultSet resultSet = execute(preparedStatement);
        
        if (null == resultSet)
        {
            throw new SecurityException("sql is not query statement. ");
        }
        
        if (resultSet.next())
        {
            return resultSet.getObject(1);
        }
        
        return null;
    }
    
    public static <T> T findEntity(Connection connection, String sql, List<Object> params, Class<T> entityType)
            throws Exception
    {
        List<T> resultList = findList(connection, sql, params, entityType);
        
        if (null == resultList || resultList.isEmpty())
        {
            return null;
        }
        
        if (resultList.size() > 1)
        {
            throw new SecurityException("result is > 1");
        }
        
        return resultList.get(0);
    }
    
    private static PreparedStatement getPreparedStatement(Connection connection, String sql, List<Object> params)
            throws Exception
    {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        
        if (null != params && params.size() > 0)
        {
            int i = 1;
            
            for (Object param : params)
            {
                preparedStatement.setObject(i++, param);
            }
        }
        
        // LogUtils.debug("sql:" + sql + " -- params:" + params);
        
        return preparedStatement;
    }
    
    private static ResultSet execute(PreparedStatement preparedStatement) throws Exception
    {
        if (null == preparedStatement)
        {
            return null;
        }
        
        return preparedStatement.execute() ? preparedStatement.getResultSet() : null;
    }
    
    private static <T> List<T> buildResultSet(ResultSet resultSet, Class<T> entityType) throws Exception
    {
        if (null == resultSet)
        {
            return null;
        }
        
        List<T> resultList = new ArrayList<>();
        
        while (resultSet.next())
        {
            T entity = entityType.newInstance();
            
            Field[] filds = entityType.getDeclaredFields();
            
            for (Field field : filds)
            {
                try
                {
                    String columnName = MappingUtils.getColumNameByFieldName(field.getName(), entityType);
                    
                    Object result = null;
                    
                    if (field.getType().getName().equals(Boolean.class.getName())
                            || field.getType().getName().equals(boolean.class.getName()))
                    {
                        result = resultSet.getBoolean(columnName);
                    }
                    else
                    {
                        result = resultSet.getObject(columnName);
                    }
                    
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), entityType);
                    
                    Method method = pd.getWriteMethod();// pd.getReadMethod();
                    method.invoke(entity, result);
                }
                catch (Exception e)
                {
                    // LogUtils.error(entityType.getName() + "." +
                    // field.getName() + " is error. " + e.getMessage());
                }
            }
            
            resultList.add(entity);
            
        }
        
        return resultList;
    }
    
    public static void rollback(Connection connection) throws Exception
    {
        
        if (null != connection)
        {
            connection.rollback();
        }
    }
    
    public static void close(Connection connection) throws Exception
    {
        
        if (null != connection)
        {
            connection.close();
        }
    }
    
    public static void closeCurrent() throws Exception
    {
        Connection connection = (Connection) ThreadLocalUtils.get(ThreadLocalUtils.JDBC_CONNECTION_KEY);
        
        if (null != connection)
        {
            ThreadLocalUtils.remove(ThreadLocalUtils.JDBC_CONNECTION_KEY);
            connection.close();
        }
    }
    
    // public static void main(String[] args)
    // {
    
    // try
    // {
    // Connection connection = JDBCUtils.getConnection();
    //
    // String sql = "SELECT * from t_student s where s.student_name = ?";
    // List<Object> params = new ArrayList<>();
    // params.add("zhang3");
    //
    // List<Student> list = findList(connection, sql, params, Student.class);
    //
    // System.out.println(list);
    //
    // }
    // catch (Exception e)
    // {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    
    // }
}
