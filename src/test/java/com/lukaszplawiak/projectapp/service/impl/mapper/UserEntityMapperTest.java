package com.lukaszplawiak.projectapp.service.impl.mapper;

import com.lukaszplawiak.projectapp.dto.UserRequestDto;
import org.junit.jupiter.api.Test;

import static com.lukaszplawiak.projectapp.service.impl.mapper.UserEntityMapper.mapToUserEntity;
import static org.assertj.core.api.Assertions.assertThat;

class UserEntityMapperTest {

    @Test
    void mapToUserEntity_ShouldBeMap() {
        // given
        var userRequestDto = UserRequestDto.UserRequestDtoBuilder.anUserRequestDto()
                .withFirstName("First Name")
                .withLastName("Last Name")
                .withEmail("email@email.com")
                .withPassword("1234")
                .build();

        // when
        var user = mapToUserEntity(userRequestDto);

        // then
        assertThat(user).isNotNull();
        assertThat(user.getFirstName()).isEqualTo("First Name");
        assertThat(user.getLastName()).isEqualTo("Last Name");
        assertThat(user.getEmail()).isEqualTo("email@email.com");
        assertThat(user.getPassword()).isEqualTo("1234");
    }
}