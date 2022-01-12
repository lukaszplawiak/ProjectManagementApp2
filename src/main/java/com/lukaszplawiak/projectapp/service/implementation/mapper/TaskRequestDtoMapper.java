package com.lukaszplawiak.projectapp.service.implementation.mapper;

import com.lukaszplawiak.projectapp.dto.TaskRequestDto;
import com.lukaszplawiak.projectapp.model.Task;

public class TaskRequestDtoMapper {
    public static TaskRequestDto mapToTaskWriteDto(Task task) {
        return TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName(task.getName())
                .withComment(task.getComment())
                .withDeadline(task.getDeadline())
                .build();
    }
}
