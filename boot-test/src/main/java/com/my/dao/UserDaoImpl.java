package com.my.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.my.bean2.User;
import com.my.vo.UserDTO;
import com.utils.HibernateUtils;

@Repository
public class UserDaoImpl implements IUserDao
{
    @Autowired
    private HibernateUtils hibernateUtils;
    
    @Override
    public User findUser(String username, String password)
    {
        String sql = "SELECT * from t_user u where u.password = :password and u.user_name = :username";
        
        Map<String, Object> condition = new HashMap<>();
        condition.put("username", username);
        condition.put("password", password);
        
        return hibernateUtils.findEntity(sql, condition, User.class);
    }
    
    @Override
    public User findUser2(UserDTO user)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public User findUser3(UserDTO user1, UserDTO user2)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void deleteUserById(String userId)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void addUser(User user)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void updateUser(User user)
    {
        // TODO Auto-generated method stub
        
    }
}
