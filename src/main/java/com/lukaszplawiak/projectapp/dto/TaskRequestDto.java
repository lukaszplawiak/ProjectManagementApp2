package com.lukaszplawiak.projectapp.dto;

import java.time.LocalDate;

public class TaskRequestDto {
    private String name;
    private String comment;
    private LocalDate deadline;

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public static final class TaskRequestDtoBuilder {
        private String name;
        private String comment;
        private LocalDate deadline;

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

        public TaskRequestDtoBuilder withDeadline(LocalDate deadline) {
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
