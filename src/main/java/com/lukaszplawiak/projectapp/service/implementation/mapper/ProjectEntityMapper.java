package com.lukaszplawiak.projectapp.service.implementation.mapper;

import com.lukaszplawiak.projectapp.dto.ProjectWriteDto;
import com.lukaszplawiak.projectapp.model.Project;

public class ProjectEntityMapper {
    public static Project mapToProjectEntity(ProjectWriteDto projectWriteDto) { // do zapisu do DB
        Project project = new Project();
        project.setTitle(projectWriteDto.getTitle());
        project.setDescription(projectWriteDto.getDescription());
        project.setDeadline(projectWriteDto.getDeadline());
        return project;
    }
}
