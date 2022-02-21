package com.lukaszplawiak.projectapp.report;

import com.lukaszplawiak.projectapp.service.ProjectService;
import com.lukaszplawiak.projectapp.service.TaskService;
import com.lukaszplawiak.projectapp.service.UserService;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ReportGenerationService {
    private final UserService userService;
    private final ProjectService projectService;
    private final TaskService taskService;

    public ReportGenerationService(UserService userService, ProjectService projectService, TaskService taskService) {
        this.userService = userService;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    public File generateReport(ReportProperties reportProperties) {
        switch (reportProperties.getReportType()) {
            case ALL_USERS:
                return new AllUsersReportGenerator(userService.getUsers())
                        .generate();
            case ALL_PROJECTS:
                return new AllProjectsReportGenerator(projectService.getAllProjects())
                        .generate();
            case DONE_PROJECTS:
                return new DoneProjectsReportGenerator(projectService.getProjectByDone(reportProperties.isDone()))
                        .generate();
            case DETAILS_PROJECT:
                return new DetailsProjectReportGenerator(projectService.getProjectById(reportProperties.getId()),
                        taskService.getTasksByProjectId(reportProperties.getId()))
                        .generate();
            case DETAILS_USER:
                return new DetailsUserReportGenerator(userService.getUser(reportProperties.getEmail()),
                        projectService.getProjectById(reportProperties.getId()),
                        projectService.getAllProjects(),
                        taskService.getTasksByProjectId(reportProperties.getId()))
                        .generate();
            default:
                throw new IllegalArgumentException();
        }
    }
}