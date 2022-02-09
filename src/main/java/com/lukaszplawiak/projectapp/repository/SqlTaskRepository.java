package com.lukaszplawiak.projectapp.repository;

import com.lukaszplawiak.projectapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Long> {
    @Override
    default Task getById(Long id) {
        return TaskRepository.super.getById(id);
    }
}