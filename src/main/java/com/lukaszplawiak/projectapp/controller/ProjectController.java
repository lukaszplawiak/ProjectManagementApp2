package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.dto.ProjectRequestDto;
import com.lukaszplawiak.projectapp.dto.ProjectResponseDto;
import com.lukaszplawiak.projectapp.model.User;
import com.lukaszplawiak.projectapp.service.ProjectService;
import com.lukaszplawiak.projectapp.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/projects")
class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @Secured({"ROLE_MANAGER"})
    @PostMapping
    ResponseEntity<ProjectResponseDto> createProject(@RequestBody @Valid ProjectRequestDto projectRequestDto,
                                                    Authentication authentication) {
        String userEmail = authentication.getName();
        User user = userService.getUser(userEmail);
        return new ResponseEntity<>(projectService.createProject(projectRequestDto, user), HttpStatus.CREATED);
    }

    @Secured({"ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @GetMapping(path = "/{id}")
    ResponseEntity<ProjectResponseDto> readProjectById(@PathVariable Long id) {
        return new ResponseEntity<>(projectService.getProjectDtoById(id), HttpStatus.OK);
    }

    @Secured({"ROLE_MANAGER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @GetMapping
    ResponseEntity<List<ProjectResponseDto>> readAllProjects(@PageableDefault Pageable pageable) {
        return new ResponseEntity<>(projectService.getAllDtoProjects(pageable), HttpStatus.OK);
    }

    @Secured({"ROLE_MANAGER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @GetMapping(path = "/search")
    ResponseEntity<List<ProjectResponseDto>> readProjectByDone(@RequestParam(defaultValue = "true") boolean done,
                                                               @PageableDefault Pageable pageable) {
        return new ResponseEntity<>(projectService.getProjectsDtoByDone(done, pageable), HttpStatus.OK);
    }

    @Secured({"ROLE_MANAGER"})
    @PutMapping(path = "/{id}")
    ResponseEntity<ProjectResponseDto> updateProject(@RequestBody @Valid ProjectRequestDto projectRequestDto,
                                                     @PathVariable Long id, Authentication authentication) {
        String userEmail = authentication.getName();
        User user = userService.getUser(userEmail);
        return new ResponseEntity<>(projectService.updateProject(projectRequestDto, id, user), HttpStatus.OK);
    }

    @Secured({"ROLE_MANAGER"})
    @DeleteMapping(path = "/{id}")
    ResponseEntity<?> deleteProject(@PathVariable Long id, Authentication authentication) {
        String userEmail = authentication.getName();
        User user = userService.getUser(userEmail);
        projectService.deleteProjectById(id, user);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}