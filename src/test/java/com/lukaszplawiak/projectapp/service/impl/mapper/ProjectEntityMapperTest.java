package com.lukaszplawiak.projectapp.service.impl.mapper;

import com.lukaszplawiak.projectapp.dto.ProjectRequestDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.lukaszplawiak.projectapp.service.impl.mapper.ProjectEntityMapper.mapToProjectEntity;
import static org.assertj.core.api.Assertions.assertThat;

class ProjectEntityMapperTest {

    @Test
    void mapToProjectEntity_ShouldBeMap() {
        // given
        var buildProject = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("Title")
                .withDescription("Description")
                .withDeadline(LocalDate.parse("2022-06-12"))
                .build();

        // when
        var project = mapToProjectEntity(buildProject);

        // then
        assertThat(project).isNotNull();
        assertThat(project.getTitle()).isEqualTo("Title");
        assertThat(project.getDescription()).isEqualTo("Description");
        assertThat(project.getDeadline()).isEqualTo(LocalDate.parse("2022-06-12"));
    }
}