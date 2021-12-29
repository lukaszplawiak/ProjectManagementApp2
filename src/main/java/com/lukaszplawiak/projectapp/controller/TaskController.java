package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.dto.TaskDto;
import com.lukaszplawiak.projectapp.service.TaskService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/projects/{projectId}/tasks")
class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    ResponseEntity<TaskDto> createTask(@RequestBody @Valid TaskDto taskDto, @PathVariable Long projectId) {
        return new ResponseEntity<>(taskService.createTask(projectId, taskDto), HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<List<TaskDto>> readTasksByProjectId(@PathVariable Long projectId, Pageable pageable) {
        return new ResponseEntity<>(taskService.getTasksByProject_Id(projectId, pageable), HttpStatus.OK);
    }
    @GetMapping(path = "/{taskId}")
    ResponseEntity<TaskDto> readTaskById( @PathVariable Long projectId, @PathVariable Long taskId) {
        return new ResponseEntity<>(taskService.getTaskById(projectId, taskId), HttpStatus.OK);
    }

    @GetMapping(path = "/search/done")
    ResponseEntity<List<TaskDto>> readTasksByDoneAndProjectId(@PathVariable Long projectId, boolean done, Pageable pageable) {
        return new ResponseEntity<>(taskService.getTasksByDoneAndProject_Id(projectId, done, pageable), HttpStatus.OK);
    }

    @PutMapping(path = "/{taskId}")
    ResponseEntity<TaskDto> updateTaskById(@PathVariable Long projectId, @PathVariable Long taskId, @RequestBody @Valid TaskDto taskDto) {
        TaskDto updatedTask = taskService.updateTaskById(projectId, taskId, taskDto);
        return new ResponseEntity<>(updatedTask,HttpStatus.OK);
    }

    @DeleteMapping(path = "/{taskId}")
    ResponseEntity<String> deleteTaskById(@PathVariable Long projectId, @PathVariable Long taskId) {
        taskService.deleteTaskById(projectId, taskId);
        return new ResponseEntity<>("Task of id: " + taskId + " deleted", HttpStatus.ACCEPTED);
    }
}
