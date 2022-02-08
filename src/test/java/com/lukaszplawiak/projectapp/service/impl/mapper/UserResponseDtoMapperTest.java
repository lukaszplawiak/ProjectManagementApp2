package com.lukaszplawiak.projectapp.service.impl.mapper;

import com.lukaszplawiak.projectapp.dto.UserResponseDto;
import com.lukaszplawiak.projectapp.model.Role;
import com.lukaszplawiak.projectapp.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static com.lukaszplawiak.projectapp.service.impl.mapper.UserResponseDtoMapper.mapToUserResponseDto;
import static org.assertj.core.api.Assertions.assertThat;

class UserResponseDtoMapperTest {

    @Test
    void mapToUserResponseDtoShouldBeSuccessful() {
        // given
        var role1 = new Role(1L, "ROLE_USER");
        var role2 = new Role(1L, "ROLE_ADMIN");
        Collection<Role> roles = new ArrayList<>();
        roles.add(role1);
        roles.add(role2);
        var user = new User(12L, "First Name", "Last Name", "email@email.com", "1234", roles);

        // when
        var userResponseDto = mapToUserResponseDto(user);

        // then
        assertThat(userResponseDto)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", user.getId())
                .hasFieldOrPropertyWithValue("firstName", user.getFirstName())
                .hasFieldOrPropertyWithValue("lastName", user.getLastName())
                .hasFieldOrPropertyWithValue("email", user.getEmail())
                .hasFieldOrPropertyWithValue("password", user.getPassword())
                .hasFieldOrPropertyWithValue("roles", user.getRoles());
    }
}