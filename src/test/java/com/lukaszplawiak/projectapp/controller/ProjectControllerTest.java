package com.lukaszplawiak.projectapp.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Clock;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukaszplawiak.projectapp.controller.config.TokenSample;
import com.lukaszplawiak.projectapp.dto.ProjectRequestDto;
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
class ProjectControllerTest {

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
    void createProject_WhenUserWithRoleUser_ShouldReturnedForbidden() throws Exception {
        ProjectRequestDto project = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("Title")
                .withDescription("Description")
                .withDeadline(LocalDate.parse("2022-03-18"))
                .build();
        mockMvc.perform(post("/api/v1/projects").header(AUTHORIZATION, tokenSample.validTokenWithRole_User())
                        .content(asJsonString(project))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void createProject_WhenUserWithRoleManager_ShouldSaveProject() throws Exception {
        ProjectRequestDto project = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("Title")
                .withDescription("Description")
                .withDeadline(LocalDate.parse("2022-03-18"))
                .build();
        mockMvc.perform(post("/api/v1/projects").header(AUTHORIZATION, tokenSample.validTokenWithRole_Manager())
                        .content(asJsonString(project))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));
    }

    @Test
    void createProject_WhenUserWithRoleAdmin_ShouldReturnedForbidden() throws Exception {
        ProjectRequestDto project = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("Title")
                .withDescription("Description")
                .withDeadline(LocalDate.parse("2022-03-18"))
                .build();
        mockMvc.perform(post("/api/v1/projects").header(AUTHORIZATION, tokenSample.validTokenWithRole_Admin())
                        .content(asJsonString(project))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void createProject_WhenUserWithRoleSuperAdmin_ShouldReturnedForbidden() throws Exception {
        ProjectRequestDto project = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("Title")
                .withDescription("Description")
                .withDeadline(LocalDate.parse("2022-03-18"))
                .build();
        mockMvc.perform(post("/api/v1/projects").header(AUTHORIZATION, tokenSample.validTokenWithRole_Super_Admin())
                        .content(asJsonString(project))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void readProjectById_WhenUserWithRoleUser_ShouldReturnedData() throws Exception {
        mockMvc.perform(get("/api/v1/projects/1").header(AUTHORIZATION, tokenSample.validTokenWithRole_User())
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
        mockMvc.perform(get("/api/v1/projects/1").header(AUTHORIZATION, tokenSample.validTokenWithRole_Manager())
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
        mockMvc.perform(get("/api/v1/projects/1").header(AUTHORIZATION, tokenSample.validTokenWithRole_Admin())
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
        mockMvc.perform(get("/api/v1/projects/1").header(AUTHORIZATION, tokenSample.validTokenWithRole_Super_Admin())
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
        mockMvc.perform(get("/api/v1/projects").header(AUTHORIZATION, tokenSample.validTokenWithRole_User())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void readAllProjects_WhenUserWithRoleManager_ShouldReturnedData() throws Exception {
        mockMvc.perform(get("/api/v1/projects").header(AUTHORIZATION, tokenSample.validTokenWithRole_Manager())
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
        mockMvc.perform(get("/api/v1/projects").header(AUTHORIZATION, tokenSample.validTokenWithRole_Admin())
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
        mockMvc.perform(get("/api/v1/projects").header(AUTHORIZATION, tokenSample.validTokenWithRole_Super_Admin())
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
        mockMvc.perform(get("/api/v1/projects/search").header(AUTHORIZATION, tokenSample.validTokenWithRole_User())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void readProjectsByDone_WhenUserWithRoleManager_ShouldReturnedEmptyArray() throws Exception {
        mockMvc.perform(get("/api/v1/projects/search").header(AUTHORIZATION, tokenSample.validTokenWithRole_Manager())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void readProjectsByDone_WhenUserWithRoleAdmin_ShouldReturnedData() throws Exception {
        mockMvc.perform(get("/api/v1/projects/search?done=false").header(AUTHORIZATION, tokenSample.validTokenWithRole_Admin())
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
        mockMvc.perform(get("/api/v1/projects/search?done=false").header(AUTHORIZATION, tokenSample.validTokenWithRole_Super_Admin())
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
        ProjectRequestDto project = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("Title")
                .withDescription("Description")
                .withDeadline(LocalDate.parse("2022-03-18"))
                .build();
        mockMvc.perform(put("/api/v1/projects/2").header(AUTHORIZATION, tokenSample.validTokenWithRole_User())
                        .content(asJsonString(project))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

//    @Test
//    void updateProject_WhenUserWithRoleManager_Should() throws Exception {
//        ProjectRequestDto project = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
//                .withTitle("Title")
//                .withDescription("Description")
//                .withDeadline(LocalDate.parse("2022-03-18"))
//                .build();
//        mockMvc.perform(put("/api/v1/projects/2")
//
//                        .header(AUTHORIZATION, tokensSample.validTokenWithRole_Manager())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is(200));
//    }

    @Test
    void deleteProject_WhenUserWithRoleUser_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/projects/3")
                        .param("id","3")
                        .header(AUTHORIZATION, tokenSample.validTokenWithRole_User())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void deleteProject_WhenUserWithRoleManager_ShouldDeleted() throws Exception {
        mockMvc.perform(delete("/api/v1/projects/6")
                        .param("id","6")
                        .header(AUTHORIZATION, tokenSample.validTokenWithRole_Manager())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(204));
    }
}