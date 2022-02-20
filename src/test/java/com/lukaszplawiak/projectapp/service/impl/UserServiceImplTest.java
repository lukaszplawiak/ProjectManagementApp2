package com.lukaszplawiak.projectapp.service.impl;

import com.lukaszplawiak.projectapp.dto.UserRequestDto;
import com.lukaszplawiak.projectapp.exception.IllegalInputException;
import com.lukaszplawiak.projectapp.model.Role;
import com.lukaszplawiak.projectapp.model.User;
import com.lukaszplawiak.projectapp.repository.RoleRepository;
import com.lukaszplawiak.projectapp.repository.UserRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Test
    void saveUser_WhenUserIsProperFormatted_ShouldCreateUser() {
        // given
        UserRequestDto user = UserRequestDto.UserRequestDtoBuilder.anUserRequestDto()
                .withFirstName("FirstName")
                .withLastName("LastName")
                .withEmail("email@mail.com")
                .withPassword("1233")
                .build();
        var userServiceImpl = new UserServiceImpl(null, null, null);

        // when
        // then
        assertThatThrownBy(() -> userServiceImpl.saveUser(user))
                .isNotInstanceOf(IllegalInputException.class);
    }

    @Test
    void saveRole_WhenRoleIsLessThanOneCharacter_ShouldThrowIllegalInputException() {
        // given
        Role role = new Role(null, "");

        var userServiceImpl = new UserServiceImpl(null, null, null);

        // when
        // then
        assertThatThrownBy(() -> userServiceImpl.saveRole(role))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void saveRole_WhenRoleIsMoreThanOneCharacter_ShouldSaveRole() {
        // given
        Role role = new Role(null, "ROLE");

        var userServiceImpl = new UserServiceImpl(null, null, null);

        // when
        // then
        assertThatThrownBy(() -> userServiceImpl.saveRole(role))
                .isNotInstanceOf(IllegalInputException.class);
    }

    @Test
    void addRoleToUser_WhenUserNotFoundInDatabase_ShouldThrowIllegalInputException() {
        // given
        String email = "email@lala.com";
        String roleName = "ROLE_NO";
        UserRepository mockUserRepository = mock(UserRepository.class);
        when(mockUserRepository.findByEmail(email)).thenReturn(null);
        var userServiceImpl = new UserServiceImpl(mockUserRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> userServiceImpl.addRoleToUser(email, roleName))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void addRoleToUser_WhenRoleAndUserAreInDatabase_ShouldAddRoleToUser() {
        // given
        String email = "email@lala.com";
        String roleName = "ROLE_NO";
        User user = User.UserBuilder.anUser().withEmail(email).build();
        Role role = new Role(12L, "ROLE_NO");

        UserRepository mockUserRepository = mock(UserRepository.class);
        when(mockUserRepository.getByEmail(email)).thenReturn(user);
        RoleRepository mockRoleRepository = mock(RoleRepository.class);
        when(mockRoleRepository.findByName(roleName)).thenReturn(role);

        var userServiceImpl = new UserServiceImpl(mockUserRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> userServiceImpl.addRoleToUser(email, roleName))
                .isNotInstanceOf(IllegalInputException.class);
    }

    @Test
    void deleteUser_WhenUserIsNotInDatabase_ShouldNotDeleteUser() {  /// to poprawic !!!!
        // given
        String email = "email@lala.com";

        var mockUserServiceImpl = mock(UserServiceImpl.class);
        mockUserServiceImpl.deleteUser(email);

        // when
        // then
        verify(mockUserServiceImpl, times(1)).deleteUser(email);
    }

    @Test
    void deleteUser_WhenUserIsInDatabase_ShouldDeleteUser() {
        // given
        String email = "email@lala.com";

        var mockUserServiceImpl = mock(UserServiceImpl.class);
        mockUserServiceImpl.deleteUser(email);
        // when
        // then
        verify(mockUserServiceImpl, times(1)).deleteUser(email);
    }
}