package com.lukaszplawiak.projectapp.repository;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class UserRepositoryTest {

    @Test
    void deleteUserByEmail_WhenEmailIsInDb_ShouldDeleteUser() {
        // given
        String email = "email@gmail.com";
        UserRepository mockUserRepository = mock(UserRepository.class);

        // when
        mockUserRepository.deleteUserByEmail(email);

        // then
        verify(mockUserRepository, times(1)).deleteUserByEmail(email);
    }
}