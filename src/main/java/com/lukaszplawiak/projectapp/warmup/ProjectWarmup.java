package com.lukaszplawiak.projectapp.warmup;

import com.lukaszplawiak.projectapp.dto.ProjectRequestDto;
import com.lukaszplawiak.projectapp.dto.TaskRequestDto;
import com.lukaszplawiak.projectapp.service.ProjectService;
import com.lukaszplawiak.projectapp.service.TaskService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class ProjectWarmup implements ApplicationListener<ContextRefreshedEvent> {
    private final ProjectService projectService;
    private final TaskService taskService;

    public ProjectWarmup(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        projectService.createProject(ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto().withTitle("Project 1").withDescription("Desc of project 1").withDeadline(LocalDate.parse("2022-02-15")).build());
        projectService.createProject(ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto().withTitle("Project 2").withDescription("Desc of project 2").withDeadline(LocalDate.parse("2022-02-15")).build());
        projectService.createProject(ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto().withTitle("Project 3").withDescription("Desc of project 3").withDeadline(LocalDate.parse("2022-02-15")).build());
        projectService.createProject(ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto().withTitle("Project 4").withDescription("Desc of project 4").withDeadline(LocalDate.parse("2022-02-15")).build());

        taskService.createTask(1L, TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto().withName("Task 1").withComment("Task 1 of project 1").withDeadline(LocalDate.parse("2022-03-15")).build());
        taskService.createTask(1L, TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto().withName("Task 2").withComment("Task 2 of project 1").withDeadline(LocalDate.parse("2022-03-15")).build());
        taskService.createTask(1L, TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto().withName("Task 3").withComment("Task 3 of project 1").withDeadline(LocalDate.parse("2022-03-15")).build());
        taskService.createTask(2L, TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto().withName("Task 1").withComment("Task 1 of project 2").withDeadline(LocalDate.parse("2022-03-15")).build());
        taskService.createTask(2L, TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto().withName("Task 2").withComment("Task 2 of project 2").withDeadline(LocalDate.parse("2022-03-15")).build());
        taskService.createTask(3L, TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto().withName("Task 1").withComment("Task 1 of project 3").withDeadline(LocalDate.parse("2022-03-15")).build());
        taskService.createTask(3L, TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto().withName("Task 2").withComment("Task 2 of project 3").withDeadline(LocalDate.parse("2022-03-15")).build());
        taskService.createTask(3L, TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto().withName("Task 3").withComment("Task 3 of project 3").withDeadline(LocalDate.parse("2022-03-15")).build());
        taskService.createTask(4L, TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto().withName("Task 1").withComment("Task 1 of project 4").withDeadline(LocalDate.parse("2022-03-15")).build());
        taskService.createTask(4L, TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto().withName("Task 2").withComment("Task 2 of project 4").withDeadline(LocalDate.parse("2022-03-15")).build());
    }
}
