package com.lukaszplawiak.projectapp.service.impl;

import com.lukaszplawiak.projectapp.dto.ProjectRequestDto;
import com.lukaszplawiak.projectapp.dto.ProjectResponseDto;
import com.lukaszplawiak.projectapp.exception.IllegalAccessException;
import com.lukaszplawiak.projectapp.model.Project;
import com.lukaszplawiak.projectapp.model.User;
import com.lukaszplawiak.projectapp.repository.ProjectRepository;
import com.lukaszplawiak.projectapp.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.lukaszplawiak.projectapp.service.impl.mapper.ProjectEntityMapper.mapToProjectEntity;
import static com.lukaszplawiak.projectapp.service.impl.mapper.ProjectResponseDtoMapper.mapToProjectResponseDto;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    public ProjectServiceImpl(final ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectResponseDto createProject(ProjectRequestDto projectRequestDto, User user) {
        Project project = mapToProjectEntity(projectRequestDto);
        project.setUser(user);
        projectRepository.save(project);
        logger.info("Created project of id: " + project.getId());
        return mapToProjectResponseDto(project);
    }

    @Override
    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Project of id: " + id + " not found"));
    }

    @Override
    public ProjectResponseDto getProjectDtoById(Long id) {
        Project project = projectRepository.getById(id);
        logger.info("Fetch project of id: " + id);
        return mapToProjectResponseDto(project);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public List<ProjectResponseDto> getAllDtoProjects(Pageable page) {
        List<Project> projectList = projectRepository.findAll(page).getContent();
        List<ProjectResponseDto> projectWriteDtoList = projectList.stream()
                .map(project -> mapToProjectResponseDto(project))
                .collect(Collectors.toList());
        logger.warn("Fetch all the projects");
        return projectWriteDtoList;
    }

    @Override
    public List<Project> getProjectByDone(boolean done) {
        return projectRepository.findByDone(done);
    }

    @Override
    public List<ProjectResponseDto> getProjectsDtoByDone(boolean done, Pageable page) {
        List<Project> projectList = projectRepository.findByDone(done);
        List<ProjectResponseDto> projectWriteDtoList = projectList.stream()
                .map(project -> mapToProjectResponseDto(project))
                .collect(Collectors.toList());
        logger.info("Fetch all the projects by 'done' state");
        return projectWriteDtoList;
    }

    @Override
    public ProjectResponseDto updateProject(ProjectRequestDto projectRequestDto, Long id, User user) {
        Project project = projectRepository.getById(id);
        if (!(project.getUser().getId() == user.getId())) {
            logger.info("Update access denied");
            throw new IllegalAccessException("Update access denied");
        }
        project.setId(id);
        project.setTitle(projectRequestDto.getTitle());
        project.setDescription(projectRequestDto.getDescription());
        project.setDeadline(projectRequestDto.getDeadline());
        logger.info("Updated project of id: " + id);
        return mapToProjectResponseDto(project);
    }

    @Override
    public void deleteProjectById(Long id, User user) {
        Project project = projectRepository.getById(id);
        if (!(project.getUser().getId() == user.getId())) {
            logger.info("Access denied");
            throw new IllegalAccessException("Access denied");
        }
        projectRepository.delete(project);
        logger.info("Deleted project of id: " + id);
    }


    public void toggleProject(Long projectId) {
        Project project = projectRepository.getById(projectId);
        if (project.getTasks().stream().allMatch(b -> b.isDone())) {
            project.setDone(!project.isDone());
            projectRepository.save(project);
            logger.info("Toggled project of id: " + projectId);
        }
    }
}
