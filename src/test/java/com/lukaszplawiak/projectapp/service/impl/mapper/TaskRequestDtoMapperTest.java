package com.lukaszplawiak.projectapp.service.impl.mapper;

import com.lukaszplawiak.projectapp.dto.TaskRequestDto;
import com.lukaszplawiak.projectapp.model.Project;
import com.lukaszplawiak.projectapp.model.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.lukaszplawiak.projectapp.service.impl.mapper.TaskRequestDtoMapper.mapToTaskRequestDto;
import static org.assertj.core.api.Assertions.assertThat;

class TaskRequestDtoMapperTest {

    @Test
    void mapToTaskRequestDtoShouldBeSuccessful() {
        // given
        Project project = new Project();
        project.setId(1L);
        Task buildTask = Task.Builder.aTask()
                .withId(2L)
                .withName("Task Name")
                .withComment("Task Comment")
                .withDeadline(LocalDate.parse("2022-09-27"))
                .build();

        // when
        TaskRequestDto taskRequestDto = mapToTaskRequestDto(buildTask);

        // then
        assertThat(taskRequestDto)
                .isNotNull()
                .hasFieldOrPropertyWithValue("name", buildTask.getName())
                .hasFieldOrPropertyWithValue("comment", buildTask.getComment())
                .hasFieldOrPropertyWithValue("deadline", buildTask.getDeadline());
    }
}