package com.lukaszplawiak.projectapp.service.impl;

import com.lukaszplawiak.projectapp.dto.TaskRequestDto;
import com.lukaszplawiak.projectapp.exception.IllegalAccessException;
import com.lukaszplawiak.projectapp.exception.IllegalActionException;
import com.lukaszplawiak.projectapp.exception.IllegalInputException;
import com.lukaszplawiak.projectapp.model.Project;
import com.lukaszplawiak.projectapp.model.Task;
import com.lukaszplawiak.projectapp.model.User;
import com.lukaszplawiak.projectapp.repository.ProjectRepository;
import com.lukaszplawiak.projectapp.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class TaskServiceImplTest {

    @Test
    void createTaskShouldThrowIllegalActionExceptionWhenProjectIsDone() {
        // given
        var mockProject = mock(Project.class);
        when(mockProject.isDone()).thenReturn(true);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);

        var taskServiceImpl = new TaskServiceImpl(null, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.createTask(22L, null, null))
                .isInstanceOf(IllegalActionException.class)
                .hasMessageContaining("The action is impossible to execute");
    }

    @Test
    void createTaskShouldThrowIllegalInputExceptionWhenTasksNameIsNull() {
        // given
        var mockProject = mock(Project.class);
        when(mockProject.isDone()).thenReturn(false);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockTaskRepository = mock(TaskRepository.class);
        var mockTaskRequestDto = mock(TaskRequestDto.class);
        when(mockTaskRequestDto.getName()).thenReturn(null);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.createTask(22L, mockTaskRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createTaskShouldThrowIllegalInputExceptionWhenTasksNameIsEmpty() {
        // given
        var mockProject = mock(Project.class);
        when(mockProject.isDone()).thenReturn(false);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockTaskRepository = mock(TaskRepository.class);
        var mockTaskRequestDto = mock(TaskRequestDto.class);
        when(mockTaskRequestDto.getName()).thenReturn("");

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.createTask(22L, mockTaskRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createTaskShouldThrowIllegalInputExceptionWhenTasksNameIsBlank() {
        // given
        var mockProject = mock(Project.class);
        when(mockProject.isDone()).thenReturn(false);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockTaskRepository = mock(TaskRepository.class);
        var mockTaskRequestDto = mock(TaskRequestDto.class);
        when(mockTaskRequestDto.getName()).thenReturn("   ");

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.createTask(22L, mockTaskRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createTaskShouldThrowIllegalInputExceptionWhenTasksCommentIsNull() {
        // given
        var mockProject = mock(Project.class);
        when(mockProject.isDone()).thenReturn(false);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockTaskRepository = mock(TaskRepository.class);
        var mockTaskRequestDto = mock(TaskRequestDto.class);
        when(mockTaskRequestDto.getName()).thenReturn("Name");
        when(mockTaskRequestDto.getComment()).thenReturn(null);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.createTask(22L, mockTaskRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createTaskShouldThrowIllegalInputExceptionWhenTasksDeadlineIsNull() {
        // given
        var mockProject = mock(Project.class);
        when(mockProject.isDone()).thenReturn(false);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockTaskRepository = mock(TaskRepository.class);
        var mockTaskRequestDto = mock(TaskRequestDto.class);
        when(mockTaskRequestDto.getName()).thenReturn("Name");
        when(mockTaskRequestDto.getComment()).thenReturn("Comment");
        when(mockTaskRequestDto.getDeadline()).thenReturn(null);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.createTask(22L, mockTaskRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createTaskShouldThrowIllegalInputExceptionWhenTasksDeadlineIsBeforeNow() {
        // given
        LocalDateTime now = LocalDateTime.of(2022, 01, 01, 0, 0);
        var fixedClock = Clock.fixed(now.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
        var fixedClockPlusDay = Clock.fixed(now.plusDays(1).toInstant(ZoneOffset.UTC), ZoneOffset.UTC);

        var mockProject = mock(Project.class);
        when(mockProject.isDone()).thenReturn(false);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockTaskRepository = mock(TaskRepository.class);
        var mockTaskRequestDto = mock(TaskRequestDto.class);
        when(mockTaskRequestDto.getName()).thenReturn("Name");
        when(mockTaskRequestDto.getComment()).thenReturn("Comment");
        when(mockTaskRequestDto.getDeadline()).thenReturn(LocalDate.now(fixedClock));

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, fixedClockPlusDay);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.createTask(22L, mockTaskRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createTaskShouldNotThrowAnyException() {
        // given
        LocalDateTime now = LocalDateTime.of(2022, 01, 01, 0, 0);
        var fixedClock = Clock.fixed(now.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
        var fixedClockPlusDay = Clock.fixed(now.plusDays(1).toInstant(ZoneOffset.UTC), ZoneOffset.UTC);

        var mockProject = mock(Project.class);
        when(mockProject.isDone()).thenReturn(false);
        var newTask = mock(Task.class);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.save(any())).thenReturn(newTask);
        var mockTaskRequestDto = mock(TaskRequestDto.class);
        when(mockTaskRequestDto.getName()).thenReturn("Name");
        when(mockTaskRequestDto.getComment()).thenReturn("Comment");
        when(mockTaskRequestDto.getDeadline()).thenReturn(LocalDate.now(fixedClockPlusDay));

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, fixedClock);

        // when
        // then
        assertThat(taskServiceImpl.createTask(22L, mockTaskRequestDto, null))
                .isNotInstanceOfAny(
                        IllegalAccessException.class,
                        IllegalActionException.class,
                        IllegalInputException.class
                );
    }

    @Test
    void updateTaskByIdShouldThrowIllegalAccessExceptionWhenUserTryToUpdateNotOwnTask() {
        // given
        var mockUser1 = mock(User.class);
        when(mockUser1.getId()).thenReturn(1L);
        var mockUser2 = mock(User.class);
        when(mockUser2.getId()).thenReturn(2L);
        var mockTask = mock(Task.class);
        when(mockTask.getUser()).thenReturn(mockUser2);
        var mockProjectRepository = mock(ProjectRepository.class);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(mockTask);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.updateTaskById(null, 1L, null, mockUser1))
                .isInstanceOf(IllegalAccessException.class)
                .hasMessageContaining("Access denied");
    }

    @Test
    void updateTaskByIdShouldThrowIllegalActionExceptionWhenProjectIsDone() {
        // given
        var mockProject = mock(Project.class);
        when(mockProject.isDone()).thenReturn(true);
        var mockUser1 = mock(User.class);
        when(mockUser1.getId()).thenReturn(1L);
        var mockTask = mock(Task.class);
        when(mockTask.getUser()).thenReturn(mockUser1);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(mockTask);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.updateTaskById(22L, 22L, null, mockUser1))
                .isInstanceOf(IllegalActionException.class)
                .hasMessageContaining("The action is impossible to execute");
    }

    @Test
    void updateTaskByIdShouldThrowIllegalInputExceptionWhenTasksTitleIsNull() {
        // given
        var mockProject = mock(Project.class);
        when(mockProject.isDone()).thenReturn(false);
        var mockUser1 = mock(User.class);
        when(mockUser1.getId()).thenReturn(1L);
        var mockTask = mock(Task.class);
        when(mockTask.getUser()).thenReturn(mockUser1);
        var mockTaskRequestDto = mock(TaskRequestDto.class);
        when(mockTaskRequestDto.getName()).thenReturn(null);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(mockTask);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);
        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.updateTaskById(22L, 22L, mockTaskRequestDto, mockUser1))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateTaskByIdShouldThrowIllegalInputExceptionWhenTasksTitleIsEmpty() {
        // given
        var mockProject = mock(Project.class);
        when(mockProject.isDone()).thenReturn(false);
        var mockUser1 = mock(User.class);
        when(mockUser1.getId()).thenReturn(1L);
        var mockTask = mock(Task.class);
        when(mockTask.getUser()).thenReturn(mockUser1);
        var mockTaskRequestDto = mock(TaskRequestDto.class);
        when(mockTaskRequestDto.getName()).thenReturn("");
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(mockTask);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);
        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.updateTaskById(22L, 22L, mockTaskRequestDto, mockUser1))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateTaskByIdShouldThrowIllegalInputExceptionWhenTasksTitleIsBlank() {
        // given
        var mockProject = mock(Project.class);
        when(mockProject.isDone()).thenReturn(false);
        var mockUser1 = mock(User.class);
        when(mockUser1.getId()).thenReturn(1L);
        var mockTask = mock(Task.class);
        when(mockTask.getUser()).thenReturn(mockUser1);
        var mockTaskRequestDto = mock(TaskRequestDto.class);
        when(mockTaskRequestDto.getName()).thenReturn("   ");
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(mockTask);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);
        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.updateTaskById(22L, 22L, mockTaskRequestDto, mockUser1))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateTaskByIdShouldThrowIllegalInputExceptionWhenTasksCommentIsNull() {
        // given
        var mockProject = mock(Project.class);
        when(mockProject.isDone()).thenReturn(false);
        var mockUser1 = mock(User.class);
        when(mockUser1.getId()).thenReturn(1L);
        var mockTask = mock(Task.class);
        when(mockTask.getUser()).thenReturn(mockUser1);
        var mockTaskRequestDto = mock(TaskRequestDto.class);
        when(mockTaskRequestDto.getName()).thenReturn("Name");
        when(mockTaskRequestDto.getComment()).thenReturn(null);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(mockTask);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);
        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.updateTaskById(22L, 22L, mockTaskRequestDto, mockUser1))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateTaskByIdShouldThrowIllegalInputExceptionWhenTasksDeadlineIsNull() {
        // given
        LocalDateTime now = LocalDateTime.of(2022, 01, 10, 0, 0);
        var fixedClock = Clock.fixed(now.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);

        var mockProject = mock(Project.class);
        when(mockProject.isDone()).thenReturn(false);
        var mockUser1 = mock(User.class);
        when(mockUser1.getId()).thenReturn(1L);
        var mockTask = mock(Task.class);
        when(mockTask.getUser()).thenReturn(mockUser1);
        var mockTaskRequestDto = mock(TaskRequestDto.class);
        when(mockTaskRequestDto.getName()).thenReturn("Name");
        when(mockTaskRequestDto.getComment()).thenReturn("Ok");
        when(mockTaskRequestDto.getDeadline()).thenReturn(null);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(mockTask);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, fixedClock);
        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.updateTaskById(22L, 22L, mockTaskRequestDto, mockUser1))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");

    }

    @Test
    void updateTaskByIdShouldThrowIllegalInputExceptionWhenTasksDeadlineIsBeforeNow() {
        // given
        LocalDateTime now = LocalDateTime.of(2022, 01, 01, 0, 0);
        var fixedClock = Clock.fixed(now.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
        var fixedClockPlusDay = Clock.fixed(now.plusDays(1).toInstant(ZoneOffset.UTC), ZoneOffset.UTC);

        var mockProject = mock(Project.class);
        when(mockProject.isDone()).thenReturn(false);
        var mockUser1 = mock(User.class);
        when(mockUser1.getId()).thenReturn(1L);
        var mockTask = mock(Task.class);
        when(mockTask.getUser()).thenReturn(mockUser1);
        var mockTaskRequestDto = mock(TaskRequestDto.class);
        when(mockTaskRequestDto.getName()).thenReturn("Name");
        when(mockTaskRequestDto.getComment()).thenReturn("Ok");
        when(mockTaskRequestDto.getDeadline()).thenReturn(LocalDate.now(fixedClock));
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(mockTask);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, fixedClockPlusDay);
        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.updateTaskById(22L, 22L, mockTaskRequestDto, mockUser1))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateTaskByIdShouldNotThrowAnyException() {
        // given
        LocalDateTime now = LocalDateTime.of(2022, 01, 01, 0, 0);
        var fixedClock = Clock.fixed(now.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
        var fixedClockPlusDay = Clock.fixed(now.plusDays(1).toInstant(ZoneOffset.UTC), ZoneOffset.UTC);

        var mockProject = mock(Project.class);
        when(mockProject.isDone()).thenReturn(false);
        var mockUser1 = mock(User.class);
        when(mockUser1.getId()).thenReturn(1L);
        var mockTask = mock(Task.class);
        when(mockTask.getUser()).thenReturn(mockUser1);
        var mockTaskRequestDto = mock(TaskRequestDto.class);
        when(mockTaskRequestDto.getName()).thenReturn("Name");
        when(mockTaskRequestDto.getComment()).thenReturn("Ok");
        when(mockTaskRequestDto.getDeadline()).thenReturn(LocalDate.now(fixedClockPlusDay));
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(mockTask);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, fixedClock);

        // when
        // then
        assertThat(taskServiceImpl.updateTaskById(22L, 22L, mockTaskRequestDto, mockUser1))
                .isNotInstanceOfAny(
                        IllegalAccessException.class,
                        IllegalActionException.class,
                        IllegalInputException.class
                );
    }

    @Test
    void deleteTaskByIdShouldThrowIllegalAccessExceptionWhenUserTryToUpdateNotOwnTask() {
        // given
        var mockUser1 = mock(User.class);
        when(mockUser1.getId()).thenReturn(1L);
        var mockUser2 = mock(User.class);
        when(mockUser2.getId()).thenReturn(2L);
        var mockTask = mock(Task.class);
        when(mockTask.getUser()).thenReturn(mockUser2);
        var mockProjectRepository = mock(ProjectRepository.class);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(mockTask);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.deleteTaskById(22L, 22L, mockUser1))
                .isInstanceOf(IllegalAccessException.class)
                .hasMessageContaining("Access denied");
    }

    @Test
    void deleteTaskByIdShouldThrowIllegalActionExceptionWhenProjectIsDone() {
        // given
        var mockProject = mock(Project.class);
        when(mockProject.isDone()).thenReturn(true);
        var mockUser1 = mock(User.class);
        when(mockUser1.getId()).thenReturn(1L);
        var mockTask = mock(Task.class);
        when(mockTask.getUser()).thenReturn(mockUser1);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(mockTask);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.deleteTaskById(22L, 22L, mockUser1))
                .isInstanceOf(IllegalActionException.class)
                .hasMessageContaining("The action is impossible to execute");
    }

    @Test
    void deleteTaskByIdShouldBeSuccessful() {
        // given
        var mockUser1 = mock(User.class);
        var mockTaskServiceImpl = mock(TaskServiceImpl.class);

        // when
        mockTaskServiceImpl.deleteTaskById(1L, 1L ,mockUser1);

        // then
        verify(mockTaskServiceImpl, times(1)).deleteTaskById(1L, 1L ,mockUser1);
    }

    @Test
    void toggleTaskShouldThrowIllegalAccessExceptionWhenUserTryToUpdateNotOwnTask() {
        // given
        var mockUser1 = mock(User.class);
        when(mockUser1.getId()).thenReturn(1L);
        var mockUser2 = mock(User.class);
        when(mockUser2.getId()).thenReturn(2L);
        var mockTask = mock(Task.class);
        when(mockTask.getUser()).thenReturn(mockUser2);
        var mockProjectRepository = mock(ProjectRepository.class);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(mockTask);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.toggleTask(22L, 22L, mockUser1))
                .isInstanceOf(IllegalAccessException.class)
                .hasMessageContaining("Access denied");
    }

    @Test
    void toggleTaskShouldThrowIllegalActionExceptionWhenProjectIsDone() {
        // given
        var mockProject = mock(Project.class);
        when(mockProject.isDone()).thenReturn(true);
        var mockUser1 = mock(User.class);
        when(mockUser1.getId()).thenReturn(1L);
        var mockTask = mock(Task.class);
        when(mockTask.getUser()).thenReturn(mockUser1);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(mockTask);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.toggleTask(1L, 1L, mockUser1))
                .isInstanceOf(IllegalActionException.class)
                .hasMessageContaining("The action is impossible to execute");
    }

    @Test
    void toggleTaskByIdShouldBeSuccessful() {
        // given
        var mockUser1 = mock(User.class);
        var mockTaskServiceImpl = mock(TaskServiceImpl.class);

        // when
        mockTaskServiceImpl.toggleTask(1L, 1L, mockUser1);

        // then
        verify(mockTaskServiceImpl, times(1)).toggleTask(1L, 1L, mockUser1);
    }

}