package com.yun.service;

import com.yun.domain.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    boolean regist(User user);

    boolean active(String code);

    User login(String username,String password);

    List<User> findByName(String name);
}
