package com.lukaszplawiak.projectapp.repository;

import com.lukaszplawiak.projectapp.model.Task;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    List<Task> findByProjectId(Long projectId);
    Optional<Task> findById(Long id);
    Task save(Task entity);
    List<Task> findByDoneAndProjectId(@Param("done") boolean done, Long projectId);
    void delete(Task entity);

    default Task getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task of id " + id + " not found"));
    }
}