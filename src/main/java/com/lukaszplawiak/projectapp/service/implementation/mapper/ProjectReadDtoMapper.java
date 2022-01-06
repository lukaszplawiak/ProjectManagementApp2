package com.lukaszplawiak.projectapp.service.implementation.mapper;

import com.lukaszplawiak.projectapp.dto.ProjectReadDto;
import com.lukaszplawiak.projectapp.model.Project;

import java.util.stream.Collectors;

import static com.lukaszplawiak.projectapp.service.implementation.mapper.TaskReadDtoMapper.mapToTaskReadDto;

public class ProjectReadDtoMapper {
    public static ProjectReadDto mapToProjectReadDto(Project project) {   // do odczytu z DB
        ProjectReadDto projectReadDto = new ProjectReadDto();
        projectReadDto.setId(project.getId());
        projectReadDto.setTitle(project.getTitle());
        projectReadDto.setDescription(project.getDescription());
        projectReadDto.setDeadline(project.getDeadline());
        projectReadDto.setDone(project.isDone());
        projectReadDto.setTasks(project.getTasks().stream()
                .map(task -> mapToTaskReadDto(task))
                .collect(Collectors.toSet()));
        return projectReadDto;
    }
}
