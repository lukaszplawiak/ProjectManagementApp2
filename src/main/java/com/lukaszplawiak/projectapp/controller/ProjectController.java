package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.dto.ProjectDto;
import com.lukaszplawiak.projectapp.service.ProjectService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/projects")
class ProjectController {
    private final ProjectService projectService;

    public ProjectController(final ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    ResponseEntity<ProjectDto> createProject(@RequestBody @Valid ProjectDto projectDto) {
        return new ResponseEntity<>(projectService.createProject(projectDto), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    ResponseEntity<ProjectDto> readProjectById(@PathVariable Long id) {
        return new ResponseEntity<>(projectService.getProjectById(id), HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<List<ProjectDto>> readAllProjects(Pageable pageable) {
        return new ResponseEntity<>(projectService.getAllProjects(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "search/done")
    ResponseEntity<List<ProjectDto>> readProjectByDone(@RequestParam(defaultValue = "true") boolean state, Pageable pageable) {
        return new ResponseEntity<>(projectService.getProjectsByDone(state, pageable), HttpStatus.OK);
    }

    @PutMapping(path = "{id}")
    ResponseEntity<ProjectDto> updateProject(@RequestBody @Valid ProjectDto projectDto, @PathVariable Long id) {
        return new ResponseEntity<>(projectService.updateProject(projectDto, id), HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    ResponseEntity<String> deleteProject(@PathVariable Long id) {
        projectService.deleteProjectById(id);
        return new ResponseEntity<>("Project entity deleted", HttpStatus.ACCEPTED);
    }

}
