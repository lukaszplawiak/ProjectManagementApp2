package com.lukaszplawiak.projectapp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class ProjectWriteDto {
    @NotEmpty
    @Size(min = 1, max = 50, message = "Name must be one or more characters")
    private String title;
    @Size(max = 200)
    private String description;
    @NotNull(message = "Deadline is mandatory")
    private LocalDateTime deadline;


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
}
