package com.lukaszplawiak.projectapp;

import com.lukaszplawiak.projectapp.dto.UserRequestDto;
import com.lukaszplawiak.projectapp.model.Role;
import com.lukaszplawiak.projectapp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

          userService.saveUser(UserRequestDto.UserRequestDtoBuilder.anUserRequestDto().withFirstName("Luk").withLastName("PLa").withEmail("lukpla@gmail.com").withPassword("1234").build());
          userService.saveUser(UserRequestDto.UserRequestDtoBuilder.anUserRequestDto().withFirstName("Ada").withLastName("Mala").withEmail("adamala@gmail.com").withPassword("1234").build());
          userService.saveUser(UserRequestDto.UserRequestDtoBuilder.anUserRequestDto().withFirstName("Ula").withLastName("Lula").withEmail("ulalula@gmail.com").withPassword("1234").build());
          userService.saveUser(UserRequestDto.UserRequestDtoBuilder.anUserRequestDto().withFirstName("Ala").withLastName("Hala").withEmail("alahala@gmail.com").withPassword("1234").build());


          userService.addRoleToUser("lukpla@gmail.com", "ROLE_USER");
          userService.addRoleToUser("lukpla@gmail.com", "ROLE_MANAGER");
          userService.addRoleToUser("lukpla@gmail.com", "ROLE_ADMIN");
          userService.addRoleToUser("adamala@gmail.com", "ROLE_MANAGER");
          userService.addRoleToUser("ulalula@gmail.com", "ROLE_ADMIN");
          userService.addRoleToUser("alahala@gmail.com", "ROLE_SUPER_ADMIN");
          userService.addRoleToUser("alahala@gmail.com", "ROLE_ADMIN");
          userService.addRoleToUser("alahala@gmail.com", "ROLE_USER");
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
