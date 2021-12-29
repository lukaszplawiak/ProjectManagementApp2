package com.lukaszplawiak.projectapp.service.implementation;

import com.lukaszplawiak.projectapp.dto.ProjectDto;
import com.lukaszplawiak.projectapp.model.Project;
import com.lukaszplawiak.projectapp.repository.ProjectRepository;
import com.lukaszplawiak.projectapp.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    public static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    public ProjectServiceImpl(final ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectDto createProject(ProjectDto projectDto) {
        Project project = mapToProjectEntity(projectDto);
        projectRepository.save(project);
        ProjectDto projectResponse = mapToProjectDto(project);
        logger.info("Created project of id: " + projectResponse.getId());
        return projectResponse;
    }

    @Override
    public ProjectDto getProjectById(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Project of id: " + id + " not found."));
        logger.info("Exposed project of id: " + id);
        return mapToProjectDto(project);
    }

    @Override
    public List<ProjectDto> getAllProjects() {
        List<Project> projectList = projectRepository.findAll();
        List<ProjectDto> projectDtoList = projectList.stream()
                .map(project -> mapToProjectDto(project))
                .collect(Collectors.toList());
        logger.warn("Exposed all the projects");
        return projectDtoList;
    }

    @Override
    public List<ProjectDto> getAllProjects(Pageable page) {
        List<Project> projectList = projectRepository.findAll(page).getContent();
        List<ProjectDto> projectDtoList = projectList.stream()
                .map(project -> mapToProjectDto(project))
                .collect(Collectors.toList());
        logger.warn("Exposed all the pageable projects");
        return projectDtoList;
    }

    @Override
    public List<ProjectDto> getProjectsByDone(boolean done, Pageable page) {
        List<Project> projectList = projectRepository.findByDone(done);
        List<ProjectDto> projectDtoList = projectList.stream()
                .map(project -> mapToProjectDto(project))
                .collect(Collectors.toList());
        logger.info("Exposed all the projects by 'done' state");
        return projectDtoList;
    }

    @Override
    public ProjectDto updateProject(ProjectDto projectDto, Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Project of id: " + id + " not found."));
        project.setId(id);
        project.setTitle(projectDto.getTitle());
        project.setDescription(projectDto.getDescription());
        project.setDeadline(projectDto.getDeadline());
        project.setDone(projectDto.isDone());
        Project updateProject = projectRepository.save(project);
        logger.info("Updated project of id: " + id);
        return mapToProjectDto(updateProject);
    }

    @Override
    public void deleteProjectById(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Project of id: " + id + " not found."));
        logger.warn("Deleted project of id: " + id);
        projectRepository.delete(project);
    }

    private ProjectDto mapToProjectDto(Project project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(project.getId());
        projectDto.setTitle(project.getTitle());
        projectDto.setDescription(project.getDescription());
        projectDto.setDeadline(project.getDeadline());
        projectDto.setDone(project.isDone());
        projectDto.setTasks(project.getTasks());
        return projectDto;
    }

    private Project mapToProjectEntity(ProjectDto projectDto) {
        Project project = new Project();
        project.setId(projectDto.getId());
        project.setTitle(projectDto.getTitle());
        project.setDescription(projectDto.getDescription());
        project.setDeadline(projectDto.getDeadline());
        project.setDone(projectDto.isDone());
        project.setTasks(projectDto.getTasks());
        return project;
    }
}
