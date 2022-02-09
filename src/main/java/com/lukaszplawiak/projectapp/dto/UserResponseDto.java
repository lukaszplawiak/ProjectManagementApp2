package com.lukaszplawiak.projectapp.dto;

import com.lukaszplawiak.projectapp.model.Role;

import java.util.ArrayList;
import java.util.Collection;

public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Collection<Role> roles = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public static final class UserResponseDtoBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private Collection<Role> roles = new ArrayList<>();

        private UserResponseDtoBuilder() {
        }

        public static UserResponseDtoBuilder anUserResponseDto() {
            return new UserResponseDtoBuilder();
        }

        public UserResponseDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public UserResponseDtoBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserResponseDtoBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserResponseDtoBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserResponseDtoBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserResponseDtoBuilder withRoles(Collection<Role> roles) {
            this.roles = roles;
            return this;
        }

        public UserResponseDto build() {
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.lastName = this.lastName;
            userResponseDto.id = this.id;
            userResponseDto.password = this.password;
            userResponseDto.email = this.email;
            userResponseDto.firstName = this.firstName;
            userResponseDto.roles = this.roles;
            return userResponseDto;
        }
    }
}