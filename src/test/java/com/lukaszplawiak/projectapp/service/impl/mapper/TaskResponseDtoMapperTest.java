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
    void mapToTaskResponseDtoShouldBeSuccessful() {
        // given
        Project project = new Project();
        project.setId(1L);
        Task buildTask = Task.Builder.aTask()
                .withId(2L)
                .withName("Task Name")
                .withComment("Task Comment")
                .withDeadline(LocalDate.parse("2022-09-27"))
                .withDone(false)
                .build();

        // when
        TaskResponseDto taskResponseDto = mapToTaskResponseDto(buildTask);

        // then
        assertThat(taskResponseDto)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", buildTask.getId())
                .hasFieldOrPropertyWithValue("name", buildTask.getName())
                .hasFieldOrPropertyWithValue("comment", buildTask.getComment())
                .hasFieldOrPropertyWithValue("deadline", buildTask.getDeadline())
                .hasFieldOrPropertyWithValue("done", buildTask.isDone());
    }
}