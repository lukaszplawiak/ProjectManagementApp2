package com.lukaszplawiak.projectapp.repository;

import com.lukaszplawiak.projectapp.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project, Long> {
    @Override
    default Project getById(Long id) {
        return ProjectRepository.super.getById(id);
    }
}