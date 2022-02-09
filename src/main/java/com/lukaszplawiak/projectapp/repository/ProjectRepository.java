package com.lukaszplawiak.projectapp.repository;

import com.lukaszplawiak.projectapp.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    List<Project> findAll();
    Optional<Project> findById(Long id);
    Project save(Project entity);
    Page<Project> findAll(Pageable page);
    List<Project> findByDone(@Param("state") boolean done);
    void delete(Project entity);

    default Project getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project of id " + id + " not found"));
    }
}