package com.lukaszplawiak.projectapp.dto;

import java.time.LocalDate;

public class TaskResponseDto {
    private Long id;
    private String name;
    private String comment;
    private LocalDate deadline;
    private boolean done;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public boolean isDone() {
        return done;
    }

    public static final class TaskResponseDtoBuilder {
        private Long id;
        private String name;
        private String comment;
        private LocalDate deadline;
        private boolean done;

        private TaskResponseDtoBuilder() {
        }

        public static TaskResponseDtoBuilder aTaskResponseDto() {
            return new TaskResponseDtoBuilder();
        }

        public TaskResponseDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public TaskResponseDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public TaskResponseDtoBuilder withComment(String comment) {
            this.comment = comment;
            return this;
        }

        public TaskResponseDtoBuilder withDeadline(LocalDate deadline) {
            this.deadline = deadline;
            return this;
        }

        public TaskResponseDtoBuilder withDone(boolean done) {
            this.done = done;
            return this;
        }

        public TaskResponseDto build() {
            TaskResponseDto taskResponseDto = new TaskResponseDto();
            taskResponseDto.id = this.id;
            taskResponseDto.deadline = this.deadline;
            taskResponseDto.name = this.name;
            taskResponseDto.done = this.done;
            taskResponseDto.comment = this.comment;
            return taskResponseDto;
        }
    }
}
