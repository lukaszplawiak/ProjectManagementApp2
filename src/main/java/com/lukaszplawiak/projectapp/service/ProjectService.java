package com.lukaszplawiak.projectapp.service;

import com.lukaszplawiak.projectapp.dto.ProjectReadDto;
import com.lukaszplawiak.projectapp.dto.ProjectWriteDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {
    ProjectWriteDto createProject(ProjectWriteDto projectWriteDto);

    ProjectReadDto getProjectById(Long id);

    List<ProjectReadDto> getAllProjects(Pageable pageable);

    List<ProjectReadDto> getProjectsByDone(boolean done, Pageable pageable);

    ProjectWriteDto updateProject(ProjectWriteDto projectWriteDto, Long id);

    void deleteProjectById(Long id);
}
