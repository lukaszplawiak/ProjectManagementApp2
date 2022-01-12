package com.lukaszplawiak.projectapp.service.implementation;

import com.lukaszplawiak.projectapp.dto.ProjectReadDto;
import com.lukaszplawiak.projectapp.dto.ProjectWriteDto;
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

import static com.lukaszplawiak.projectapp.service.implementation.mapper.ProjectEntityMapper.mapToProjectEntity;
import static com.lukaszplawiak.projectapp.service.implementation.mapper.ProjectReadDtoMapper.mapToProjectReadDto;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    public ProjectServiceImpl(final ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectWriteDto createProject(ProjectWriteDto projectWriteDto) {
        Project project = mapToProjectEntity(projectWriteDto);
        projectRepository.save(project);
        logger.info("Created project of id: " + project.getId());
        return projectWriteDto;
    }

    @Override
    public ProjectReadDto getProjectById(Long id) {
        Project project = projectRepository.getById(id);
        logger.info("Exposed project of id: " + id);
        return mapToProjectReadDto(project);
    }

    @Override
    public List<ProjectReadDto> getAllProjects(Pageable page) {
        List<Project> projectList = projectRepository.findAll(page).getContent();
        List<ProjectReadDto> projectWriteDtoList = projectList.stream()
                .map(project -> mapToProjectReadDto(project))
                .collect(Collectors.toList());
        logger.warn("Exposed all the projects");
        return projectWriteDtoList;
    }

    @Override
    public List<ProjectReadDto> getProjectsByDone(boolean done, Pageable page) {
        List<Project> projectList = projectRepository.findByDone(done);
        List<ProjectReadDto> projectWriteDtoList = projectList.stream()
                .map(project -> mapToProjectReadDto(project))
                .collect(Collectors.toList());
        logger.info("Exposed all the projects by 'done' state");
        return projectWriteDtoList;
    }

    @Override
    public ProjectWriteDto updateProject(ProjectWriteDto projectWriteDto, Long id) {
        Project project = projectRepository.getById(id);
        project.setId(id);
        project.setTitle(projectWriteDto.getTitle());
        project.setDescription(projectWriteDto.getDescription());
        project.setDeadline(projectWriteDto.getDeadline());
        logger.info("Updated project of id: " + id);
        return projectWriteDto;
    }

    @Override
    public void deleteProjectById(Long id) {
        Project project = projectRepository.getById(id);
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
