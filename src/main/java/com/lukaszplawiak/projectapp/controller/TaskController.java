package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.dto.TaskReadDto;
import com.lukaszplawiak.projectapp.dto.TaskWriteDto;
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
    ResponseEntity<TaskWriteDto> createTask(@RequestBody @Valid TaskWriteDto taskWriteDto, @PathVariable Long projectId) {
        return new ResponseEntity<>(taskService.createTask(projectId, taskWriteDto), HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<List<TaskReadDto>> readTasksByProjectId(@PathVariable Long projectId, Pageable pageable) {
        return new ResponseEntity<>(taskService.getTasksByProject_Id(projectId, pageable), HttpStatus.OK);
    }
    @GetMapping(path = "/{taskId}")
    ResponseEntity<TaskReadDto> readTaskById(@PathVariable Long projectId, @PathVariable Long taskId) {
        return new ResponseEntity<>(taskService.getTaskById(projectId, taskId), HttpStatus.OK);
    }

    @GetMapping(path = "/search/done/false")
    ResponseEntity<List<TaskReadDto>> readTasksByDoneIsFalseAndProjectId(@PathVariable Long projectId, boolean done, Pageable pageable) {
        return new ResponseEntity<>(taskService.getTasksByDoneIsFalseAndProject_Id(projectId, done, pageable), HttpStatus.OK);
    }

    @PutMapping(path = "/{taskId}")
    ResponseEntity<TaskWriteDto> updateTaskById(@PathVariable Long projectId, @PathVariable Long taskId, @RequestBody @Valid TaskWriteDto taskWriteDto) {
        TaskWriteDto updatedTask = taskService.updateTaskById(projectId, taskId, taskWriteDto);
        return new ResponseEntity<>(updatedTask,HttpStatus.OK);
    }

    @PatchMapping(path = "/{taskId}")
    ResponseEntity<?> toggleTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        taskService.toggleTask(projectId, taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{taskId}")
    ResponseEntity<String> deleteTaskById(@PathVariable Long projectId, @PathVariable Long taskId) {
        taskService.deleteTaskById(projectId, taskId);
        return new ResponseEntity<>("Task of id: " + taskId + " deleted", HttpStatus.NO_CONTENT);
    }
}
