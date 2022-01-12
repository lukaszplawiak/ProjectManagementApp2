package com.lukaszplawiak.projectapp.service;

import com.lukaszplawiak.projectapp.dto.ProjectResponseDto;
import com.lukaszplawiak.projectapp.dto.ProjectRequestDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {
    ProjectRequestDto createProject(ProjectRequestDto projectRequestDto);

    ProjectResponseDto getProjectById(Long id);

    List<ProjectResponseDto> getAllProjects(Pageable pageable);

    List<ProjectResponseDto> getProjectsByDone(boolean done, Pageable pageable);

    ProjectRequestDto updateProject(ProjectRequestDto projectRequestDto, Long id);

    void deleteProjectById(Long id);
}
