package com.lukaszplawiak.projectapp.service.implementation.mapper;

import com.lukaszplawiak.projectapp.dto.TaskWriteDto;
import com.lukaszplawiak.projectapp.model.Task;

public class TaskWriteDtoMapper {
    public static TaskWriteDto mapToTaskWriteDto(Task task) {
        TaskWriteDto taskWriteDto = new TaskWriteDto();
        taskWriteDto.setName(task.getName());
        taskWriteDto.setComment(task.getComment());
        taskWriteDto.setDeadline(task.getDeadline());
        return taskWriteDto;
    }
}
