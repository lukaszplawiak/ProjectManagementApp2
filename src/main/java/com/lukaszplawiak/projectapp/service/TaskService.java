package com.lukaszplawiak.projectapp.service;

import com.lukaszplawiak.projectapp.dto.TaskRequestDto;
import com.lukaszplawiak.projectapp.dto.TaskResponseDto;
import com.lukaszplawiak.projectapp.model.Task;
import com.lukaszplawiak.projectapp.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {
    TaskResponseDto createTask(Long projectId, TaskRequestDto taskRequestDto, User user);

    TaskResponseDto getTaskById(Long projectId, Long taskId);

    List<Task> getTasksByProjectId(Long id);

    List<TaskResponseDto> getTasksDtoByProjectId(Long projectId, Pageable pageable);

    List<TaskResponseDto> getTasksByDoneIsFalseAndProjectId(Long projectId, boolean done, Pageable pageable);

    TaskResponseDto updateTaskById(Long projectId, Long TaskId, TaskRequestDto taskRequestDto, User user);

    void toggleTask(Long projectId, Long taskId, User user);

    void deleteTaskById(Long projectId, Long TaskId, User user);
}