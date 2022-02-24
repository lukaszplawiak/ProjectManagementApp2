package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.controller.config.TokenSample;
import com.lukaszplawiak.projectapp.dto.ProjectRequestDto;
import com.lukaszplawiak.projectapp.service.impl.UserServiceImpl;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.lukaszplawiak.projectapp.controller.config.TestObjectMapper.asJsonString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ProjectControllerTest extends ControllerTestBase {

    @Autowired
    UserServiceImpl userService;

    private ProjectRequestDto getProject() {
        return ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("Title")
                .withDescription("Description")
                .withDeadline(LocalDate.parse("2022-03-18"))
                .build();
    }

    @Test
    void createProject_WhenUserWithRoleUser_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/projects")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_USER)
                        .content(asJsonString(getProject()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void createProject_WhenUserWithRoleManager_ShouldSaveProject() throws Exception {
        mockMvc.perform(post("/api/v1/projects")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_MANAGER)
                        .content(asJsonString(getProject()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));

        assertThat(getProject().getTitle()).isEqualTo("Title");
        assertThat(getProject().getDescription()).isEqualTo("Description");
        assertThat(getProject().getDeadline()).isEqualTo(LocalDate.parse("2022-03-18"));
    }

    @Test
    void createProject_WhenUserWithRoleManagerHaveExpiredToken_ShouldNotSaveProject() throws Exception {
        mockMvc.perform(post("/api/v1/projects")
                        .header(AUTHORIZATION, TokenSample.EXPIRED_TOKEN_ROLE_MANAGER)
                        .content(asJsonString(getProject()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

    @Test
    void createProject_WhenUserWithRoleAdmin_ShouldReturnForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/projects")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_ADMIN)
                        .content(asJsonString(getProject()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void createProject_WhenUserWithRoleSuperAdmin_ShouldReturnForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/projects")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_SUPER_ADMIN)
                        .content(asJsonString(getProject()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void readProjectById_WhenUserWithRoleUser_ShouldReturnData() throws Exception {
        mockMvc.perform(get("/api/v1/projects/1")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_USER)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", Is.is(1)))
                .andExpect(jsonPath("$.title", Is.is("SOLID learn")))
                .andExpect(jsonPath("$.description", Is.is("Learn all 5 solid principles!")))
                .andExpect(jsonPath("$.deadline", Is.is("2022-02-15")));
    }

    @Test
    void readProjectById_WhenUserWithRoleManager_ShouldReturnedData() throws Exception {
        mockMvc.perform(get("/api/v1/projects/1")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_MANAGER)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", Is.is(1)))
                .andExpect(jsonPath("$.title", Is.is("SOLID learn")))
                .andExpect(jsonPath("$.description", Is.is("Learn all 5 solid principles!")))
                .andExpect(jsonPath("$.deadline", Is.is("2022-02-15")));
    }

    @Test
    void readProjectById_WhenUserWithRoleAdmin_ShouldReturnedData() throws Exception {
        mockMvc.perform(get("/api/v1/projects/1")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_ADMIN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", Is.is(1)))
                .andExpect(jsonPath("$.title", Is.is("SOLID learn")))
                .andExpect(jsonPath("$.description", Is.is("Learn all 5 solid principles!")))
                .andExpect(jsonPath("$.deadline", Is.is("2022-02-15")));
    }

    @Test
    void readProjectById_WhenUserWithRoleSuperAdmin_ShouldReturnedData() throws Exception {
        mockMvc.perform(get("/api/v1/projects/1")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_SUPER_ADMIN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", Is.is(1)))
                .andExpect(jsonPath("$.title", Is.is("SOLID learn")))
                .andExpect(jsonPath("$.description", Is.is("Learn all 5 solid principles!")))
                .andExpect(jsonPath("$.deadline", Is.is("2022-02-15")));
    }

    @Test
    void readAllProjects_WhenUserWithRoleUser_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/projects")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_USER)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void readAllProjects_WhenUserWithRoleManager_ShouldReturnedData() throws Exception {
        mockMvc.perform(get("/api/v1/projects")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_MANAGER)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].id", Is.is(1)))
                .andExpect(jsonPath("$.[0].title", Is.is("SOLID learn")))
                .andExpect(jsonPath("$.[0].description", Is.is("Learn all 5 solid principles!")))
                .andExpect(jsonPath("$.[0].deadline", Is.is("2022-02-15")));
    }

    @Test
    void readAllProjects_WhenUserWithRoleAdmin_ShouldReturnedData() throws Exception {
        mockMvc.perform(get("/api/v1/projects")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_ADMIN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].id", Is.is(1)))
                .andExpect(jsonPath("$.[0].title", Is.is("SOLID learn")))
                .andExpect(jsonPath("$.[0].description", Is.is("Learn all 5 solid principles!")))
                .andExpect(jsonPath("$.[0].deadline", Is.is("2022-02-15")));
    }

    @Test
    void readAllProjects_WhenUserWithRoleSuperAdmin_ShouldReturnedData() throws Exception {
        mockMvc.perform(get("/api/v1/projects")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_SUPER_ADMIN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].id", Is.is(1)))
                .andExpect(jsonPath("$.[0].title", Is.is("SOLID learn")))
                .andExpect(jsonPath("$.[0].description", Is.is("Learn all 5 solid principles!")))
                .andExpect(jsonPath("$.[0].deadline", Is.is("2022-02-15")));
    }

    @Test
    void readProjectsByDone_WhenUserWithRoleUser_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/projects/search")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_USER)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void readProjectsByDoneTrue_WhenUserWithRoleManager_ShouldReturnedEmptyArray() throws Exception {
        mockMvc.perform(get("/api/v1/projects/search")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_MANAGER)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void readProjectsByDoneFalse_WhenUserWithRoleAdmin_ShouldReturnedData() throws Exception {
        mockMvc.perform(get("/api/v1/projects/search?done=false")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_ADMIN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].id", Is.is(1)))
                .andExpect(jsonPath("$.[0].title", Is.is("SOLID learn")))
                .andExpect(jsonPath("$.[0].description", Is.is("Learn all 5 solid principles!")))
                .andExpect(jsonPath("$.[0].deadline", Is.is("2022-02-15")));
    }

    @Test
    void readProjectsByDone_WhenUserWithRoleSuperAdmin_ShouldReturnedData() throws Exception {
        mockMvc.perform(get("/api/v1/projects/search?done=false")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_SUPER_ADMIN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].id", Is.is(1)))
                .andExpect(jsonPath("$.[0].title", Is.is("SOLID learn")))
                .andExpect(jsonPath("$.[0].description", Is.is("Learn all 5 solid principles!")))
                .andExpect(jsonPath("$.[0].deadline", Is.is("2022-02-15")));
    }

    @Test
    void updateProject_WhenUserWithRoleUser_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(put("/api/v1/projects/2")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_USER)
                        .content(asJsonString(getProject()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void updateProject_WhenUserWithRoleManager_ShouldUpdateProject() throws Exception {
        ProjectRequestDto project = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("Title2")
                .withDescription("Description2")
                .withDeadline(LocalDate.parse("2022-09-19"))
                .withUser(userService.getUser("mickeymouse@gmail.com"))
                .build();
        mockMvc.perform(put("/api/v1/projects/3")
                        .content(asJsonString(project))
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_MANAGER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        assertThat(project).isNotNull();
        assertThat(project.getTitle()).isEqualTo("Title2");
        assertThat(project.getDescription()).isEqualTo("Description2");
        assertThat(project.getDeadline()).isEqualTo(LocalDate.parse("2022-09-19"));
    }

    @Test
    void updateProject_WhenUserWithRoleManagerHaveExpiredToken_ShouldNotUpdateProject() throws Exception {
        ProjectRequestDto project = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("Title2")
                .withDescription("Description2")
                .withDeadline(LocalDate.parse("2022-09-19"))
                .withUser(userService.getUser("mickeymouse@gmail.com"))
                .build();
        mockMvc.perform(put("/api/v1/projects/3")
                        .content(asJsonString(project))
                        .header(AUTHORIZATION, TokenSample.EXPIRED_TOKEN_ROLE_MANAGER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

    @Test
    void deleteProject_WhenUserWithRoleUser_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/projects/3")
                        .param("id", "3")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Transactional
    @Test
    void deleteProject_WhenUserWithRoleManager_ShouldDeleted() throws Exception {
        mockMvc.perform(delete("/api/v1/projects/6")
                        .param("id", "6")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_MANAGER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(204));
    }
}