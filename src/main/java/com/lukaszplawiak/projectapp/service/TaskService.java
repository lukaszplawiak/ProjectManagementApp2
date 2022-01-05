package com.lukaszplawiak.projectapp.service;

import com.lukaszplawiak.projectapp.dto.TaskReadDto;
import com.lukaszplawiak.projectapp.dto.TaskWriteDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {
    TaskWriteDto createTask(Long projectId, TaskWriteDto taskWriteDto);

    TaskReadDto getTaskById(Long projectId, Long taskId);

    List<TaskReadDto> getTasksByProject_Id(Long projectId, Pageable pageable);

    List<TaskReadDto> getTasksByDone(boolean done, Pageable pageable);

    List<TaskReadDto> getTasksByDoneAndProject_Id(Long projectId, boolean done, Pageable pageable);

    TaskWriteDto updateTaskById(Long projectId, Long TaskId, TaskWriteDto taskWriteDto);

    void toggleTask(Long projectId, Long taskId);

    void deleteTaskById(Long projectId, Long TaskId);

}
