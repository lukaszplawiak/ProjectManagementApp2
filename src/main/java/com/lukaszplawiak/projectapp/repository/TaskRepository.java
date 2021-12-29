package com.lukaszplawiak.projectapp.repository;

import com.lukaszplawiak.projectapp.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    List<Task> findAll();
    List<Task> findByProjectId(Long projectId);
    Optional<Task> findById(Long id);
    boolean existsById(Long id);
    Task save(Task entity);
    Page<Task> findAll(Pageable page);
    List<Task> findByDone(@Param("state") boolean done);
    void deleteById(Long id);
    void delete(Task entity);

}
