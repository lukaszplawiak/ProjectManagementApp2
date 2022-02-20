package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.controller.config.TokenSample;
import com.lukaszplawiak.projectapp.dto.TaskRequestDto;
import com.lukaszplawiak.projectapp.service.impl.UserServiceImpl;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static com.lukaszplawiak.projectapp.controller.config.TestObjectMapper.asJsonString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TaskControllerTest extends ControllerTestBase {

    @Autowired
    UserServiceImpl userService;

    private TaskRequestDto getTask() {
        return TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Name")
                .withComment("Comment")
                .withDeadline(LocalDate.parse("2022-12-12"))
                .build();
    }

    @Test
    void createTask_WhenUserWithRoleUser_ShouldCreateTask() throws Exception {
        mockMvc.perform(post("/api/v1/projects/1/tasks")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_USER)
                        .content(asJsonString(getTask()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));

        assertThat(getTask().getName()).isEqualTo("Name");
        assertThat(getTask().getComment()).isEqualTo("Comment");
        assertThat(getTask().getDeadline()).isEqualTo(LocalDate.parse("2022-12-12"));
    }

    @Test
    void createTask_WhenUserWithRoleUserHaveExpiredToken_ShouldNotCreateTask() throws Exception {
        mockMvc.perform(post("/api/v1/projects/1/tasks")
                        .header(AUTHORIZATION, TokenSample.EXPIRED_TOKEN_ROLE_USER)
                        .content(asJsonString(getTask()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

    @Test
    void createTask_WhenUserWithRoleManager_ShouldCreateTask() throws Exception {
        mockMvc.perform(post("/api/v1/projects/1/tasks")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_MANAGER)
                        .content(asJsonString(getTask()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));

        assertThat(getTask().getName()).isEqualTo("Name");
        assertThat(getTask().getComment()).isEqualTo("Comment");
        assertThat(getTask().getDeadline()).isEqualTo(LocalDate.parse("2022-12-12"));
    }

    @Test
    void createTask_WhenUserWithRoleAdmin_ShouldNotCreateTask() throws Exception {
        mockMvc.perform(post("/api/v1/projects/1/tasks")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_ADMIN)
                        .content(asJsonString(getTask()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void readTasksByProjectId_WhenUserWithRoleUser_ShouldReturnedTasks() throws Exception {
        mockMvc.perform(get("/api/v1/projects/1/tasks")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_USER)
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
        mockMvc.perform(get("/api/v1/projects/1/tasks")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_MANAGER)
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
        mockMvc.perform(get("/api/v1/projects/1/tasks")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_ADMIN)
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
        mockMvc.perform(get("/api/v1/projects/1/tasks")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_SUPER_ADMIN)
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
        mockMvc.perform(get("/api/v1/projects/1/tasks/1")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_USER)
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
        mockMvc.perform(get("/api/v1/projects/1/tasks/1")

                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_MANAGER)
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
        mockMvc.perform(get("/api/v1/projects/1/tasks/1")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_ADMIN)
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
        mockMvc.perform(get("/api/v1/projects/1/tasks/1")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_SUPER_ADMIN)
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
        mockMvc.perform(get("/api/v1/projects/1/tasks/search")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_USER)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void readTasksByDoneIsFalseAndProjectId_WhenUserRoleIsManager_ShouldReturnedArray() throws Exception {
        mockMvc.perform(get("/api/v1/projects/1/tasks/search")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_MANAGER)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void readTasksByDoneIsFalseAndProjectId_WhenUserRoleIsAdmin_ShouldReturnedArray() throws Exception {
        mockMvc.perform(get("/api/v1/projects/1/tasks/search")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_ADMIN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void readTasksByDoneIsFalseAndProjectId_WhenUserRoleIsSuperAdmin_ShouldReturnedArray() throws Exception {
        mockMvc.perform(get("/api/v1/projects/1/tasks/search")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_SUPER_ADMIN)
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
        mockMvc.perform(put("/api/v1/projects/1/tasks/2")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_USER)
                        .content(asJsonString(task2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void updateTaskById_WhenUserIsOwnerOfTask_ShouldUpdateTask() throws Exception {
        TaskRequestDto task2 = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Name4")
                .withComment("Comment3")
                .withDeadline(LocalDate.parse("2022-12-12"))
                .withUser(userService.getUser("mickeymouse@gmail.com"))
                .build();
        mockMvc.perform(put("/api/v1/projects/1/tasks/3")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_MANAGER)
                        .content(asJsonString(task2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        assertThat(task2).isNotNull();
        assertThat(task2.getName()).isEqualTo("Name4");
        assertThat(task2.getComment()).isEqualTo("Comment3");
        assertThat(task2.getDeadline()).isEqualTo(LocalDate.parse("2022-12-12"));
    }

    @Test
    void updateTaskById_WhenUserIsOwnerOfTaskHaveExpiredToken_ShouldNotUpdateTask() throws Exception {
        TaskRequestDto task2 = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Name4")
                .withComment("Comment3")
                .withDeadline(LocalDate.parse("2022-12-12"))
                .withUser(userService.getUser("mickeymouse@gmail.com"))
                .build();
        mockMvc.perform(put("/api/v1/projects/1/tasks/3")
                        .header(AUTHORIZATION, TokenSample.EXPIRED_TOKEN_ROLE_MANAGER)
                        .content(asJsonString(task2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

    @Test
    void toggleTask_WhenUserIsNotOwnerOfTask_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(patch("/api/v1/projects/5/tasks/13")
                        .param("projectId","5")
                        .param("taskId","13")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_MANAGER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void toggleTask_WhenUserIsOwnerOfTask_ShouldToggleTask() throws Exception {
        mockMvc.perform(patch("/api/v1/projects/5/tasks/13")
                        .param("projectId","5")
                        .param("taskId","13")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(204));
    }

    @Test
    void deleteTaskById_WhenUserWithRoleUser_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/projects/3/tasks/6")
                        .param("projectId","3")
                        .param("taskId","6")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void deleteTaskById_WhenUserWithRoleManager_ShouldDeleted() throws Exception {
        mockMvc.perform(delete("/api/v1/projects/4/tasks/9")
                        .param("projectId","4")
                        .param("taskId","9")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }
}