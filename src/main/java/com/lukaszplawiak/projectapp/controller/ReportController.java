package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.model.Project;
import com.lukaszplawiak.projectapp.model.Task;
import com.lukaszplawiak.projectapp.model.User;
import com.lukaszplawiak.projectapp.report.AllProjectListReport;
import com.lukaszplawiak.projectapp.report.AllUserListReport;
import com.lukaszplawiak.projectapp.report.ProjectsTaskListReport;
import com.lukaszplawiak.projectapp.service.ProjectService;
import com.lukaszplawiak.projectapp.service.TaskService;
import com.lukaszplawiak.projectapp.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final TaskService taskService;

    ReportController(UserService userService, ProjectService projectService, TaskService taskService) {
        this.userService = userService;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @GetMapping(path = "/users")
    public void getReportUsersList(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
        String currentDateTime = dateFormat.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Employees_List_Report_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<User> users = userService.getUsers();
        AllUserListReport generator = new AllUserListReport();
        generator.setUsers(users);
        generator.generateUserList(response);
    }

    @GetMapping(path = "/projects")
    public void getReportProjectsList(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
        String currentDateTime = dateFormat.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Projects_List_Report_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<Project> projects = projectService.getAllProjects();
        AllProjectListReport generator = new AllProjectListReport();
        generator.setProjects(projects);
        generator.generateProjectList(response);
    }

    @GetMapping(path = "/projects/{id}")
    public void getReportProjectsTaskList(HttpServletResponse response, @PathVariable Long id) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
        String currentDateTime = dateFormat.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Project_Details_List_Report_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        Project projects = projectService.getProjectById(id);
        List<Task> tasks = taskService.getTasksByProjectId(id);
        ProjectsTaskListReport generator = new ProjectsTaskListReport();
        generator.setProjects(projects);
        generator.setTasks(tasks);
        generator.generateProjectsTaskList(response);
    }
}
