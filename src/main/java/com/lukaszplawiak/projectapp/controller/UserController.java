package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.dto.UserRequestDto;
import com.lukaszplawiak.projectapp.dto.UserResponseDto;
import com.lukaszplawiak.projectapp.model.Role;
import com.lukaszplawiak.projectapp.security.RoleAndUserForm;
import com.lukaszplawiak.projectapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/users")
    ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getDtoUsers(), HttpStatus.OK);
    }

    @GetMapping(path = "/roles")
    ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(userService.getRoles(), HttpStatus.OK);
    }

    @PostMapping(path = "/users/save")
    ResponseEntity<UserResponseDto> saveUser(@RequestBody UserRequestDto user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PostMapping(path = "/roles/save")
    ResponseEntity<Role> saveRole(@RequestBody Role role) {
        return new ResponseEntity<>(userService.saveRole(role), HttpStatus.CREATED);
    }

    @PostMapping(path = "/roles/addtouser")
    ResponseEntity<?> addRoleToUser(@RequestBody RoleAndUserForm form) {
        userService.addRoleToUser(form.getEmail(), form.getRoleName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/users/delete")
    ResponseEntity<String> deleteUser(@RequestBody RoleAndUserForm form) {
        userService.deleteUser(form.getEmail());
        return new ResponseEntity<>("Deleted user: " + form.getEmail(), HttpStatus.OK);
    }
}