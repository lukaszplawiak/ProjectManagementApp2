package com.lukaszplawiak.projectapp.warmup;

import com.lukaszplawiak.projectapp.dto.ProjectRequestDto;
import com.lukaszplawiak.projectapp.dto.TaskRequestDto;
import com.lukaszplawiak.projectapp.repository.UserRepository;
import com.lukaszplawiak.projectapp.service.ProjectService;
import com.lukaszplawiak.projectapp.service.TaskService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ProjectsWarmup implements ApplicationListener<ContextRefreshedEvent> {
    private final ProjectService projectService;
    private final TaskService taskService;
    private final UserRepository userRepository;

    public ProjectsWarmup(ProjectService projectService, TaskService taskService, UserRepository userRepository) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        projectService.createProject(ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("SOLID learn").withDescription("Learn all 5 solid principles!").withDeadline(LocalDate.parse("2022-02-15")).build(), userRepository.findByEmail("lukpla@gmail.com"));
        projectService.createProject(ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("KISS and DRY").withDescription("Remember what is all about!").withDeadline(LocalDate.parse("2022-02-15")).build(), userRepository.findByEmail("lukpla@gmail.com"));
        projectService.createProject(ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("TEST code").withDescription("Always writes tests and run tests before commit!").withDeadline(LocalDate.parse("2022-02-15")).build(), userRepository.findByEmail("johnsmith@gmail.com"));
        projectService.createProject(ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("SECURE yourself").withDescription("Security is most important non business features!").withDeadline(LocalDate.parse("2022-02-15")).build(), userRepository.findByEmail("johnsmith@gmail.com"));
        projectService.createProject(ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("CLIENT first").withDescription("Focus on client needs, not coders wants!").withDeadline(LocalDate.parse("2022-02-15")).build(), userRepository.findByEmail("geraltrivi@gmail.com"));

        taskService.createTask(1L, TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Task 1").withComment("Task 1 of project 1").withDeadline(LocalDate.parse("2022-03-15")).build(), userRepository.findByEmail("johnsmith@gmail.com"));
        taskService.createTask(1L, TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Task 2").withComment("Task 2 of project 1").withDeadline(LocalDate.parse("2022-03-15")).build(), userRepository.findByEmail("johnsmith@gmail.com"));
        taskService.createTask(1L, TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Task 3").withComment("Task 3 of project 1").withDeadline(LocalDate.parse("2022-03-15")).build(), userRepository.findByEmail("geraltrivi@gmail.com"));
        taskService.createTask(2L, TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Task 1").withComment("Task 1 of project 2").withDeadline(LocalDate.parse("2022-03-15")).build(), userRepository.findByEmail("lukpla@gmail.com"));
        taskService.createTask(2L, TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Task 2").withComment("Task 2 of project 2").withDeadline(LocalDate.parse("2022-03-15")).build(), userRepository.findByEmail("lukpla@gmail.com"));
        taskService.createTask(3L, TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Task 1").withComment("Task 1 of project 3").withDeadline(LocalDate.parse("2022-03-15")).build(), userRepository.findByEmail("johnsmith@gmail.com"));
        taskService.createTask(3L, TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Task 2").withComment("Task 2 of project 3").withDeadline(LocalDate.parse("2022-03-15")).build(), userRepository.findByEmail("lukpla@gmail.com"));
        taskService.createTask(3L, TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Task 3").withComment("Task 3 of project 3").withDeadline(LocalDate.parse("2022-03-15")).build(), userRepository.findByEmail("geraltrivi@gmail.com"));
        taskService.createTask(4L, TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Task 1").withComment("Task 1 of project 4").withDeadline(LocalDate.parse("2022-03-15")).build(), userRepository.findByEmail("lukpla@gmail.com"));
        taskService.createTask(4L, TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Task 2").withComment("Task 2 of project 4").withDeadline(LocalDate.parse("2022-03-15")).build(), userRepository.findByEmail("geraltrivi@gmail.com"));
        taskService.createTask(5L, TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Task 1").withComment("Task 1 of project 5").withDeadline(LocalDate.parse("2022-03-15")).build(), userRepository.findByEmail("geraltrivi@gmail.com"));
        taskService.createTask(5L, TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Task 2").withComment("Task 2 of project 5").withDeadline(LocalDate.parse("2022-03-15")).build(), userRepository.findByEmail("johnsmith@gmail.com"));
    }
}
