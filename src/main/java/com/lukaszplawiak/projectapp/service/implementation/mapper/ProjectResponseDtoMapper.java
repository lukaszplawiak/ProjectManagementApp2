package com.lukaszplawiak.projectapp.service.implementation.mapper;

import com.lukaszplawiak.projectapp.dto.ProjectResponseDto;
import com.lukaszplawiak.projectapp.model.Project;

import java.util.stream.Collectors;

import static com.lukaszplawiak.projectapp.service.implementation.mapper.TaskResponseDtoMapper.mapToTaskReadDto;

public class ProjectResponseDtoMapper {
    public static ProjectResponseDto mapToProjectReadDto(Project project) {
        return ProjectResponseDto.ProjectResponseDtoBuilder.aProjectResponseDto()
                .withId(project.getId())
                .withTitle(project.getTitle())
                .withDescription(project.getDescription())
                .withDeadline(project.getDeadline())
                .withDone(project.isDone())
                .withTasks(project.getTasks().stream()
                        .map(task -> mapToTaskReadDto(task))
                        .collect(Collectors.toUnmodifiableSet()))
                .build();
    }
}
