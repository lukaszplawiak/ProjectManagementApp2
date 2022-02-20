package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.controller.config.TokenSample;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReportControllerTest extends ControllerTestBase {

    @Test
    void getReport_WhenUserWithRoleUser_ShouldReturnedForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/reports/ALL_USERS")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_USER)
                        .contentType(MediaType.APPLICATION_PDF))
                .andExpect(status().is(403));
    }

    @Test
    void getReport_WhenUserWithRoleManager_ShouldReturnedPdf() throws Exception {
        mockMvc.perform(get("/api/v1/reports/ALL_USERS")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_MANAGER)
                        .contentType(MediaType.APPLICATION_PDF))
                .andExpect(status().is(200));
    }

    @Test
    void getReport_WhenUserWithRoleAdmin_ShouldReturnedPdf() throws Exception {
        mockMvc.perform(get("/api/v1/reports/ALL_PROJECTS")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_ADMIN)
                        .contentType(MediaType.APPLICATION_PDF))
                .andExpect(status().is(200));
    }

    @Test
    void getReport_WhenUserWithRoleAdminHaveExpiredToken_ShouldNotReturnedPdf() throws Exception {
        mockMvc.perform(get("/api/v1/reports/ALL_PROJECTS")
                        .header(AUTHORIZATION, TokenSample.EXPIRED_TOKEN_ROLE_ADMIN)
                        .contentType(MediaType.APPLICATION_PDF))
                .andExpect(status().is(401));
    }

    @Test
    void getReport_WhenUserWithRoleSuperAdmin_ShouldReturnedPdf() throws Exception {
        mockMvc.perform(get("/api/v1/reports/DONE_PROJECTS")
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_SUPER_ADMIN)
                        .contentType(MediaType.APPLICATION_PDF))
                .andExpect(status().is(200));
    }

    @Test
    void getReport_WhenUserWithRoleSuperAdminHaveExpiredToken_ShouldNotReturnedPdf() throws Exception {
        mockMvc.perform(get("/api/v1/reports/DONE_PROJECTS")
                        .header(AUTHORIZATION, TokenSample.EXPIRED_TOKEN_ROLE_SUPER_ADMIN)
                        .contentType(MediaType.APPLICATION_PDF))
                .andExpect(status().is(401));
    }
}