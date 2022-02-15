package com.lukaszplawiak.projectapp.service.impl.mapper;

import com.lukaszplawiak.projectapp.dto.TaskResponseDto;
import com.lukaszplawiak.projectapp.model.Project;
import com.lukaszplawiak.projectapp.model.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.lukaszplawiak.projectapp.service.impl.mapper.TaskResponseDtoMapper.mapToTaskResponseDto;
import static org.assertj.core.api.Assertions.assertThat;

class TaskResponseDtoMapperTest {

    @Test
    void mapToTaskResponseDto_WhenInputDataIsCorrect_ShouldBeMap() {
        // given
        var task = Task.TaskBuilder.aTask()
                .withId(2L)
                .withName("Task Name")
                .withComment("Task Comment")
                .withDeadline(LocalDate.parse("2022-09-27"))
                .withDone(false)
                .build();

        // when
        var taskResponseDto = mapToTaskResponseDto(task);

        // then
        assertThat(taskResponseDto)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", task.getId())
                .hasFieldOrPropertyWithValue("name", task.getName())
                .hasFieldOrPropertyWithValue("comment", task.getComment())
                .hasFieldOrPropertyWithValue("deadline", task.getDeadline())
                .hasFieldOrPropertyWithValue("done", task.isDone());
    }
}