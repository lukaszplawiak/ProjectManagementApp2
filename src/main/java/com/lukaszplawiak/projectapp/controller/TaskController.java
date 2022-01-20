package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.dto.TaskResponseDto;
import com.lukaszplawiak.projectapp.dto.TaskRequestDto;
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
    ResponseEntity<TaskRequestDto> createTask(@RequestBody @Valid TaskRequestDto taskRequestDto, @PathVariable Long projectId) {
        return new ResponseEntity<>(taskService.createTask(projectId, taskRequestDto), HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<List<TaskResponseDto>> readTasksByProjectId(@PathVariable Long projectId, Pageable pageable) {
        return new ResponseEntity<>(taskService.getTasksDtoByProjectId(projectId, pageable), HttpStatus.OK);
    }
    @GetMapping(path = "/{taskId}")
    ResponseEntity<TaskResponseDto> readTaskById(@PathVariable Long projectId, @PathVariable Long taskId) {
        return new ResponseEntity<>(taskService.getTaskById(projectId, taskId), HttpStatus.OK);
    }

    @GetMapping(path = "/search")
    ResponseEntity<List<TaskResponseDto>> readTasksByDoneIsFalseAndProjectId(@PathVariable Long projectId, @RequestParam(defaultValue = "false") boolean done, Pageable pageable) {
        return new ResponseEntity<>(taskService.getTasksByDoneIsFalseAndProjectId(projectId, done, pageable), HttpStatus.OK);
    }

    @PutMapping(path = "/{taskId}")
    ResponseEntity<TaskRequestDto> updateTaskById(@PathVariable Long projectId, @PathVariable Long taskId, @RequestBody @Valid TaskRequestDto taskRequestDto) {
        TaskRequestDto updatedTask = taskService.updateTaskById(projectId, taskId, taskRequestDto);
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
        return new ResponseEntity<>("Task of id: " + taskId + " deleted", HttpStatus.OK);
    }
}
