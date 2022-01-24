package com.lukaszplawiak.projectapp.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRequestDto {
    @NotBlank
    @Size(min = 2, max = 50, message = "First name must be 2 or more characters")
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 50, message = "Last name must be 2 or more characters")
    private String lastName;
    @Email
    private String email;
    @NotBlank
    @Size(min = 4, max = 50, message = "Password must be 4 or more characters")
    private String password;

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

    public static final class UserRequestDtoBuilder {
        private String firstName;
        private String lastName;
        private String email;
        private String password;

        private UserRequestDtoBuilder() {
        }

        public static UserRequestDtoBuilder anUserRequestDto() {
            return new UserRequestDtoBuilder();
        }

        public UserRequestDtoBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserRequestDtoBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserRequestDtoBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserRequestDtoBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserRequestDto build() {
            UserRequestDto userRequestDto = new UserRequestDto();
            userRequestDto.firstName = this.firstName;
            userRequestDto.lastName = this.lastName;
            userRequestDto.email = this.email;
            userRequestDto.password = this.password;
            return userRequestDto;
        }
    }
}
