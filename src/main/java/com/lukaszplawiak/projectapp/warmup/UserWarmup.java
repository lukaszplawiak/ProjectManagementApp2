package com.lukaszplawiak.projectapp.warmup;

import com.lukaszplawiak.projectapp.dto.UserRequestDto;
import com.lukaszplawiak.projectapp.model.Role;
import com.lukaszplawiak.projectapp.service.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class UserWarmup implements ApplicationListener<ContextRefreshedEvent> {
    private final UserService userService;

    public UserWarmup(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
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
        userService.addRoleToUser("lukpla@gmail.com", "ROLE_SUPER_ADMIN");
        userService.addRoleToUser("adamala@gmail.com", "ROLE_USER");
        userService.addRoleToUser("ulalula@gmail.com", "ROLE_ADMIN");
        userService.addRoleToUser("alahala@gmail.com", "ROLE_SUPER_ADMIN");
        userService.addRoleToUser("alahala@gmail.com", "ROLE_ADMIN");
        userService.addRoleToUser("alahala@gmail.com", "ROLE_USER");
    }
}
