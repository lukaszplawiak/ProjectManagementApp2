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

    public File generateReport(ReportType reportType, boolean done, String email , String projectId) { //
        Long parseId = Long.parseLong(projectId);
        switch (reportType) {
            case ALL_PROJECTS:
                return new AllProjectsReportGenerator(projectService.getAllProjects()).generate();
            case USERS:
                return new AllUsersReportGenerator(userService.getUsers()).generate();
            case DONE_PROJECTS:
                return new DoneProjectsReportGenerator(projectService.getProjectByDone(done)).generate();
            case DETAILS_PROJECT:
                return new DetailsProjectReportGenerator(projectService.getProjectById(parseId), taskService.getTasksByProjectId(parseId)).generate();
            case DETAILS_USER:
                return new DetailsUserReportGenerator(userService.getUser(email), projectService.getProjectById(parseId), projectService.getAllProjects(), taskService.getTasksByProjectId(parseId)).generate();
            default:
                throw new IllegalArgumentException();
        }
    }
//    public File generateReport(ReportType reportType, boolean done) {
//        switch (reportType) {
//            case ALL_PROJECTS:
//                return new AllProjectsReportGenerator(projectService.getAllProjects()).generate();
//            case USERS:
//                return new AllUsersReportGenerator(userService.getUsers()).generate();
//            case DONE_PROJECTS:
//                return new DoneProjectsReportGenerator(projectService.getProjectByDone(done)).generate();
//            default:
//                throw new IllegalArgumentException();
//        }
//    }
//
//    public File generateReport(ReportType reportType, String projectId, String email) {
//        Long parseId = Long.parseLong(projectId);
//        switch (reportType) {
//            case DETAILS_PROJECT:
//                return new DetailsProjectReportGenerator(projectService.getProjectById(parseId), taskService.getTasksByProjectId(parseId)).generate();
//            case DETAILS_USER:
//                return new DetailsUserReportGenerator(userService.getUser(email), projectService.getProjectById(parseId), projectService.getAllProjects(), taskService.getTasksByProjectId(parseId)).generate();
//            default:
//                throw new IllegalArgumentException();
//        }
//    }
}