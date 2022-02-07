package com.lukaszplawiak.projectapp.warmup;

import com.lukaszplawiak.projectapp.dto.UserRequestDto;
import com.lukaszplawiak.projectapp.model.Role;
import com.lukaszplawiak.projectapp.service.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class AppUsersWarmup implements ApplicationListener<ContextRefreshedEvent> {
    private final UserService userService;

    public AppUsersWarmup(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
//        userService.saveRole(new Role(null, "ROLE_USER"));
//        userService.saveRole(new Role(null, "ROLE_MANAGER"));
//        userService.saveRole(new Role(null, "ROLE_ADMIN"));
//        userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

        userService.saveUser(UserRequestDto.UserRequestDtoBuilder.anUserRequestDto().withFirstName("Lukasz").withLastName("Plawiak").withEmail("lukpla@gmail.com").withPassword("1234").build());
        userService.saveUser(UserRequestDto.UserRequestDtoBuilder.anUserRequestDto().withFirstName("John").withLastName("Smith").withEmail("johnsmith@gmail.com").withPassword("1234").build());
        userService.saveUser(UserRequestDto.UserRequestDtoBuilder.anUserRequestDto().withFirstName("Jan").withLastName("Kowlski").withEmail("jankowalski@gmail.com").withPassword("1234").build());
        userService.saveUser(UserRequestDto.UserRequestDtoBuilder.anUserRequestDto().withFirstName("Mickey").withLastName("Mouse").withEmail("mickeymouse@gmail.com").withPassword("1234").build());
        userService.saveUser(UserRequestDto.UserRequestDtoBuilder.anUserRequestDto().withFirstName("Geralt").withLastName("Rivi").withEmail("geraltrivi@gmail.com").withPassword("1234").build());
        userService.saveUser(UserRequestDto.UserRequestDtoBuilder.anUserRequestDto().withFirstName("Mona").withLastName("Liza").withEmail("monaliza@gmail.com").withPassword("1234").build());

        userService.addRoleToUser("lukpla@gmail.com", "ROLE_USER");
        userService.addRoleToUser("lukpla@gmail.com", "ROLE_MANAGER");
        userService.addRoleToUser("lukpla@gmail.com", "ROLE_ADMIN");
        userService.addRoleToUser("lukpla@gmail.com", "ROLE_SUPER_ADMIN");
        userService.addRoleToUser("johnsmith@gmail.com", "ROLE_USER");
        userService.addRoleToUser("jankowalski@gmail.com", "ROLE_MANAGER");
        userService.addRoleToUser("mickeymouse@gmail.com", "ROLE_ADMIN");
        userService.addRoleToUser("geraltrivi@gmail.com", "ROLE_USER");
        userService.addRoleToUser("geraltrivi@gmail.com", "ROLE_MANAGER");
        userService.addRoleToUser("monaliza@gmail.com", "ROLE_SUPER_ADMIN");
    }
}
