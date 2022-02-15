package com.lukaszplawiak.projectapp.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Clock;
import com.lukaszplawiak.projectapp.controller.config.TokensSample;
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
    TokensSample tokensSample;

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
    void readAllUsers_WhenUserWithRoleUserToFetchAllUsers_ShouldFetchAllUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users").header(AUTHORIZATION, tokensSample.validTokenWithRole_User()))
                .andExpect(status().is(403));
    }

    @Test
    void readAllUsers_WhenUserWithRoleUserToFetchAllUsers_ShouldFetchAllUsers2() throws Exception {
        mockMvc.perform(get("/api/v1/users").header(AUTHORIZATION, tokensSample.validTokenWithRole_User()))
                .andExpect(status().is(403));
    }
}