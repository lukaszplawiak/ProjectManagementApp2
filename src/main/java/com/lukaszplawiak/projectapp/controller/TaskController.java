package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.dto.TaskRequestDto;
import com.lukaszplawiak.projectapp.dto.TaskResponseDto;
import com.lukaszplawiak.projectapp.model.User;
import com.lukaszplawiak.projectapp.service.TaskService;
import com.lukaszplawiak.projectapp.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/projects/{projectId}/tasks")
class TaskController {
    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @Secured({"ROLE_USER", "ROLE_MANAGER"})
    @PostMapping
    ResponseEntity<TaskResponseDto> createTask(@RequestBody @Valid TaskRequestDto taskRequestDto,
                                              @PathVariable Long projectId, Authentication authentication) {
        String userEmail = authentication.getName();
        User user = userService.getUser(userEmail);
        return new ResponseEntity<>(taskService.createTask(projectId, taskRequestDto, user), HttpStatus.CREATED);
    }

    @Secured({"ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @GetMapping
    ResponseEntity<List<TaskResponseDto>> readTasksByProjectId(@PathVariable Long projectId, Pageable pageable) {
        return new ResponseEntity<>(taskService.getTasksDtoByProjectId(projectId, pageable), HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @GetMapping(path = "/{taskId}")
    ResponseEntity<TaskResponseDto> readTaskById(@PathVariable Long projectId, @PathVariable Long taskId) {
        return new ResponseEntity<>(taskService.getTaskById(projectId, taskId), HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @GetMapping(path = "/search")
    ResponseEntity<List<TaskResponseDto>> readTasksByDoneIsFalseAndProjectId(@PathVariable Long projectId, @RequestParam(defaultValue = "false") boolean done, Pageable pageable) {
        return new ResponseEntity<>(taskService.getTasksByDoneIsFalseAndProjectId(projectId, done, pageable), HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_MANAGER"})
    @PutMapping(path = "/{taskId}")
    ResponseEntity<TaskResponseDto> updateTaskById(@PathVariable Long projectId, @PathVariable Long taskId,
                                                  @RequestBody @Valid TaskRequestDto taskRequestDto, Authentication authentication) {
        String userEmail = authentication.getName();
        User user = userService.getUser(userEmail);
        TaskResponseDto updatedTask = taskService.updateTaskById(projectId, taskId, taskRequestDto, user);
        return new ResponseEntity<>(updatedTask,HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_MANAGER"})
    @PatchMapping(path = "/{taskId}")
    ResponseEntity<?> toggleTask(@PathVariable Long projectId, @PathVariable Long taskId,
                                 Authentication authentication) {
        String userEmail = authentication.getName();
        User user = userService.getUser(userEmail);
        taskService.toggleTask(projectId, taskId, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Secured({"ROLE_USER", "ROLE_MANAGER"})
    @DeleteMapping(path = "/{taskId}")
    ResponseEntity<?> deleteTaskById(@PathVariable Long projectId, @PathVariable Long taskId,
                                          Authentication authentication) {
        String userEmail = authentication.getName();
        User user = userService.getUser(userEmail);
        taskService.deleteTaskById(projectId, taskId, user);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}