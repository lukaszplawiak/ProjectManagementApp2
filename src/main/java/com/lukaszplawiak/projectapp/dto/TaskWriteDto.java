package com.lukaszplawiak.projectapp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class TaskWriteDto {
    @NotEmpty
    @Size(min = 2, max = 50, message = "Title must be two or more characters")
    private String name;
    @Size(max = 250)
    private String comment;
    private LocalDateTime deadline;
    private boolean done;

    public TaskWriteDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

}
