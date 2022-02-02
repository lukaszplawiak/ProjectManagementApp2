package com.lukaszplawiak.projectapp.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String comment;
    private LocalDate deadline;
    @Column(columnDefinition = "BIT", length = 1)
    private boolean done;
    @Embedded
    private Audit audit = new Audit();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

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

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Audit getAudit() {
        return audit;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return done == task.done && Objects.equals(id, task.id) && Objects.equals(name, task.name) && Objects.equals(comment, task.comment) && Objects.equals(deadline, task.deadline) && Objects.equals(audit, task.audit) && Objects.equals(project, task.project) && Objects.equals(user, task.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, comment, deadline, done, audit, project, user);
    }

    public static final class Builder {
        private Long id;
        private String name;
        private String comment;
        private LocalDate deadline;
        private boolean done;
        private Project project;
        private User user;

        private Builder() {
        }

        public static Builder aTask() {
            return new Builder();
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withComment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder withDeadline(LocalDate deadline) {
            this.deadline = deadline;
            return this;
        }

        public Builder withDone(boolean done) {
            this.done = done;
            return this;
        }

        public Builder withProject(Project project) {
            this.project = project;
            return this;
        }

        public Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public Task build() {
            Task task = new Task();
            task.setId(id);
            task.setName(name);
            task.setComment(comment);
            task.setDeadline(deadline);
            task.setDone(done);
            task.setProject(project);
            task.setUser(user);
            return task;
        }
    }
}
