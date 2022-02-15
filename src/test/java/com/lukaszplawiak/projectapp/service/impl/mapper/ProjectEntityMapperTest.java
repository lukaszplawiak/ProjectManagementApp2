package com.lukaszplawiak.projectapp.service.impl.mapper;

import com.lukaszplawiak.projectapp.dto.ProjectRequestDto;
import com.lukaszplawiak.projectapp.model.Project;
import com.lukaszplawiak.projectapp.model.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.lukaszplawiak.projectapp.service.impl.mapper.ProjectEntityMapper.mapToProjectEntity;
import static org.assertj.core.api.Assertions.assertThat;

class ProjectEntityMapperTest {

    @Test
    void mapToProjectEntity_WhenInputDataIsCorrect_ShouldBeMap() {
        // given
        var task1 = new Task();
        var task2 = new Task();
        Set<Task> tasks = new HashSet<>();
        tasks.add(task1);
        tasks.add(task2);
        var buildProject = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("Title")
                .withDescription("Description")
                .withDeadline(LocalDate.parse("2022-06-12"))
                .build();

        // when
        var project = mapToProjectEntity(buildProject);

        // then
        assertThat(project)
                .isNotNull()
                .hasFieldOrPropertyWithValue("title", project.getTitle())
                .hasFieldOrPropertyWithValue("description", project.getDescription())
                .hasFieldOrPropertyWithValue("deadline", project.getDeadline());
    }
}