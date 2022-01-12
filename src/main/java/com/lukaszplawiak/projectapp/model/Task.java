package com.lukaszplawiak.projectapp.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String comment;
    private LocalDateTime deadline;
    private boolean done;
    @Embedded
    private Audit audit = new Audit();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String description) {
        this.comment = description;
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return done == task.done && Objects.equals(id, task.id) && Objects.equals(name, task.name) && Objects.equals(comment, task.comment) && Objects.equals(deadline, task.deadline) && Objects.equals(audit, task.audit) && Objects.equals(project, task.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, comment, deadline, done, audit, project);
    }


    public static final class Builder {
        private Task task;

        private Builder() {
            task = new Task();
        }

        public static Builder aTask() {
            return new Builder();
        }

        public Builder withId(Long id) {
            task.setId(id);
            return this;
        }

        public Builder withName(String name) {
            task.setName(name);
            return this;
        }

        public Builder withComment(String comment) {
            task.setComment(comment);
            return this;
        }

        public Builder withDeadline(LocalDateTime deadline) {
            task.setDeadline(deadline);
            return this;
        }

        public Builder withDone(boolean done) {
            task.setDone(done);
            return this;
        }

        public Builder withProject(Project project) {
            task.setProject(project);
            return this;
        }

        public Task build() {
            return task;
        }
    }
}
