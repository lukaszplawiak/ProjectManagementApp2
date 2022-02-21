package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.controller.config.TokenSample;
import com.lukaszplawiak.projectapp.report.ReportProperties;
import com.lukaszplawiak.projectapp.report.ReportType;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.lukaszplawiak.projectapp.controller.config.TestObjectMapper.asJsonString;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReportControllerTest extends ControllerTestBase {

    @Test
    void getReport_WhenUserWithRoleUser_ShouldReturnedForbidden() throws Exception {
        ReportProperties reportProperties = new ReportProperties(ReportType.ALL_USERS, false, null, null);
        mockMvc.perform(post("/api/v1/reports")
                        .content(asJsonString(reportProperties))
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_USER)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void getReport_WhenUserWithRoleManager_ShouldReturnedAllUsersPdf() throws Exception {
        ReportProperties reportProperties = new ReportProperties(ReportType.ALL_USERS, false, null, null);
        mockMvc.perform(post("/api/v1/reports")
                        .content(asJsonString(reportProperties))
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_MANAGER)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void getReport_WhenUserWithRoleAdmin_ShouldReturnedAllUsersPdf() throws Exception {
        ReportProperties reportProperties = new ReportProperties(ReportType.ALL_USERS, false, null, null);
        mockMvc.perform(post("/api/v1/reports")
                        .content(asJsonString(reportProperties))
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_ADMIN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void getReport_WhenUserWithRoleAdminHaveExpiredToken_ShouldNotReturnedPdf() throws Exception {
        mockMvc.perform(get("/api/v1/reports")
                        .header(AUTHORIZATION, TokenSample.EXPIRED_TOKEN_ROLE_ADMIN)
                        .contentType(MediaType.APPLICATION_PDF))
                .andExpect(status().is(401));
    }

    @Test
    void getReport_WhenUserWithRoleSuperAdmin_ShouldReturnedAllUsersPdf() throws Exception {
        ReportProperties reportProperties = new ReportProperties(ReportType.ALL_USERS, false, null, null);
        mockMvc.perform(post("/api/v1/reports")
                        .content(asJsonString(reportProperties))
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_SUPER_ADMIN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void getReport_WhenUserWithRoleSuperAdminHaveExpiredToken_ShouldNotReturnedPdf() throws Exception {
        mockMvc.perform(get("/api/v1/reports")
                        .header(AUTHORIZATION, TokenSample.EXPIRED_TOKEN_ROLE_SUPER_ADMIN)
                        .contentType(MediaType.APPLICATION_PDF))
                .andExpect(status().is(401));
    }

    @Test
    void getReport_WhenUserWithRoleManager_ShouldReturnedAllProjectsPdf() throws Exception {
        ReportProperties reportProperties = new ReportProperties(ReportType.ALL_PROJECTS, false, null, null);
        mockMvc.perform(post("/api/v1/reports")
                        .content(asJsonString(reportProperties))
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_MANAGER)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void getReport_WhenUserWithRoleManager_ShouldReturnedDoneProjectsPdf() throws Exception {
        ReportProperties reportProperties = new ReportProperties(ReportType.DONE_PROJECTS, false, null, null);
        mockMvc.perform(post("/api/v1/reports")
                        .content(asJsonString(reportProperties))
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_MANAGER)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void getReport_WhenUserWithRoleManager_ShouldReturnedDetailsProjectPdf() throws Exception {
        ReportProperties reportProperties = new ReportProperties(ReportType.DETAILS_PROJECT, false, 3L, null);
        mockMvc.perform(post("/api/v1/reports")
                        .content(asJsonString(reportProperties))
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_MANAGER)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void getReport_WhenUserWithRoleManager_ShouldReturnedDetailsUserPdf() throws Exception {
        ReportProperties reportProperties = new ReportProperties(ReportType.DETAILS_USER, false, null, "geraltrivi@gmail.com");
        mockMvc.perform(post("/api/v1/reports")
                        .content(asJsonString(reportProperties))
                        .header(AUTHORIZATION, TokenSample.VALID_TOKEN_ROLE_MANAGER)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }
}