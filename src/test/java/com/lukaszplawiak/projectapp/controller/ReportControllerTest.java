package com.lukaszplawiak.projectapp.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Clock;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukaszplawiak.projectapp.controller.config.TokenSample;
import com.lukaszplawiak.projectapp.service.UserService;
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
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReportControllerTest {

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
    void getReport_WhenUserWithRoleUser_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/reports/ALL_USERS").header(AUTHORIZATION, tokenSample.validTokenWithRole_User())
                        .contentType(MediaType.APPLICATION_PDF))
                .andExpect(status().is(403));
    }

    @Test
    void getReport_WhenUserWithRoleManager_ShouldReturnedPdf() throws Exception {
        mockMvc.perform(get("/api/v1/reports/ALL_USERS").header(AUTHORIZATION, tokenSample.validTokenWithRole_Manager())
                        .contentType(MediaType.APPLICATION_PDF))
                .andExpect(status().is(200));
    }

    @Test
    void getReport_WhenUserWithRoleAdmin_ShouldReturnedPdf() throws Exception {
        mockMvc.perform(get("/api/v1/reports/ALL_PROJECTS").header(AUTHORIZATION, tokenSample.validTokenWithRole_Admin())
                        .contentType(MediaType.APPLICATION_PDF))
                .andExpect(status().is(200));
    }

    @Test
    void getReport_WhenUserWithRoleSuperAdmin_ShouldReturnedPdf() throws Exception {
        mockMvc.perform(get("/api/v1/reports/DONE_PROJECTS").header(AUTHORIZATION, tokenSample.validTokenWithRole_Super_Admin())
                        .contentType(MediaType.APPLICATION_PDF))
                .andExpect(status().is(200));
    }
}