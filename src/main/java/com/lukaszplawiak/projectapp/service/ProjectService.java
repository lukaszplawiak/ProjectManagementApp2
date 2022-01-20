package com.lukaszplawiak.projectapp.service;

import com.lukaszplawiak.projectapp.dto.ProjectResponseDto;
import com.lukaszplawiak.projectapp.dto.ProjectRequestDto;
import com.lukaszplawiak.projectapp.model.Project;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;

public interface ProjectService {
    ProjectRequestDto createProject(ProjectRequestDto projectRequestDto);

    Project getProjectById(Long id);

    ProjectResponseDto getProjectDtoById(Long id);

    List<Project> getAllProjects();

    List<ProjectResponseDto> getAllDtoProjects(Pageable pageable);

    List<Project> getProjectByDone(boolean done);

    List<ProjectResponseDto> getProjectsDtoByDone(boolean done, Pageable pageable);

    ProjectRequestDto updateProject(ProjectRequestDto projectRequestDto, Long id);

    void deleteProjectById(Long id);
}
