package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.model.Role;
import com.lukaszplawiak.projectapp.security.RoleAndUserForm;
import com.lukaszplawiak.projectapp.model.User;
import com.lukaszplawiak.projectapp.service.RefreshTokenService;
import com.lukaszplawiak.projectapp.service.UserService;
import com.lukaszplawiak.projectapp.service.impl.RefreshTokenServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
class UserController {
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    @GetMapping(path = "/users")
    ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping(path = "/users/save")
    ResponseEntity<User> saveUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PostMapping(path = "/roles/save")
    ResponseEntity<Role> saveUser(@RequestBody Role role) {
        return new ResponseEntity<>(userService.saveRole(role), HttpStatus.CREATED);
    }

    @PostMapping(path = "/roles/addtouser")
    ResponseEntity<?> addRoleToUser(@RequestBody RoleAndUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/users/delete")
    ResponseEntity<String> deleteUser(@RequestBody RoleAndUserForm form) {
        userService.deleteUser(form.getUsername());
        return new ResponseEntity<>("Deleted user: " + form.getUsername(), HttpStatus.OK);
    }

    @GetMapping(path = "/token/refresh")
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        refreshTokenService.refreshToken(request, response);
    }
}

