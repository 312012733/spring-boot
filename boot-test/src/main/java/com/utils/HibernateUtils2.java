//package com.utils;
//
//import java.math.BigInteger;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.apache.log4j.Logger;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.query.Query;
//
//import com.my.bean2.User;
//import com.my.vo.Page;
//
//public class HibernateUtils2
//{
//    private static final Logger LOG = Logger.getLogger(HibernateUtils2.class);
//    
//    private static SessionFactory sessionFactory;
//    
//    static
//    {
//        try
//        {
//            // 1.加载配置
//            Configuration config = new Configuration().configure("hibernate.cfg.xml");
//            
//            // 2.SqlSessionFactory
//            sessionFactory = config.buildSessionFactory();
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//    
//    private static void temp()
//    {
//        Session session = sessionFactory.getCurrentSession();
//        
//        Transaction t = session.beginTransaction();
//        //
//        // Map<String, Object> condition = new HashMap<>();
//        // condition.put("id", "1");
//        // String sql = "SELECT * from t_user u where u.pk_id = :id";
//        // findEntits(sql, condition, User.class);
//        //
//        // session.get(User.class, "1");
//        
//        Query<User> query = session.createQuery("from User", User.class).setCacheable(true)
//                .setCacheRegion("sampleCache1");
//        
//        query.list();
//        
//        t.commit();
//        
//    }
//    
//    public static void main(String[] args) throws InterruptedException
//    {
//        System.out.println("-----------start sleep------------");
//        
//        temp();
//        temp();
//        temp();
//        temp();
//        
//        Thread.sleep(1 * 1000);
//        
//        temp();
//        
//        System.out.println("-----------end sleep------------");
//        
//        // t.commit();
//        
//        System.exit(0);
//    }
//    
//    public static SessionFactory getSessionFactory()
//    {
//        // 1.加载配置
//        Configuration config = new Configuration().configure("hibernate.cfg.xml");
//        
//        // 2.SqlSessionFactory
//        SessionFactory sessionFactory = config.buildSessionFactory();
//        
//        return sessionFactory;
//    }
//    
//    public static <T> Page<T> findByPage(String listSql, String countSql, Page<T> page, Map<String, Object> condition,
//            Class<T> entityType)
//    {
//        List<T> pageList = findEntits(listSql, page, condition, entityType);
//        
//        BigInteger count = uniqueResult(countSql, condition, BigInteger.class);
//        
//        page.setPageList(pageList);
//        
//        page.setMaxCount(count.longValue());
//        
//        return page;
//    }
//    
//    public static <T> List<T> findEntits(String sql, Map<String, Object> condition, Class<T> entityType)
//    {
//        return findEntits(sql, null, condition, entityType);
//    }
//    
//    public static <T> T findEntity(String sql, Map<String, Object> condition, Class<T> entityType)
//    {
//        
//        List<T> list = findEntits(sql, condition, entityType);
//        
//        if (null == list)
//        {
//            return null;
//        }
//        
//        if (list.size() > 1)
//        {
//            throw new SecurityException("result > 1");
//        }
//        
//        return list.size() > 0 ? list.get(0) : null;
//    }
//    
//    @SuppressWarnings("unchecked")
//    public static <T> T uniqueResult(String sql, Map<String, Object> condition, Class<T> resultType)
//    {
//        LOG.info(sql + " -- " + condition);
//        
//        Session session = sessionFactory.getCurrentSession();
//        
//        Query<?> query = session.createNativeQuery(sql);
//        
//        setParameter(condition, query);
//        
//        T result = (T) query.uniqueResult();
//        
//        return result;
//    }
//    
//    private static <T> List<T> findEntits(String sql, Page<T> page, Map<String, Object> condition, Class<T> entityType)
//    {
//        LOG.info(sql + " -- " + condition);
//        
//        Session session = sessionFactory.getCurrentSession();
//        
//        Query<T> query = session.createNativeQuery(sql, entityType).setCacheable(true);
//        
//        if (null != page)
//        {
//            query.setFirstResult(page.getOffset());
//            query.setMaxResults(page.getPageSize());
//        }
//        
//        setParameter(condition, query);
//        
//        List<T> listResult = query.getResultList();
//        
//        return listResult;
//        
//    }
//    
//    private static <T> void setParameter(Map<String, Object> condition, Query<T> query)
//    {
//        if (null != condition)
//        {
//            for (Entry<String, Object> entry : condition.entrySet())
//            {
//                query.setParameter(entry.getKey(), entry.getValue());
//            }
//        }
//    }
//    
//    public static void close(Session session)
//    {
//        if (null != session)
//        {
//            session.close();
//        }
//    }
//    
//}
