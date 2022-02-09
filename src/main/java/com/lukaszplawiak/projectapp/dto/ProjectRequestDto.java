package com.lukaszplawiak.projectapp.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class ProjectRequestDto {
    @NotBlank
    @Size(min = 1, max = 50)
    private String title;
    @NotNull
    @Size(max = 255)
    private String description;
    @NotNull
    private LocalDate deadline;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public static final class ProjectRequestDtoBuilder {
        private String title;
        private String description;
        private LocalDate deadline;

        private ProjectRequestDtoBuilder() {
        }

        public static ProjectRequestDtoBuilder aProjectRequestDto() {
            return new ProjectRequestDtoBuilder();
        }

        public ProjectRequestDtoBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public ProjectRequestDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ProjectRequestDtoBuilder withDeadline(LocalDate deadline) {
            this.deadline = deadline;
            return this;
        }

        public ProjectRequestDto build() {
            ProjectRequestDto projectRequestDto = new ProjectRequestDto();
            projectRequestDto.title = this.title;
            projectRequestDto.deadline = this.deadline;
            projectRequestDto.description = this.description;
            return projectRequestDto;
        }
    }
}