package com.lukaszplawiak.projectapp.service.implementation.mapper;

import com.lukaszplawiak.projectapp.dto.TaskReadDto;
import com.lukaszplawiak.projectapp.model.Task;

public class TaskReadDtoMapper {
    public static TaskReadDto mapToTaskReadDto(Task task) {
        TaskReadDto taskReadDto = new TaskReadDto();
        taskReadDto.setId(task.getId());
        taskReadDto.setName(task.getName());
        taskReadDto.setComment(task.getComment());
        taskReadDto.setDeadline(task.getDeadline());
        taskReadDto.setDone(task.isDone());
        return taskReadDto;
    }

}
