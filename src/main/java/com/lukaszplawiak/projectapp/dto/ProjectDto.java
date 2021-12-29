package com.lukaszplawiak.projectapp.dto;

import com.lukaszplawiak.projectapp.model.Task;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ProjectDto {
    private Long id;
    @NotEmpty
    @Size(min = 1, max = 50, message = "Name must be one or more characters")
    private String title;
    @Size(max = 200)
    private String description;
    @NotNull(message = "Deadline is mandatory")
    private LocalDateTime deadline;
    private boolean done;
    private Set<Task> tasks = new HashSet<>(); // to pole mozna zrobic finalne

    public long getId() {
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

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }


}
