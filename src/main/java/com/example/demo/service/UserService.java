package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {
    Iterable<User> findAllUser();
    User findById(Long id);
    boolean isUserExist(User user);
    void saveUser(User user);
    void deleteUserById(Long id);
}
