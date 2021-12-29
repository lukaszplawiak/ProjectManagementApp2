package com.lukaszplawiak.projectapp.repository;

import com.lukaszplawiak.projectapp.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project, Long> {
    @Override
    @Query("from Project p join fetch p.tasks")
    List<Project> findAll();
}
