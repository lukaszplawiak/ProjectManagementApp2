package com.lukaszplawiak.projectapp.service.impl.mapper;

import com.lukaszplawiak.projectapp.dto.ProjectResponseDto;
import com.lukaszplawiak.projectapp.model.Project;
import com.lukaszplawiak.projectapp.model.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.lukaszplawiak.projectapp.service.impl.mapper.ProjectResponseDtoMapper.mapToProjectResponseDto;
import static org.assertj.core.api.Assertions.assertThat;

class ProjectResponseDtoMapperTest {

    @Test
    void mapToProjectResponseDto_WhenInputDataIsCorrect_ShouldBeMap() {
        // given
        var task1 = new Task();
        var task2 = new Task();
        Set<Task> tasks = new HashSet<>();
        tasks.add(task1);
        tasks.add(task2);
        var buildProject = Project.ProjectBuilder.aProject()
                .withId(1L)
                .withTitle("Title")
                .withDescription("Description")
                .withDeadline(LocalDate.parse("2022-06-12"))
                .withDone(false)
                .withTasks(tasks)
                .build();

        // when
        var projectResponseDto = mapToProjectResponseDto(buildProject);

        // then
        assertThat(projectResponseDto)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", projectResponseDto.getId())
                .hasFieldOrPropertyWithValue("title", projectResponseDto.getTitle())
                .hasFieldOrPropertyWithValue("description", projectResponseDto.getDescription())
                .hasFieldOrPropertyWithValue("deadline", projectResponseDto.getDeadline())
                .hasFieldOrPropertyWithValue("done", projectResponseDto.isDone())
                .hasFieldOrPropertyWithValue("tasks", projectResponseDto.getTasks());
    }
}