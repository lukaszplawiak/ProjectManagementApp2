package com.lukaszplawiak.projectapp.repository;

import com.lukaszplawiak.projectapp.controller.ControllerTestBase;
import com.lukaszplawiak.projectapp.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TaskRepositoryTest extends ControllerTestBase {

    @Autowired
    TaskRepository taskRepository;

    @Test
    void getById_WhenTaskIsInDatabase_ShouldGetTask() {
        // given
        // when
        Task task = taskRepository.getById(5L);

        // then
        assertThat(task).isNotNull();
        assertThat(task.getId()).isEqualTo(5);
    }
}