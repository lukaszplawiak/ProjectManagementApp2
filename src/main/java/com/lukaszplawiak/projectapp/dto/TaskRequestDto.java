package com.lukaszplawiak.projectapp.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class TaskRequestDto {
    @NotBlank
    @Size(min = 2, max = 50, message = "Title must be 2 or more characters")
    private String name;
    @Size(max = 255)
    private String comment;
    private LocalDateTime deadline;

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public static final class TaskRequestDtoBuilder {
        private String name;
        private String comment;
        private LocalDateTime deadline;

        private TaskRequestDtoBuilder() {
        }

        public static TaskRequestDtoBuilder aTaskRequestDto() {
            return new TaskRequestDtoBuilder();
        }

        public TaskRequestDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public TaskRequestDtoBuilder withComment(String comment) {
            this.comment = comment;
            return this;
        }

        public TaskRequestDtoBuilder withDeadline(LocalDateTime deadline) {
            this.deadline = deadline;
            return this;
        }

        public TaskRequestDto build() {
            TaskRequestDto taskRequestDto = new TaskRequestDto();
            taskRequestDto.deadline = this.deadline;
            taskRequestDto.name = this.name;
            taskRequestDto.comment = this.comment;
            return taskRequestDto;
        }
    }
}
