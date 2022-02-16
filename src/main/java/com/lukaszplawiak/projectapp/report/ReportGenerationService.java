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

    public File generateReport(ReportType reportType, boolean done) {
        switch (reportType) {
            case ALL_USERS:
                return new AllUsersReportGenerator(userService.getUsers()).generate();
            case ALL_PROJECTS:
                return new AllProjectsReportGenerator(projectService.getAllProjects()).generate();
            case DONE_PROJECTS:
                return new DoneProjectsReportGenerator(projectService.getProjectByDone(done)).generate();
            default:
                throw new IllegalArgumentException();
        }
    }

    public File generateReport(ReportType reportType, String projectId) {
        Long parseId = Long.parseLong(projectId);
        if (reportType == ReportType.DETAILS_PROJECT) {
            return new DetailsProjectReportGenerator(projectService.getProjectById(parseId), taskService.getTasksByProjectId(parseId)).generate();
        }
        throw new IllegalArgumentException();
    }
}