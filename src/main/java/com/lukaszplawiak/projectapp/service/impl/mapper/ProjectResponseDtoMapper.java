package com.lukaszplawiak.projectapp.service.impl.mapper;

import com.lukaszplawiak.projectapp.dto.ProjectResponseDto;
import com.lukaszplawiak.projectapp.model.Project;

import java.util.stream.Collectors;

import static com.lukaszplawiak.projectapp.service.impl.mapper.TaskResponseDtoMapper.mapToTaskResponseDto;

public class ProjectResponseDtoMapper {
    public static ProjectResponseDto mapToProjectResponseDto(Project project) {
        return ProjectResponseDto.ProjectResponseDtoBuilder.aProjectResponseDto()
                .withId(project.getId())
                .withTitle(project.getTitle())
                .withDescription(project.getDescription())
                .withDeadline(project.getDeadline())
                .withDone(project.isDone())
                .withTasks(project.getTasks().stream()
                        .map(task -> mapToTaskResponseDto(task))
                        .collect(Collectors.toUnmodifiableSet()))
                .build();
    }
}
