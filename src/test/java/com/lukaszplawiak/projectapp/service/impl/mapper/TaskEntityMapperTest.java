package com.lukaszplawiak.projectapp.service.impl.mapper;

import com.lukaszplawiak.projectapp.dto.TaskRequestDto;
import com.lukaszplawiak.projectapp.model.Project;
import com.lukaszplawiak.projectapp.model.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.lukaszplawiak.projectapp.service.impl.mapper.TaskEntityMapper.mapToTaskEntity;
import static org.assertj.core.api.Assertions.assertThat;

class TaskEntityMapperTest {

    @Test
    void mapToTaskEntityShouldBeSuccessful() {
        // given
        Project project = new Project();
        project.setId(1L);
        TaskRequestDto buildTask = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Task Name")
                .withComment("Task Comment")
                .withDeadline(LocalDate.parse("2022-09-27"))
                .build();

        // when
        Task task = mapToTaskEntity(buildTask);

        // then
        assertThat(task)
                .isNotNull()
                .hasFieldOrPropertyWithValue("name", buildTask.getName())
                .hasFieldOrPropertyWithValue("comment", buildTask.getComment())
                .hasFieldOrPropertyWithValue("deadline", buildTask.getDeadline());
    }
}