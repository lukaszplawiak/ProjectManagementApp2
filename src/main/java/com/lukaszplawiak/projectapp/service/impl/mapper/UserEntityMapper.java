package com.lukaszplawiak.projectapp.service.impl.mapper;

import com.lukaszplawiak.projectapp.dto.UserRequestDto;
import com.lukaszplawiak.projectapp.model.User;

public class UserEntityMapper {
    public static User mapToUserEntity(UserRequestDto userRequestDto) {
        return User.UserBuilder.anUser()
                .withFirstName(userRequestDto.getFirstName())
                .withLastName(userRequestDto.getLastName())
                .withEmail(userRequestDto.getEmail())
                .withPassword(userRequestDto.getPassword())
                .build();
    }
}