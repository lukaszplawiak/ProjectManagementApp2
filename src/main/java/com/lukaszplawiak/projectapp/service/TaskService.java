package com.lukaszplawiak.projectapp.service;

import com.lukaszplawiak.projectapp.dto.TaskResponseDto;
import com.lukaszplawiak.projectapp.dto.TaskRequestDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {
    TaskRequestDto createTask(Long projectId, TaskRequestDto taskRequestDto);

    TaskResponseDto getTaskById(Long projectId, Long taskId);

    List<TaskResponseDto> getTasksByProject_Id(Long projectId, Pageable pageable);

    List<TaskResponseDto> getTasksByDoneIsFalseAndProject_Id(Long projectId, boolean done, Pageable pageable);

    TaskRequestDto updateTaskById(Long projectId, Long TaskId, TaskRequestDto taskRequestDto);

    void toggleTask(Long projectId, Long taskId);

    void deleteTaskById(Long projectId, Long TaskId);

}
