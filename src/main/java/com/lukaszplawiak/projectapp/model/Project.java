package com.lukaszplawiak.projectapp.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDate deadline;
    private boolean done;
    @Embedded
    private Audit audit = new Audit();
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private Set<Task> tasks = new HashSet<>();

    public Project() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Set<Task> getTasks() {
        return tasks.stream().collect(Collectors.toUnmodifiableSet());
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Audit getAudit() {
        return audit;
    }

    public static final class ProjectBuilder {
        private Project project;

        private ProjectBuilder() {
            project = new Project();
        }

        public static ProjectBuilder aProject() {
            return new ProjectBuilder();
        }

        public ProjectBuilder withTitle(String title) {
            project.setTitle(title);
            return this;
        }

        public ProjectBuilder withDescription(String description) {
            project.setDescription(description);
            return this;
        }

        public ProjectBuilder withDeadline(LocalDate deadline) {
            project.setDeadline(deadline);
            return this;
        }

        public ProjectBuilder withDone(boolean done) {
            project.setDone(done);
            return this;
        }

        public ProjectBuilder withTasks(Set<Task> tasks) {
            project.setTasks(tasks);
            return this;
        }

        public Project build() {
            return project;
        }
    }
}
