package com.lukaszplawiak.projectapp.dto;

import com.lukaszplawiak.projectapp.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class TaskRequestDto {
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
    @NotNull
    @Size(max = 255)
    private String comment;
    @NotNull
    private LocalDate deadline;
    private User user;

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public User getUser() {
        return user;
    }

    public static final class TaskRequestDtoBuilder {
        private String name;
        private String comment;
        private LocalDate deadline;
        private User user;

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

        public TaskRequestDtoBuilder withUser(User user) {
            this.user = user;
            return this;
        }

        public TaskRequestDto build() {
            TaskRequestDto taskRequestDto = new TaskRequestDto();
            taskRequestDto.deadline = this.deadline;
            taskRequestDto.name = this.name;
            taskRequestDto.comment = this.comment;
            taskRequestDto.user = this.user;
            return taskRequestDto;
        }
    }
}