package com.lukaszplawiak.projectapp.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ProjectResponseDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate deadline;
    private boolean done;
    private Set<TaskResponseDto> tasks = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public boolean isDone() {
        return done;
    }

    public Set<TaskResponseDto> getTasks() {
        return tasks;
    }

    public static final class ProjectResponseDtoBuilder {
        private Long id;
        private String title;
        private String description;
        private LocalDate deadline;
        private boolean done;
        private Set<TaskResponseDto> tasks = new HashSet<>();

        private ProjectResponseDtoBuilder() {
        }

        public static ProjectResponseDtoBuilder aProjectResponseDto() {
            return new ProjectResponseDtoBuilder();
        }

        public ProjectResponseDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ProjectResponseDtoBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public ProjectResponseDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ProjectResponseDtoBuilder withDeadline(LocalDate deadline) {
            this.deadline = deadline;
            return this;
        }

        public ProjectResponseDtoBuilder withDone(boolean done) {
            this.done = done;
            return this;
        }

        public ProjectResponseDtoBuilder withTasks(Set<TaskResponseDto> tasks) {
            this.tasks = tasks;
            return this;
        }

        public ProjectResponseDto build() {
            ProjectResponseDto projectResponseDto = new ProjectResponseDto();
            projectResponseDto.id = this.id;
            projectResponseDto.done = this.done;
            projectResponseDto.title = this.title;
            projectResponseDto.description = this.description;
            projectResponseDto.tasks = this.tasks;
            projectResponseDto.deadline = this.deadline;
            return projectResponseDto;
        }
    }
}
