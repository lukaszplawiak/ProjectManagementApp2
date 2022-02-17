package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.dto.UserRequestDto;
import com.lukaszplawiak.projectapp.dto.UserResponseDto;
import com.lukaszplawiak.projectapp.model.Role;
import com.lukaszplawiak.projectapp.model.RoleAndUserForm;
import com.lukaszplawiak.projectapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @PostMapping(path = "/users/save")
    ResponseEntity<UserResponseDto> saveUser(@RequestBody UserRequestDto user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @Secured({"ROLE_SUPER_ADMIN"})
    @PostMapping(path = "/roles/save")
    ResponseEntity<Role> saveRole(@RequestBody Role role) {
        return new ResponseEntity<>(userService.saveRole(role), HttpStatus.CREATED);
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @PostMapping(path = "/roles/addtouser")
    ResponseEntity<?> addRoleToUser(@RequestBody RoleAndUserForm form) {
        userService.addRoleToUser(form.getEmail(), form.getRoleName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @GetMapping(path = "/users")
    ResponseEntity<List<UserResponseDto>> readAllUsers() {
        return new ResponseEntity<>(userService.getDtoUsers(), HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @GetMapping(path = "/roles")
    ResponseEntity<List<Role>> readAllRoles() {
        return new ResponseEntity<>(userService.getRoles(), HttpStatus.OK);
    }

    @Secured({"ROLE_SUPER_ADMIN"})
    @DeleteMapping(path = "/users/delete")
    ResponseEntity<?> deleteUser(@RequestBody RoleAndUserForm form) {
        userService.deleteUser(form.getEmail());
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}