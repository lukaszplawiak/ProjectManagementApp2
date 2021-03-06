package com.lukaszplawiak.projectapp.service.impl;

import com.lukaszplawiak.projectapp.dto.ProjectRequestDto;
import com.lukaszplawiak.projectapp.dto.ProjectResponseDto;
import com.lukaszplawiak.projectapp.exception.IllegalAccessException;
import com.lukaszplawiak.projectapp.exception.IllegalInputException;
import com.lukaszplawiak.projectapp.exception.IllegalModificationException;
import com.lukaszplawiak.projectapp.model.Project;
import com.lukaszplawiak.projectapp.model.User;
import com.lukaszplawiak.projectapp.repository.ProjectRepository;
import com.lukaszplawiak.projectapp.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.lukaszplawiak.projectapp.service.impl.mapper.ProjectEntityMapper.mapToProjectEntity;
import static com.lukaszplawiak.projectapp.service.impl.mapper.ProjectResponseDtoMapper.mapToProjectResponseDto;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final Clock clock;
    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    public ProjectServiceImpl(final ProjectRepository projectRepository, Clock clock) {
        this.projectRepository = projectRepository;
        this.clock = clock;
    }

    @Override
    public ProjectResponseDto createProject(ProjectRequestDto projectRequestDto, User user) {
        projectDeadlineValidation(projectRequestDto);
        Project project = mapToProjectEntity(projectRequestDto);
        project.setUser(user);
        projectRepository.save(project);
        logger.trace("Created project of id: " + project.getId());
        return mapToProjectResponseDto(project);
    }

    @Override
    public Project getProjectById(Long id) {
        return projectRepository.getById(id);
    }

    @Override
    public ProjectResponseDto getProjectDtoById(Long id) {
        Project project = projectRepository.getById(id);
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
        return projectWriteDtoList;
    }

    @Override
    public ProjectResponseDto updateProject(ProjectRequestDto projectRequestDto, Long id, User user) {
        projectDeadlineValidation(projectRequestDto);
        Project project = projectRepository.getById(id);
        checkUserAccess(user, project);
        projectIsDoneCheck(id, project);
        project.setId(id);
        project.setTitle(projectRequestDto.getTitle());
        project.setDescription(projectRequestDto.getDescription());
        project.setDeadline(projectRequestDto.getDeadline());
        logger.trace("Updated project of id: " + id);
        return mapToProjectResponseDto(project);
    }

    @Override
    public void deleteProjectById(Long id, User user) {
        Project project = projectRepository.getById(id);
        checkUserAccess(user, project);
        projectRepository.delete(project);
        logger.trace("Deleted project of id: " + id);
    }


    public void toggleProject(Long projectId) {
        Project project = projectRepository.getById(projectId);
        if (project.getTasks().stream().allMatch(b -> b.isDone())) {
            project.setDone(!project.isDone());
            //projectRepository.save(project);
            logger.trace("Toggled project of id: " + projectId);
        }
    }

    private void checkUserAccess(User user, Project project) {
        if (!(Objects.equals(project.getUser().getId(), user.getId()))) {
            logger.trace("Access denied");
            throw new IllegalAccessException();
        }
    }

    private void projectIsDoneCheck(Long projectId, Project project) {
        if (project.isDone()) {
            logger.trace("Project of id: " + projectId + " is done. The action is impossible to execute");
            throw new IllegalModificationException(projectId);
        }
    }

    private void projectDeadlineValidation(ProjectRequestDto projectRequestDto) {
        if (projectRequestDto.getDeadline().isBefore(ChronoLocalDate.from(LocalDateTime.now(clock)))) {
            logger.info("Project's deadline must be set after now");
            throw new IllegalInputException();
        }
    }
}