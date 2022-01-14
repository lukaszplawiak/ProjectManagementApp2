package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.dto.ProjectResponseDto;
import com.lukaszplawiak.projectapp.dto.ProjectRequestDto;
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
    ResponseEntity<ProjectRequestDto> createProject(@RequestBody @Valid ProjectRequestDto projectRequestDto) {
        return new ResponseEntity<>(projectService.createProject(projectRequestDto), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    ResponseEntity<ProjectResponseDto> readProjectById(@PathVariable Long id) {
        return new ResponseEntity<>(projectService.getProjectById(id), HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<List<ProjectResponseDto>> readAllProjects(Pageable pageable) {
        return new ResponseEntity<>(projectService.getAllProjects(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/search")
    ResponseEntity<List<ProjectResponseDto>> readProjectByDone(@RequestParam(defaultValue = "true") boolean done, Pageable pageable) {
        return new ResponseEntity<>(projectService.getProjectsByDone(done, pageable), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    ResponseEntity<ProjectRequestDto> updateProject(@RequestBody @Valid ProjectRequestDto projectRequestDto, @PathVariable Long id) {
        return new ResponseEntity<>(projectService.updateProject(projectRequestDto, id), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<String> deleteProject(@PathVariable Long id) {
        projectService.deleteProjectById(id);
        return new ResponseEntity<>("Project entity of id: " + id + " deleted", HttpStatus.OK);
    }
}
