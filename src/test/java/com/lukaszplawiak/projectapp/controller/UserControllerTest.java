package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.controller.config.TokenSample;
import com.lukaszplawiak.projectapp.dto.UserRequestDto;
import com.lukaszplawiak.projectapp.model.Role;
import com.lukaszplawiak.projectapp.model.RoleAndUserForm;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static com.lukaszplawiak.projectapp.controller.config.TestObjectMapper.asJsonString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends ControllerTestBase {

    private UserRequestDto getUser() {
        return UserRequestDto.UserRequestDtoBuilder.anUserRequestDto()
                .withFirstName("First")
                .withLastName("Last")
                .withEmail("emailis@gmail.com")
                .withPassword("1234")
                .build();
    }

    @Test
    void saveUser_WhenUserWithRoleUser_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_USER)
                        .content(asJsonString(getUser()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void saveUser_WhenUserWithRoleManager_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_MANAGER)
                        .content(asJsonString(getUser()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void saveUser_WhenUserWithRoleAdmin_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_ADMIN)
                        .content(asJsonString(getUser()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));

        assertThat(getUser().getFirstName()).isEqualTo("First");
        assertThat(getUser().getLastName()).isEqualTo("Last");
        assertThat(getUser().getEmail()).isEqualTo("emailis@gmail.com");
    }

    @Test
    void saveUser_WhenUserWithRoleAdminHaveExpiredToken_ShouldNotCreateUser() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .header(AUTHORIZATION, TokenSample.EXPIRED_TOKEN_ROLE_ADMIN)
                        .content(asJsonString(getUser()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

    @Transactional
    @Test
    void saveUser_WhenUserWithRoleSuperAdmin_ShouldCreateUser() throws Exception {
        UserRequestDto user = UserRequestDto.UserRequestDtoBuilder.anUserRequestDto()
                .withFirstName("First2")
                .withLastName("Last2")
                .withEmail("emailis2@gmail.com")
                .withPassword("1234")
                .build();
        mockMvc.perform(post("/api/v1/users")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_SUPER_ADMIN)
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));

        assertThat(getUser().getFirstName()).isEqualTo("First");
        assertThat(getUser().getLastName()).isEqualTo("Last");
        assertThat(getUser().getEmail()).isEqualTo("emailis@gmail.com");
    }

    @Test
    void saveUser_WhenFirstNameIsLessThanOneCharacter_ShouldNotCreateUser() throws Exception {
        UserRequestDto user = UserRequestDto.UserRequestDtoBuilder.anUserRequestDto()
                .withFirstName("")
                .withLastName("Last")
                .withEmail("emailis2@gmail.com")
                .withPassword("1234")
                .build();
        mockMvc.perform(post("/api/v1/users")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_SUPER_ADMIN)
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    void saveUser_WhenLastNameIsLessThanOneCharacter_ShouldNotCreateUser() throws Exception {
        UserRequestDto user = UserRequestDto.UserRequestDtoBuilder.anUserRequestDto()
                .withFirstName("First")
                .withLastName("")
                .withEmail("emailis2@gmail.com")
                .withPassword("1234")
                .build();
        mockMvc.perform(post("/api/v1/users")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_SUPER_ADMIN)
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    void saveUser_WhenEmailIsNotProperlyFormatted_ShouldNotCreateUser() throws Exception {
        UserRequestDto user = UserRequestDto.UserRequestDtoBuilder.anUserRequestDto()
                .withFirstName("First")
                .withLastName("Last")
                .withEmail("emailis2gmail.com")
                .withPassword("1234")
                .build();
        mockMvc.perform(post("/api/v1/users")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_SUPER_ADMIN)
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    void saveUser_WhenPasswordIsShorterThan4Characters_ShouldNotCreateUser() throws Exception {
        UserRequestDto user = UserRequestDto.UserRequestDtoBuilder.anUserRequestDto()
                .withFirstName("First")
                .withLastName("Last")
                .withEmail("emailis@2gmail.com")
                .withPassword("123")
                .build();
        mockMvc.perform(post("/api/v1/users")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_SUPER_ADMIN)
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    void saveRole_WhenUserWithRoleUser_ShouldNotCreateRole() throws Exception {
        Role role = new Role(null, "ROLE_READER");
        mockMvc.perform(post("/api/v1/roles")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_USER)
                        .content(asJsonString(role))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void saveRole_WhenUserWithRoleManager_ShouldNotCreateRole() throws Exception {
        Role role = new Role(null, "ROLE_READER");
        mockMvc.perform(post("/api/v1/roles")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_MANAGER)
                        .content(asJsonString(role))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void saveRole_WhenUserWithRoleAdmin_ShouldNotCreateRole() throws Exception {
        Role role = new Role(null, "ROLE_READER");
        mockMvc.perform(post("/api/v1/roles")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_ADMIN)
                        .content(asJsonString(role))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void saveRole_WhenUserWithRoleSuperAdmin_ShouldCreateRole() throws Exception {
        Role role = new Role(null, "ROLE_READER");
        mockMvc.perform(post("/api/v1/roles")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_SUPER_ADMIN)
                        .content(asJsonString(role))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));
    }

    @Test
    void saveRole_WhenRoleNameIsEmpty_ShouldNotCreateRole() throws Exception {
        Role role = new Role(null, "");
        mockMvc.perform(post("/api/v1/roles")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_SUPER_ADMIN)
                        .content(asJsonString(role))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    void addRoleToUser_WhenUserRoleIsUser_ShouldNotAddRoleToUser() throws Exception {
        RoleAndUserForm form = new RoleAndUserForm("monaliza@gmail.com", "ROLE_ADMIN");
        mockMvc.perform(post("/api/v1/users/addtouser")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_USER)
                        .content(asJsonString(form))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void addRoleToUser_WhenUserRoleIsManager_ShouldNotAddRoleToUser() throws Exception {
        RoleAndUserForm form = new RoleAndUserForm("monaliza@gmail.com", "ROLE_ADMIN");
        mockMvc.perform(post("/api/v1/users/addtouser")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_MANAGER)
                        .content(asJsonString(form))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Transactional
    @Test
    void addRoleToUser_WhenUserRoleIsAdmin_ShouldAddRoleToUser() throws Exception {
        RoleAndUserForm form = new RoleAndUserForm("monaliza@gmail.com", "ROLE_ADMIN");
        mockMvc.perform(post("/api/v1/users/addtouser")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_ADMIN)
                        .content(asJsonString(form))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Transactional
    @Test
    void addRoleToUser_WhenUserRoleIsSuperAdmin_ShouldAddRoleToUser() throws Exception {
        RoleAndUserForm form = new RoleAndUserForm("monaliza@gmail.com", "ROLE_USER");
        mockMvc.perform(post("/api/v1/users/addtouser")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_SUPER_ADMIN)
                        .content(asJsonString(form))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void readAllUsers_WhenUserWithRoleUser_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_USER))
                .andExpect(status().is(403));
    }

    @Test
    void readAllUsers_WhenUserWithRoleManager_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_MANAGER))
                .andExpect(status().is(403));
    }

    @Test
    void readAllUsers_WhenUserWithRoleAdmin_ShouldReturnedUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_ADMIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void readAllUsers_WhenUserWithRoleSuperAdmin_ShouldReturnedUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_SUPER_ADMIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void readAllRoles_WhenUserWithRoleUser_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/roles")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_USER))
                .andExpect(status().is(403));
    }

    @Test
    void readAllRoles_WhenUserWithRoleManager_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/roles")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_MANAGER))
                .andExpect(status().is(403));
    }

    @Test
    void readAllRoles_WhenUserWithRoleAdmin_ShouldReturnedRoles() throws Exception {
        mockMvc.perform(get("/api/v1/roles")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_ADMIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void readAllRoles_WhenUserWithRoleSuperAdmin_ShouldReturnedRoles() throws Exception {
        mockMvc.perform(get("/api/v1/roles")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_SUPER_ADMIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }
}