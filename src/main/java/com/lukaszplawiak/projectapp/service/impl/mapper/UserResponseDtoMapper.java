package com.lukaszplawiak.projectapp.service.impl.mapper;

import com.lukaszplawiak.projectapp.dto.UserResponseDto;
import com.lukaszplawiak.projectapp.model.User;

public class UserResponseDtoMapper {
    public static UserResponseDto mapToUserResponseDto(User user) {
        return UserResponseDto.UserResponseDtoBuilder.anUserResponseDto()
                .withId(user.getId())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withEmail(user.getEmail())
                .withPassword(user.getPassword())
                .withRoles(user.getRoles())
                .build();
    }
}
