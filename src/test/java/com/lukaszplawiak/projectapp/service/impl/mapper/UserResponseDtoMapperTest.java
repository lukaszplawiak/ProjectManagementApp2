package com.lukaszplawiak.projectapp.service.impl.mapper;

import com.lukaszplawiak.projectapp.model.Role;
import com.lukaszplawiak.projectapp.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static com.lukaszplawiak.projectapp.service.impl.mapper.UserResponseDtoMapper.mapToUserResponseDto;
import static org.assertj.core.api.Assertions.assertThat;

class UserResponseDtoMapperTest {

    @Test
    void mapToUserResponseDto_WhenUserHaveRoles_ShouldBeMap() {
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
        assertThat(userResponseDto).isNotNull();
        assertThat(userResponseDto.getId()).isEqualTo(12L);
        assertThat(userResponseDto.getFirstName()).isEqualTo("First Name");
        assertThat(userResponseDto.getLastName()).isEqualTo("Last Name");
        assertThat(userResponseDto.getEmail()).isEqualTo("email@email.com");
        assertThat(userResponseDto.getPassword()).isEqualTo("1234");
        assertThat(userResponseDto.getRoles()).hasSize(2);
        assertThat(userResponseDto.getRoles()).contains(role1, role2);
    }

    @Test
    void mapToUserResponseDto_WhenUserDoNotHaveRoles_ShouldBeMap() {
        // given
        var user = new User(12L, "First Name", "Last Name", "email@email.com", "1234", null);

        // when
        var userResponseDto = mapToUserResponseDto(user);

        // then
        assertThat(userResponseDto).isNotNull();
        assertThat(userResponseDto.getId()).isEqualTo(12L);
        assertThat(userResponseDto.getFirstName()).isEqualTo("First Name");
        assertThat(userResponseDto.getLastName()).isEqualTo("Last Name");
        assertThat(userResponseDto.getEmail()).isEqualTo("email@email.com");
        assertThat(userResponseDto.getPassword()).isEqualTo("1234");
        assertThat(userResponseDto.getRoles()).isNull();
    }
}