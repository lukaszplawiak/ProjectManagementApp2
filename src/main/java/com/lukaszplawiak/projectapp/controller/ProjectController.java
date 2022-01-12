package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.dto.ProjectReadDto;
import com.lukaszplawiak.projectapp.dto.ProjectWriteDto;
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
    ResponseEntity<ProjectWriteDto> createProject(@RequestBody @Valid ProjectWriteDto projectWriteDto) {
        return new ResponseEntity<>(projectService.createProject(projectWriteDto), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    ResponseEntity<ProjectReadDto> readProjectById(@PathVariable Long id) {
        return new ResponseEntity<>(projectService.getProjectById(id), HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<List<ProjectReadDto>> readAllProjects(Pageable pageable) {
        return new ResponseEntity<>(projectService.getAllProjects(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/search")
    ResponseEntity<List<ProjectReadDto>> readProjectByDone(@RequestParam(defaultValue = "true") boolean done, Pageable pageable) {
        return new ResponseEntity<>(projectService.getProjectsByDone(done, pageable), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    ResponseEntity<ProjectWriteDto> updateProject(@RequestBody @Valid ProjectWriteDto projectWriteDto, @PathVariable Long id) {
        return new ResponseEntity<>(projectService.updateProject(projectWriteDto, id), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<String> deleteProject(@PathVariable Long id) {
        projectService.deleteProjectById(id);
        return new ResponseEntity<>("Project entity deleted", HttpStatus.NO_CONTENT);
    }
}
