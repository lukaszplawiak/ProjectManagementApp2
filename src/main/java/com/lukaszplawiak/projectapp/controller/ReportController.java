package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.model.Project;
import com.lukaszplawiak.projectapp.model.User;
import com.lukaszplawiak.projectapp.report.ProjectListReport;
import com.lukaszplawiak.projectapp.report.UserListReport;
import com.lukaszplawiak.projectapp.service.ProjectService;
import com.lukaszplawiak.projectapp.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/report")
class ReportController {
    private final UserService userService;
    private final ProjectService projectService;

    ReportController(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping(path = "/users")
    public void getReportUsersList(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
        String currentDateTime = dateFormat.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Users_List_Report_"+currentDateTime+".pdf";
        response.setHeader(headerKey, headerValue);
        List<User> users = userService.getUsers();
        UserListReport generator = new UserListReport();
        generator.setUsers(users);
        generator.generateUserList(response);
    }

    @GetMapping(path = "/projects")
    public void getReportProjectsList(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
        String currentDateTime = dateFormat.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Project_List_Report_"+currentDateTime+".pdf";
        response.setHeader(headerKey, headerValue);
        List<Project> projects = projectService.getAllProjects();
        ProjectListReport generator = new ProjectListReport();
        generator.setProjects(projects);
        generator.generateProjectList(response);
    }
}
