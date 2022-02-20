package com.lukaszplawiak.projectapp.service.impl.mapper;

import com.lukaszplawiak.projectapp.dto.TaskRequestDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.lukaszplawiak.projectapp.service.impl.mapper.TaskEntityMapper.mapToTaskEntity;
import static org.assertj.core.api.Assertions.assertThat;

class TaskEntityMapperTest {

    @Test
    void mapToTaskEntity_ShouldBeMap() {
        // given
        var buildTask = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Task Name")
                .withComment("Task Comment")
                .withDeadline(LocalDate.parse("2022-09-27"))
                .build();

        // when
        var task = mapToTaskEntity(buildTask);

        // then
        assertThat(task).isNotNull();
        assertThat(task.getName()).isEqualTo("Task Name");
        assertThat(task.getComment()).isEqualTo("Task Comment");
        assertThat(task.getDeadline()).isEqualTo(LocalDate.parse("2022-09-27"));
    }
}