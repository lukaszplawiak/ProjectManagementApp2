package com.lukaszplawiak.projectapp.service;

import com.lukaszplawiak.projectapp.model.Role;
import com.lukaszplawiak.projectapp.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUsers();
}
