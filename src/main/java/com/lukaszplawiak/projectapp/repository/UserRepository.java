package com.lukaszplawiak.projectapp.repository;

import com.lukaszplawiak.projectapp.model.User;

import java.util.List;

public interface UserRepository {
    User findByUsername(String username);

    List<User> findAll();

    User save(User user);

    void deleteUserByUsername(String username);
}
