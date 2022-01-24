package com.lukaszplawiak.projectapp.service;

import com.lukaszplawiak.projectapp.dto.ProjectRequestDto;
import com.lukaszplawiak.projectapp.dto.ProjectResponseDto;
import com.lukaszplawiak.projectapp.model.Project;
import com.lukaszplawiak.projectapp.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {
    ProjectRequestDto createProject(ProjectRequestDto projectRequestDto, User user);

    Project getProjectById(Long id);

    ProjectResponseDto getProjectDtoById(Long id);

    List<Project> getAllProjects();

    List<ProjectResponseDto> getAllDtoProjects(Pageable pageable);

    List<Project> getProjectByDone(boolean done);

    List<ProjectResponseDto> getProjectsDtoByDone(boolean done, Pageable pageable);

    ProjectRequestDto updateProject(ProjectRequestDto projectRequestDto, Long id, User user);

    void deleteProjectById(Long id, User user);
}
