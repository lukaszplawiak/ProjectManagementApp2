package com.lukaszplawiak.projectapp.service.impl;

import com.lukaszplawiak.projectapp.dto.TaskRequestDto;
import com.lukaszplawiak.projectapp.exception.IllegalCreateTaskException;
import com.lukaszplawiak.projectapp.model.Project;
import com.lukaszplawiak.projectapp.model.Task;
import com.lukaszplawiak.projectapp.repository.ProjectRepository;
import com.lukaszplawiak.projectapp.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static java.time.LocalDate.parse;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class TaskServiceImplTest {

    @Test
    void createTaskShouldThrowIllegalCreateTaskExceptionWhenProjectIsDone() {
        // given
        Project project = new Project();
        project.setDone(true);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);
        var taskServiceImpl = new TaskServiceImpl(null, mockProjectRepository, null);
        // when
        var exception = catchThrowable(() -> taskServiceImpl.createTask(22L, null, null));
        // then
        assertThatThrownBy(() -> taskServiceImpl.createTask(22L, null, null))
                .isInstanceOf(IllegalCreateTaskException.class);
        assertThat(exception).isInstanceOf(IllegalCreateTaskException.class)
                .hasMessageContaining("Create task is impossible");
    }

    @Test
    void createTaskShouldNotThrowIllegalCreateTaskExceptionWhenProjectIsNotDone() {
        // given
        var project = new Project();
        project.setDone(false);
        var taskRequestDto = mock(TaskRequestDto.class);
        var newTask = mock(Task.class);
        var mockProjectRepository = mock(ProjectRepository.class);
        var mockTaskRepository = mock(TaskRepository.class);
        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null);
        // when
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);
        when(mockTaskRepository.save(any())).thenReturn(newTask);
        // then
        assertThat(taskServiceImpl.createTask(22L, taskRequestDto, null)).isNotInstanceOf(IllegalCreateTaskException.class);
    }

    @Test
    void getTaskByIdShouldThrowIllegalArgumentExceptionWhenObjectsAreNotEquals() {
        // given
        var projectWithTask = mock(Project.class);
        projectWithTask.setId(1L);

        var projectWithNoTask = mock(Project.class);
        projectWithNoTask.setId(2L);

        var task = mock(Task.class);
        task.setProject(projectWithTask);

        var mockTaskRepository = mock(TaskRepository.class);
        var mockProjectRepository = mock(ProjectRepository.class);
        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null);
        // when
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);
        when(mockProjectRepository.getById(anyLong())).thenReturn(projectWithNoTask);

        when(task.getProject()).thenReturn(projectWithTask);
        when(projectWithNoTask.getId()).thenReturn(2L);

        var exception = catchThrowable(() -> taskServiceImpl.getTaskById(1L, 2L));
        // then
        assertThatThrownBy(() -> taskServiceImpl.getTaskById(1L, 2L))
                .isInstanceOf(IllegalArgumentException.class);
        assertThat(exception).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Task does not belong to project");
    }
}