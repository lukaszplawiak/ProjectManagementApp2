package com.lukaszplawiak.projectapp.repository;

import com.lukaszplawiak.projectapp.controller.ControllerTestBase;
import com.lukaszplawiak.projectapp.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserRepositoryTest extends ControllerTestBase {

    @Autowired
    UserRepository userRepository;

    @Test
    void getByEmail_WhenUserIsInDatabase_ShouldGetUser() {
        // given
        // when
        User user = userRepository.getByEmail("johnsmith@gmail.com");

        // then
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("johnsmith@gmail.com");
    }
}