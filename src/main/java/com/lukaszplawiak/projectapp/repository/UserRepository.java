package com.lukaszplawiak.projectapp.repository;

import com.lukaszplawiak.projectapp.model.User;

import java.util.List;

public interface UserRepository {
    User findByEmail(String email);
    List<User> findAll();
    User save(User user);
    void deleteUserByEmail(String email);
}