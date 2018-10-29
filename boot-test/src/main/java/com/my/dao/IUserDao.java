package com.my.dao;

import com.my.bean2.User;
import com.my.vo.UserDTO;

public interface IUserDao
{
    
    // select * from t_user u where u.user_name = ? and u.password = ?
    User findUser(String username, String password);
    
    User findUser2(UserDTO user);
    
    User findUser3(UserDTO user1, UserDTO user2);
    
    void deleteUserById(String userId);
    
    void addUser(User user);
    
    void updateUser(User user);
}
