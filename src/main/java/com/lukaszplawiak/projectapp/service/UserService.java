package com.lukaszplawiak.projectapp.service;

import com.lukaszplawiak.projectapp.dto.UserRequestDto;
import com.lukaszplawiak.projectapp.dto.UserResponseDto;
import com.lukaszplawiak.projectapp.model.Role;
import com.lukaszplawiak.projectapp.model.User;

import java.util.List;

public interface UserService {
    UserResponseDto saveUser(UserRequestDto user);

    Role saveRole(Role role);

    User getUser(String email);

    List<User> getUsers();

    List<UserResponseDto> getDtoUsers();

    List<Role> getRoles();

    void addRoleToUser(String email, String roleName);
}