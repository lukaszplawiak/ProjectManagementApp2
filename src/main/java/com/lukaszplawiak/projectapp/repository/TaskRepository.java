package com.lukaszplawiak.projectapp.repository;

import com.lukaszplawiak.projectapp.model.Task;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    List<Task> findByProjectId(Long projectId);
    Optional<Task> findById(Long id);
    Task save(Task entity);
    List<Task> findByDone(@Param("state") boolean done);
    void delete(Task entity);

}
