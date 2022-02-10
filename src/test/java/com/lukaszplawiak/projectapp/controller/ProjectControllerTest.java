package com.lukaszplawiak.projectapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukaszplawiak.projectapp.dto.ProjectRequestDto;
import com.lukaszplawiak.projectapp.dto.ProjectResponseDto;
import com.lukaszplawiak.projectapp.dto.UserRequestDto;
import com.lukaszplawiak.projectapp.model.Project;
import com.lukaszplawiak.projectapp.model.User;
import com.lukaszplawiak.projectapp.repository.ProjectRepository;
import com.lukaszplawiak.projectapp.repository.UserRepository;
import com.lukaszplawiak.projectapp.service.ProjectService;
import com.lukaszplawiak.projectapp.service.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static com.lukaszplawiak.projectapp.service.impl.mapper.UserEntityMapper.mapToUserEntity;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
class ProjectControllerTest {

//    @Autowired
//    private WebApplicationContext context;
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private ProjectService projectService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private MockMvc mvc;

//    @BeforeEach
//    public void setup() {
//        mvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }

//    @Test
//    void readProjectByIdShouldThrowIllegalAccessExceptionWith403WhenUserIsNotAuthorized() throws Exception {
//        // given
//        // when
//        mvc.perform(get("/api/v1/projects"))
//                .andExpect(status().is(403));
//
//        // then
//    }
//
//    @Test
//    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
//    void readProjectByIdShould() throws Exception {
//        // given
//
//        // when
//        mvc.perform(get("/api/v1/projects"))
//                .andExpect(status().is(200));
//
//        // then
//    }

//    @Test
//    void readProjectByIdShouldGetSingleProject() throws Exception {
//        // given
//        UserRequestDto userDto = UserRequestDto.UserRequestDtoBuilder.anUserRequestDto().withFirstName("Lukasz2").withLastName("Plawiak2").withEmail("lukpla3@gmail.com").withPassword("1234").build();
//        userService.saveUser(userDto);
//        User user = mapToUserEntity(userDto);
//        ProjectRequestDto projectDto = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto().withTitle("SOLID learn").withDescription("Learn all 5 solid principles!").withDeadline(LocalDate.parse("2022-02-15")).build();
//        ProjectResponseDto project1 = projectService.createProject(projectDto, user);
//
//
//        // when
//        ResultActions resultActions = mvc.perform(get("/api/v1/projects/" + project1.getId()))
//                .andExpect(status().is(200));
//
//        // then
//        Project project = objectMapper.readValue(resultActions.)
//    }

}