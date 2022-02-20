package com.lukaszplawiak.projectapp.service.impl.mapper;

import com.lukaszplawiak.projectapp.model.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.lukaszplawiak.projectapp.service.impl.mapper.TaskRequestDtoMapper.mapToTaskRequestDto;
import static org.assertj.core.api.Assertions.assertThat;

class TaskRequestDtoMapperTest {

    @Test
    void mapToTaskRequestDto_ShouldBeMap() {
        // given
        var task = Task.TaskBuilder.aTask()
                .withName("Task Name")
                .withComment("Task Comment")
                .withDeadline(LocalDate.parse("2022-09-27"))
                .build();

        // when
        var taskRequestDto = mapToTaskRequestDto(task);

        // then
        assertThat(taskRequestDto).isNotNull();
        assertThat(taskRequestDto.getName()).isEqualTo("Task Name");
        assertThat(taskRequestDto.getComment()).isEqualTo("Task Comment");
        assertThat(taskRequestDto.getDeadline()).isEqualTo(LocalDate.parse("2022-09-27"));
    }
}