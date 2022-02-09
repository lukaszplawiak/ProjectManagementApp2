package com.lukaszplawiak.projectapp.service.impl.mapper;

import com.lukaszplawiak.projectapp.dto.TaskRequestDto;
import com.lukaszplawiak.projectapp.model.Task;

public class TaskEntityMapper {
    public static Task mapToTaskEntity(TaskRequestDto taskRequestDto) {
        return Task.TaskBuilder.aTask()
                .withName(taskRequestDto.getName())
                .withComment(taskRequestDto.getComment())
                .withDeadline(taskRequestDto.getDeadline())
                .build();
    }
}