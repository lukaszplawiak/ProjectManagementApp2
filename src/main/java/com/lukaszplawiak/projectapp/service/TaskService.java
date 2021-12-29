package com.lukaszplawiak.projectapp.service;

import com.lukaszplawiak.projectapp.dto.TaskDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {
    TaskDto createTask(Long projectId, TaskDto taskDto);

    TaskDto getTaskById(Long projectId, Long taskId);

    List<TaskDto> getTasksByProject_Id(Long projectId, Pageable pageable);

    List<TaskDto> getTasksByDone(boolean done, Pageable pageable);

    List<TaskDto> getTasksByDoneAndProject_Id(Long projectId, boolean done, Pageable pageable);

    TaskDto updateTaskById(Long projectId, Long TaskId, TaskDto taskDto);

    void deleteTaskById(Long projectId, Long TaskId);

}
