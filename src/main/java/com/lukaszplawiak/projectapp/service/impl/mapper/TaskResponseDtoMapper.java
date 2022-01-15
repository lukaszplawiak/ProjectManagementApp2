package com.lukaszplawiak.projectapp.service.impl.mapper;

import com.lukaszplawiak.projectapp.dto.TaskResponseDto;
import com.lukaszplawiak.projectapp.model.Task;

public class TaskResponseDtoMapper {
    public static TaskResponseDto mapToTaskResponseDto(Task task) {
        return TaskResponseDto.TaskResponseDtoBuilder.aTaskResponseDto()
                .withId(task.getId())
                .withName(task.getName())
                .withComment(task.getComment())
                .withDeadline(task.getDeadline())
                .withDone(task.isDone())
                .build();
    }
}
