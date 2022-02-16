package com.lukaszplawiak.projectapp.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Clock;
import com.lukaszplawiak.projectapp.controller.config.TokenSample;
import com.lukaszplawiak.projectapp.dto.UserRequestDto;
import com.lukaszplawiak.projectapp.model.Role;
import com.lukaszplawiak.projectapp.model.RoleAndUserForm;
import com.lukaszplawiak.projectapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static com.lukaszplawiak.projectapp.controller.config.ObjectMapperConfig.asJsonString;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    FilterChainProxy filterChainProxy;

    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @MockBean
    Clock clock;

    @Autowired
    TokenSample tokenSample;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity(filterChainProxy))
                .build();
        when(clock.getToday()).thenReturn(Date.from(LocalDateTime.of(2022, 2, 15, 16, 13).toInstant(ZoneOffset.UTC)));
    }

    @Configuration
    @EnableAutoConfiguration
    @ComponentScan("com.lukaszplawiak.projectapp")
    static class TestSecurityConfig {
        @Primary
        @Bean
        JWTVerifier jwtVerifier(Clock clock, @Value("${jwt.secret}") String secret) {
            JWTVerifier.BaseVerification verification = (JWTVerifier.BaseVerification) JWT.require(Algorithm.HMAC256(secret));
            return verification.build(clock);
        }
    }

    @Test
    void saveUser_WhenUserWithRoleUser_ShouldReturnedForbidden() throws Exception {
        UserRequestDto user = UserRequestDto.UserRequestDtoBuilder.anUserRequestDto()
                .withFirstName("Name")
                .withLastName("Last")
                .withEmail("emailis@gmail.com")
                .withPassword("1234")
                .build();
        mockMvc.perform(post("/api/v1/users/save").header(AUTHORIZATION, tokenSample.validTokenWithRole_User())
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void saveUser_WhenUserWithRoleManager_ShouldReturnedForbidden() throws Exception {
        UserRequestDto user = UserRequestDto.UserRequestDtoBuilder.anUserRequestDto()
                .withFirstName("Name")
                .withLastName("Last")
                .withEmail("emailis@gmail.com")
                .withPassword("1234")
                .build();
        mockMvc.perform(post("/api/v1/users/save").header(AUTHORIZATION, tokenSample.validTokenWithRole_Manager())
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void saveUser_WhenUserWithRoleAdmin_ShouldCreateUser() throws Exception {
        UserRequestDto user = UserRequestDto.UserRequestDtoBuilder.anUserRequestDto()
                .withFirstName("Name")
                .withLastName("Last")
                .withEmail("emailis@gmail.com")
                .withPassword("1234")
                .build();
        mockMvc.perform(post("/api/v1/users/save").header(AUTHORIZATION, tokenSample.validTokenWithRole_Admin())
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));
    }

    @Test
    void saveUser_WhenUserWithRoleSuperAdmin_ShouldCreateUser() throws Exception {
        UserRequestDto user = UserRequestDto.UserRequestDtoBuilder.anUserRequestDto()
                .withFirstName("First")
                .withLastName("Last")
                .withEmail("emailis2@gmail.com")
                .withPassword("1234")
                .build();
        mockMvc.perform(post("/api/v1/users/save").header(AUTHORIZATION, tokenSample.validTokenWithRole_Super_Admin())
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));
    }

    @Test
    void saveUser_WhenFirstNameIsLessThanOneCharacter_ShouldNotCreateUser() throws Exception {
        UserRequestDto user = UserRequestDto.UserRequestDtoBuilder.anUserRequestDto()
                .withFirstName("")
                .withLastName("Last")
                .withEmail("emailis2@gmail.com")
                .withPassword("1234")
                .build();
        mockMvc.perform(post("/api/v1/users/save").header(AUTHORIZATION, tokenSample.validTokenWithRole_Super_Admin())
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
        mockMvc.perform(post("/api/v1/users/save").header(AUTHORIZATION, tokenSample.validTokenWithRole_Super_Admin())
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
        mockMvc.perform(post("/api/v1/users/save").header(AUTHORIZATION, tokenSample.validTokenWithRole_Super_Admin())
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
        mockMvc.perform(post("/api/v1/users/save").header(AUTHORIZATION, tokenSample.validTokenWithRole_Super_Admin())
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    void saveRole_WhenUserWithRoleUser_ShouldNotCreateRole() throws Exception {
        Role role = new Role(null, "ROLE_READER");
        mockMvc.perform(post("/api/v1/roles/save").header(AUTHORIZATION, tokenSample.validTokenWithRole_User())
                        .content(asJsonString(role))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void saveRole_WhenUserWithRoleManager_ShouldNotCreateRole() throws Exception {
        Role role = new Role(null, "ROLE_READER");
        mockMvc.perform(post("/api/v1/roles/save").header(AUTHORIZATION, tokenSample.validTokenWithRole_Manager())
                        .content(asJsonString(role))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void saveRole_WhenUserWithRoleAdmin_ShouldNotCreateRole() throws Exception {
        Role role = new Role(null, "ROLE_READER");
        mockMvc.perform(post("/api/v1/roles/save").header(AUTHORIZATION, tokenSample.validTokenWithRole_Admin())
                        .content(asJsonString(role))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void saveRole_WhenUserWithRoleSuperAdmin_ShouldNotCreateRole() throws Exception {
        Role role = new Role(null, "ROLE_READER");
        mockMvc.perform(post("/api/v1/roles/save").header(AUTHORIZATION, tokenSample.validTokenWithRole_Super_Admin())
                        .content(asJsonString(role))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));
    }

    @Test
    void saveRole_WhenRoleNameIsEmpty_ShouldNotCreateRole() throws Exception {
        Role role = new Role(null, "");
        mockMvc.perform(post("/api/v1/roles/save").header(AUTHORIZATION, tokenSample.validTokenWithRole_Super_Admin())
                        .content(asJsonString(role))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    void addRoleToUser_WhenUserRoleIsUser_ShouldNotAddRoleToUser() throws Exception {
        RoleAndUserForm form = new RoleAndUserForm("monaliza@gmail.com", "ROLE_ADMIN");
        mockMvc.perform(post("/api/v1/roles/addtouser").header(AUTHORIZATION, tokenSample.validTokenWithRole_User())
                .content(asJsonString(form))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void addRoleToUser_WhenUserRoleIsManager_ShouldNotAddRoleToUser() throws Exception {
        RoleAndUserForm form = new RoleAndUserForm("monaliza@gmail.com", "ROLE_ADMIN");
        mockMvc.perform(post("/api/v1/roles/addtouser").header(AUTHORIZATION, tokenSample.validTokenWithRole_Manager())
                        .content(asJsonString(form))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void addRoleToUser_WhenUserRoleIsAdmin_ShouldAddRoleToUser() throws Exception {
        RoleAndUserForm form = new RoleAndUserForm("monaliza@gmail.com", "ROLE_ADMIN");
        mockMvc.perform(post("/api/v1/roles/addtouser").header(AUTHORIZATION, tokenSample.validTokenWithRole_Admin())
                        .content(asJsonString(form))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void addRoleToUser_WhenUserRoleIsSuperAdmin_ShouldAddRoleToUser() throws Exception {
        RoleAndUserForm form = new RoleAndUserForm("monaliza@gmail.com", "ROLE_USER");
        mockMvc.perform(post("/api/v1/roles/addtouser").header(AUTHORIZATION, tokenSample.validTokenWithRole_Super_Admin())
                        .content(asJsonString(form))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void readAllUsers_WhenUserWithRoleUser_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/users").header(AUTHORIZATION, tokenSample.validTokenWithRole_User()))
                .andExpect(status().is(403));
    }

    @Test
    void readAllUsers_WhenUserWithRoleManager_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/users").header(AUTHORIZATION, tokenSample.validTokenWithRole_Manager()))
                .andExpect(status().is(403));
    }

    @Test
    void readAllUsers_WhenUserWithRoleAdmin_ShouldReturnedUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users").header(AUTHORIZATION, tokenSample.validTokenWithRole_Admin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void readAllUsers_WhenUserWithRoleSuperAdmin_ShouldReturnedUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users").header(AUTHORIZATION, tokenSample.validTokenWithRole_Super_Admin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void readAllRoles_WhenUserWithRoleUser_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/roles").header(AUTHORIZATION, tokenSample.validTokenWithRole_User()))
                .andExpect(status().is(403));
    }

    @Test
    void readAllRoles_WhenUserWithRoleManager_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/roles").header(AUTHORIZATION, tokenSample.validTokenWithRole_Manager()))
                .andExpect(status().is(403));
    }

    @Test
    void readAllRoles_WhenUserWithRoleAdmin_ShouldReturnedRoles() throws Exception {
        mockMvc.perform(get("/api/v1/roles").header(AUTHORIZATION, tokenSample.validTokenWithRole_Admin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void readAllRoles_WhenUserWithRoleSuperAdmin_ShouldReturnedRoles() throws Exception {
        mockMvc.perform(get("/api/v1/roles").header(AUTHORIZATION, tokenSample.validTokenWithRole_Super_Admin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void deleteUser_WhenUserWithRoleUser_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1//users/delete")
                        .header(AUTHORIZATION, tokenSample.validTokenWithRole_User())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    void deleteUser_WhenUserWithRoleManager_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1//users/delete")
                        .header(AUTHORIZATION, tokenSample.validTokenWithRole_Manager())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    void deleteUser_WhenUserWithRoleAdmin_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1//users/delete")
                        .header(AUTHORIZATION, tokenSample.validTokenWithRole_Admin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    void deleteUser_WhenUserWithRoleSuperAdmin_ShouldDeleteUser() throws Exception {
        RoleAndUserForm form = new RoleAndUserForm("monaliza2@gmail.com", null);
        mockMvc.perform(delete("/api/v1//users/delete").header(AUTHORIZATION, tokenSample.validTokenWithRole_Super_Admin())
                        .content(asJsonString(form))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }





}