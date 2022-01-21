package com.lukaszplawiak.projectapp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class ProjectRequestDto {
    @NotEmpty
    @Size(min = 1, max = 50, message = "Name must be 2 or more characters")
    private String title;
    @Size(max = 255)
    private String description;
    @NotNull(message = "Deadline is mandatory")
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
