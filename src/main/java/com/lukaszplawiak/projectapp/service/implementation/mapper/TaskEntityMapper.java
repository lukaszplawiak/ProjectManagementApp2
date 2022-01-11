package com.lukaszplawiak.projectapp.service.implementation.mapper;

import com.lukaszplawiak.projectapp.dto.TaskWriteDto;
import com.lukaszplawiak.projectapp.model.Task;

public class TaskEntityMapper {
    public static Task mapToTaskEntity(TaskWriteDto taskWriteDto) {
        Task task = new Task();
        task.setName(taskWriteDto.getName());
        task.setComment(taskWriteDto.getComment());
        task.setDeadline(taskWriteDto.getDeadline());
        return task;
    }
}
