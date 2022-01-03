package com.lukaszplawiak.projectapp.service;

import com.lukaszplawiak.projectapp.dto.ProjectDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {
    ProjectDto createProject(ProjectDto projectDto);

    ProjectDto getProjectById(Long id);

    List<ProjectDto> getAllProjects();

    List<ProjectDto> getAllProjects(Pageable pageable);

    List<ProjectDto> getProjectsByDone(boolean done, Pageable pageable);

    ProjectDto updateProject(ProjectDto projectDto, Long id);

    void deleteProjectById(Long id);
}
