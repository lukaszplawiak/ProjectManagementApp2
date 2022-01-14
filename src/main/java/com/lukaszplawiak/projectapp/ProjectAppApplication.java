package com.lukaszplawiak.projectapp;

import com.lukaszplawiak.projectapp.model.Role;
import com.lukaszplawiak.projectapp.model.User;
import com.lukaszplawiak.projectapp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class ProjectAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectAppApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
          userService.saveRole(new Role(null, "ROLE_USER"));
          userService.saveRole(new Role(null, "ROLE_MANAGER"));
          userService.saveRole(new Role(null, "ROLE_ADMIN"));
          userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

          userService.saveUser(new User(null, "Luk", "luk", "1234", new ArrayList<>()));
          userService.saveUser(new User(null, "Ada", "ada", "1234", new ArrayList<>()));
          userService.saveUser(new User(null, "Ula", "ula", "1234", new ArrayList<>()));
          userService.saveUser(new User(null, "Ala", "ala", "1234", new ArrayList<>()));

          userService.addRoleToUser("luk", "ROLE_USER");
          userService.addRoleToUser("luk", "ROLE_MANAGER");
          userService.addRoleToUser("ada", "ROLE_MANAGER");
          userService.addRoleToUser("ula", "ROLE_ADMIN");
          userService.addRoleToUser("ala", "ROLE_SUPER_ADMIN");
          userService.addRoleToUser("ala", "ROLE_ADMIN");
          userService.addRoleToUser("ala", "ROLE_USER");
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
