package com.lukaszplawiak.projectapp.repository;

import com.lukaszplawiak.projectapp.controller.ControllerTestBase;
import com.lukaszplawiak.projectapp.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ProjectRepositoryTest extends ControllerTestBase {

    @Autowired
    ProjectRepository projectRepository;

    @Test
    void getById_WhenProjectIsinDatabase_ShouldGetProject() {
        // given
        // when
        Project project = projectRepository.getById(4L);

        // then
        assertThat(project).isNotNull();
        assertThat(project.getId()).isEqualTo(4);
    }
}