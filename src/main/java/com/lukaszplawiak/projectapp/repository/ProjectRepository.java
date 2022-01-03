package com.lukaszplawiak.projectapp.repository;

import com.lukaszplawiak.projectapp.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    List<Project> findAll();
    Optional<Project> findById(Long id);
    boolean existsById(Long id);
    Project save(Project entity);
    Page<Project> findAll(Pageable page);
    List<Project> findByDone(@Param("state") boolean done);
    void deleteById(Long id);
    void delete(Project entity);
}
