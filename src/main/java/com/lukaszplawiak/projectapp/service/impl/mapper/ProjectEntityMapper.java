package com.lukaszplawiak.projectapp.service.impl.mapper;

import com.lukaszplawiak.projectapp.dto.ProjectRequestDto;
import com.lukaszplawiak.projectapp.model.Project;

public class ProjectEntityMapper {
    public static Project mapToProjectEntity(ProjectRequestDto projectRequestDto) {
        return Project.ProjectBuilder.aProject()
                .withTitle(projectRequestDto.getTitle())
                .withDescription(projectRequestDto.getDescription())
                .withDeadline(projectRequestDto.getDeadline())
                .build();
    }
}
