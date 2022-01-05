package com.lukaszplawiak.projectapp.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ProjectReadDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private boolean done;
    private Set<TaskReadDto> tasks = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Set<TaskReadDto> getTasks() {
        return tasks;
    }

    public void setTasks(Set<TaskReadDto> tasks) {
        this.tasks = tasks;
    }
}
