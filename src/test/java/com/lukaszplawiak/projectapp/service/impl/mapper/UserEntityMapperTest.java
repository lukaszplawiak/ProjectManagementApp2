package com.lukaszplawiak.projectapp.service.impl.mapper;

import com.lukaszplawiak.projectapp.dto.UserRequestDto;
import com.lukaszplawiak.projectapp.model.User;
import org.junit.jupiter.api.Test;

import static com.lukaszplawiak.projectapp.service.impl.mapper.UserEntityMapper.mapToUserEntity;
import static org.assertj.core.api.Assertions.assertThat;

class UserEntityMapperTest {

    @Test
    void mapToUserEntity_WhenInputDataIsCorrect_ShouldBeMap() {
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
        assertThat(user)
                .isNotNull()
                .hasFieldOrPropertyWithValue("firstName", userRequestDto.getFirstName())
                .hasFieldOrPropertyWithValue("lastName", userRequestDto.getLastName())
                .hasFieldOrPropertyWithValue("email", userRequestDto.getEmail())
                .hasFieldOrPropertyWithValue("password", userRequestDto.getPassword());
    }
}