package com.mybatis.tkmybatis.service;


import com.mybatis.tkmybatis.entity.User;

import java.util.List;

public interface UserService {
    public User getUser(int id);

    public void insertUser(User user);

    public List<User> getAllUser();

    public void updateUser(User user);

    public void deleteUser(int id);
}
