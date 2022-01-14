package com.lukaszplawiak.projectapp.service;

import com.lukaszplawiak.projectapp.dto.UserRequestDto;
import com.lukaszplawiak.projectapp.dto.UserResponseDto;
import com.lukaszplawiak.projectapp.model.Role;
import com.lukaszplawiak.projectapp.model.User;

import java.util.List;

public interface UserService {
    User saveUser(UserRequestDto user);

    Role saveRole(Role role);

    User getUser(String email);

    List<UserResponseDto> getUsers();

    void addRoleToUser(String email, String roleName);

    void deleteUser(String email);
}
