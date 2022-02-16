package com.lukaszplawiak.projectapp.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Clock;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukaszplawiak.projectapp.controller.config.TokenSample;
import com.lukaszplawiak.projectapp.dto.TaskRequestDto;
import com.lukaszplawiak.projectapp.service.UserService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static com.lukaszplawiak.projectapp.controller.config.ObjectMapperConfig.asJsonString;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    FilterChainProxy filterChainProxy;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @MockBean
    Clock clock;

    @Autowired
    TokenSample tokenSample;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity(filterChainProxy))
                .build();
        when(clock.getToday()).thenReturn(Date.from(LocalDateTime.of(2022, 2, 15, 16, 13)
                .toInstant(ZoneOffset.UTC)));
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
    void createTask_WhenUserWithRoleUser_ShouldCreateTask() throws Exception {
        TaskRequestDto task1 = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Name1")
                .withComment("Comm1")
                .withDeadline(LocalDate.parse("2022-12-12"))
                .build();
        mockMvc.perform(post("/api/v1/projects/1/tasks").header(AUTHORIZATION, tokenSample.validTokenWithRole_User())
                        .content(asJsonString(task1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));
    }

    @Test
    void createTask_WhenUserWithRoleManager_ShouldCreateTask() throws Exception {
        TaskRequestDto task1 = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Name2")
                .withComment("Comm2")
                .withDeadline(LocalDate.parse("2022-12-12"))
                .build();
        mockMvc.perform(post("/api/v1/projects/1/tasks").header(AUTHORIZATION, tokenSample.validTokenWithRole_Manager())
                        .content(asJsonString(task1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));
    }

    @Test
    void createTask_WhenUserWithRoleAdmin_ShouldNotCreateTask() throws Exception {
        TaskRequestDto task1 = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Name2")
                .withComment("Comm2")
                .withDeadline(LocalDate.parse("2022-12-12"))
                .build();
        mockMvc.perform(post("/api/v1/projects/1/tasks").header(AUTHORIZATION, tokenSample.validTokenWithRole_Admin())
                        .content(asJsonString(task1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

//    @Test
//    void createTask_WhenUserWithRoleSuperAdmin_ShouldNotCreateTask() throws Exception {
//        TaskRequestDto task1 = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
//                .withName("Name2")
//                .withComment("Comm2")
//                .withDeadline(LocalDate.parse("2022-12-12"))
//                .build();
//        mockMvc.perform(post("/api/v1/projects/1/tasks").header(AUTHORIZATION, tokenSample.validTokenWithRole_Super_Admin())
//                        .content(asJsonString(task1))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is(403));
//    }

    @Test
    void readTasksByProjectId_WhenUserWithRoleUser_ShouldReturnedTasks() throws Exception {
        mockMvc.perform(get("/api/v1/projects/1/tasks").header(AUTHORIZATION, tokenSample.validTokenWithRole_User())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.[0].id", Is.is(1)))
                .andExpect(jsonPath("$.[0].name", Is.is("Task 1")))
                .andExpect(jsonPath("$.[0].comment", Is.is("Task 1 of project 1")))
                .andExpect(jsonPath("$.[0].deadline", Is.is("2022-03-15")));
    }

    @Test
    void readTasksByProjectId_WhenUserWithRoleManager_ShouldReturnedTasks() throws Exception {
        mockMvc.perform(get("/api/v1/projects/1/tasks").header(AUTHORIZATION, tokenSample.validTokenWithRole_Manager())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.[0].id", Is.is(1)))
                .andExpect(jsonPath("$.[0].name", Is.is("Task 1")))
                .andExpect(jsonPath("$.[0].comment", Is.is("Task 1 of project 1")))
                .andExpect(jsonPath("$.[0].deadline", Is.is("2022-03-15")));
    }

    @Test
    void readTasksByProjectId_WhenUserWithRoleAdmin_ShouldReturnedTasks() throws Exception {
        mockMvc.perform(get("/api/v1/projects/1/tasks").header(AUTHORIZATION, tokenSample.validTokenWithRole_Admin())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.[0].id", Is.is(1)))
                .andExpect(jsonPath("$.[0].name", Is.is("Task 1")))
                .andExpect(jsonPath("$.[0].comment", Is.is("Task 1 of project 1")))
                .andExpect(jsonPath("$.[0].deadline", Is.is("2022-03-15")));
    }

    @Test
    void readTasksByProjectId_WhenUserWithRoleSuperAdmin_ShouldReturnedTasks() throws Exception {
        mockMvc.perform(get("/api/v1/projects/1/tasks").header(AUTHORIZATION, tokenSample.validTokenWithRole_Super_Admin())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.[0].id", Is.is(1)))
                .andExpect(jsonPath("$.[0].name", Is.is("Task 1")))
                .andExpect(jsonPath("$.[0].comment", Is.is("Task 1 of project 1")))
                .andExpect(jsonPath("$.[0].deadline", Is.is("2022-03-15")));
    }

    @Test
    void readTaskById_WhenUserWithRoleUser_ShouldReturnedTasks() throws Exception {
        mockMvc.perform(get("/api/v1/projects/1/tasks/1").header(AUTHORIZATION, tokenSample.validTokenWithRole_User())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", Is.is(1)))
                .andExpect(jsonPath("$.name", Is.is("Task 1")))
                .andExpect(jsonPath("$.comment", Is.is("Task 1 of project 1")))
                .andExpect(jsonPath("$.deadline", Is.is("2022-03-15")));
    }

    @Test
    void readTaskById_WhenUserWithRoleManager_ShouldReturnedTasks() throws Exception {
        mockMvc.perform(get("/api/v1/projects/1/tasks/1").header(AUTHORIZATION, tokenSample.validTokenWithRole_Manager())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", Is.is(1)))
                .andExpect(jsonPath("$.name", Is.is("Task 1")))
                .andExpect(jsonPath("$.comment", Is.is("Task 1 of project 1")))
                .andExpect(jsonPath("$.deadline", Is.is("2022-03-15")));
    }

    @Test
    void readTaskById_WhenUserWithRoleAdmin_ShouldReturnedTasks() throws Exception {
        mockMvc.perform(get("/api/v1/projects/1/tasks/1").header(AUTHORIZATION, tokenSample.validTokenWithRole_Admin())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", Is.is(1)))
                .andExpect(jsonPath("$.name", Is.is("Task 1")))
                .andExpect(jsonPath("$.comment", Is.is("Task 1 of project 1")))
                .andExpect(jsonPath("$.deadline", Is.is("2022-03-15")));
    }

    @Test
    void readTaskById_WhenUserWithRoleSuperAdmin_ShouldReturnedTasks() throws Exception {
        mockMvc.perform(get("/api/v1/projects/1/tasks/1").header(AUTHORIZATION, tokenSample.validTokenWithRole_Super_Admin())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", Is.is(1)))
                .andExpect(jsonPath("$.name", Is.is("Task 1")))
                .andExpect(jsonPath("$.comment", Is.is("Task 1 of project 1")))
                .andExpect(jsonPath("$.deadline", Is.is("2022-03-15")));
    }

    @Test
    void readTasksByDoneIsFalseAndProjectId_WhenUserRoleIsUser_ShouldReturnedArray() throws Exception {
        mockMvc.perform(get("/api/v1/projects/1/tasks/search").header(AUTHORIZATION, tokenSample.validTokenWithRole_User())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void readTasksByDoneIsFalseAndProjectId_WhenUserRoleIsManager_ShouldReturnedArray() throws Exception {
        mockMvc.perform(get("/api/v1/projects/1/tasks/search").header(AUTHORIZATION, tokenSample.validTokenWithRole_Manager())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void readTasksByDoneIsFalseAndProjectId_WhenUserRoleIsAdmin_ShouldReturnedArray() throws Exception {
        mockMvc.perform(get("/api/v1/projects/1/tasks/search").header(AUTHORIZATION, tokenSample.validTokenWithRole_Admin())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void readTasksByDoneIsFalseAndProjectId_WhenUserRoleIsSuperAdmin_ShouldReturnedArray() throws Exception {
        mockMvc.perform(get("/api/v1/projects/1/tasks/search").header(AUTHORIZATION, tokenSample.validTokenWithRole_Super_Admin())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void updateTaskById_WhenUserIsNotOwnerOfTask_ShouldReturnedForbidden() throws Exception {
        TaskRequestDto task2 = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Name3")
                .withComment("Comment3")
                .withDeadline(LocalDate.parse("2022-12-12"))
                .build();
        mockMvc.perform(put("/api/v1/projects/1/tasks/2").header(AUTHORIZATION, tokenSample.validTokenWithRole_User())
                        .content(asJsonString(task2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

//    @Test
//    void updateTaskById_WhenUserIsOwnerOfTask_ShouldUpdateTask() throws Exception {
//        TaskRequestDto task2 = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
//                .withName("Name4")
//                .withComment("Comment3")
//                .withDeadline(LocalDate.parse("2022-12-12"))
//                .build();
//        mockMvc.perform(put("/api/v1/projects/1/tasks/3").header(AUTHORIZATION, tokenSample.validTokenWithRole_Manager())
//                        .content(asJsonString(task2))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is(204));
//    }

    @Test
    void toggleTask_WhenUserIsNotOwnerOfTask_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(patch("/api/v1/projects/5/tasks/13")
                        .param("projectId","5")
                        .param("taskId","13")
                        .header(AUTHORIZATION, tokenSample.validTokenWithRole_Manager())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void toggleTask_WhenUserIsOwnerOfTask_ShouldToggleTask() throws Exception {
        mockMvc.perform(patch("/api/v1/projects/5/tasks/13")
                        .param("projectId","5")
                        .param("taskId","13")
                        .header(AUTHORIZATION, tokenSample.validTokenWithRole_User())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(204));
    }

    @Test
    void deleteTaskById_WhenUserWithRoleUser_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/projects/3/tasks/6")
                        .param("projectId","3")
                        .param("taskId","6")
                        .header(AUTHORIZATION, tokenSample.validTokenWithRole_User())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void deleteTaskById_WhenUserWithRoleManager_ShouldDeleted() throws Exception {
        mockMvc.perform(delete("/api/v1/projects/4/tasks/9")
                        .param("projectId","4")
                        .param("taskId","9")
                        .header(AUTHORIZATION, tokenSample.validTokenWithRole_User())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }
}