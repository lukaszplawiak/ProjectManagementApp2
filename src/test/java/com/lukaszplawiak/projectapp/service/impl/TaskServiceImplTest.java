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
        Project project = Project.ProjectBuilder.aProject()
                .withDone(true)
                .build();
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);

        var taskServiceImpl = new TaskServiceImpl(null, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.createTask(anyLong(), null, null))
                .isInstanceOf(IllegalActionException.class)
                .hasMessageContaining("The action is impossible to execute");
    }

    @Test
    void createTaskShouldThrowIllegalInputExceptionWhenTaskNameIsNull() {
        // given
        Project project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .build();
        var taskRequestDto = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName(null)
                .build();
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);

        var taskServiceImpl = new TaskServiceImpl(null, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.createTask(anyLong(), taskRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createTaskShouldThrowIllegalInputExceptionWhenTaskNameIsEmpty() {
        // given
        Project project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .build();
        var taskRequestDto = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("")
                .build();
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);

        var taskServiceImpl = new TaskServiceImpl(null, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.createTask(anyLong(), taskRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createTaskShouldThrowIllegalInputExceptionWhenTaskNameIsBlank() {
        // given
        Project project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .build();
        var taskRequestDto = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("   ")
                .build();

        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);

        var taskServiceImpl = new TaskServiceImpl(null, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.createTask(anyLong(), taskRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createTaskShouldThrowIllegalInputExceptionWhenTaskCommentIsNull() {
        // given
        Project project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .build();
        var taskRequestDto = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Name")
                .withComment(null)
                .build();
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);

        var taskServiceImpl = new TaskServiceImpl(null, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.createTask(anyLong(), taskRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createTaskShouldThrowIllegalInputExceptionWhenTasksDeadlineIsNull() {
        // given
        Project project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .build();
        var taskRequestDto = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Name")
                .withComment("Comment")
                .withDeadline(null)
                .build();
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);

        var taskServiceImpl = new TaskServiceImpl(null, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.createTask(anyLong(), taskRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createTaskShouldThrowIllegalInputExceptionWhenTasksDeadlineIsBeforeNow() {
        // given
        LocalDateTime now = LocalDateTime.of(2022, 01, 01, 0, 0);
        var fixedClock = Clock.fixed(now.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
        var fixedClockPlusDay = Clock.fixed(now.plusDays(1).toInstant(ZoneOffset.UTC), ZoneOffset.UTC);

        Project project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .build();
        var taskRequestDto = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Name")
                .withComment("Comment")
                .withDeadline(LocalDate.now(fixedClock))
                .build();
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);

        var taskServiceImpl = new TaskServiceImpl(null, mockProjectRepository, null, fixedClockPlusDay);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.createTask(anyLong(), taskRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createTaskShouldNotThrowAnyException() {
        // given
        LocalDateTime now = LocalDateTime.of(2022, 01, 01, 0, 0);
        var fixedClock = Clock.fixed(now.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
        var fixedClockPlusDay = Clock.fixed(now.plusDays(1).toInstant(ZoneOffset.UTC), ZoneOffset.UTC);

        Project project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .build();
        var task = Task.TaskBuilder.aTask()
                .build();
        var taskRequestDto = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Name")
                .withComment("Comment")
                .withDeadline(LocalDate.now(fixedClockPlusDay))
                .build();
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.save(any())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, fixedClock);

        // when
        // then
        assertThat(taskServiceImpl.createTask(anyLong(), taskRequestDto, null))
                .isNotInstanceOfAny(
                        IllegalAccessException.class,
                        IllegalActionException.class,
                        IllegalInputException.class
                );
    }

    @Test
    void updateTaskByIdShouldThrowIllegalAccessExceptionWhenUserTryToUpdateNotOwnTask() {
        // given
        var user1 = User.UserBuilder.anUser()
                .withId(1L)
                .build();
        var user2 = User.UserBuilder.anUser()
                .withId(2L)
                .build();
        var task = Task.TaskBuilder.aTask()
                .withUser(user2)
                .build();
        var mockProjectRepository = mock(ProjectRepository.class);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.updateTaskById(null, anyLong(), null, user1))
                .isInstanceOf(IllegalAccessException.class)
                .hasMessageContaining("Access denied");
    }

    @Test
    void updateTaskByIdShouldThrowIllegalActionExceptionWhenProjectIsDone() {
        // given
        Long id = 1L;
        var project = Project.ProjectBuilder.aProject()
                .withDone(true)
                .build();
        var user = User.UserBuilder.anUser()
                .withId(id)
                .build();
        var task = Task.TaskBuilder.aTask()
                .withUser(user)
                .build();
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.updateTaskById(id, id, null, user))
                .isInstanceOf(IllegalActionException.class)
                .hasMessageContaining("The action is impossible to execute");
    }

    @Test
    void updateTaskByIdShouldThrowIllegalInputExceptionWhenTaskNameIsNull() {
        // given
        Long id = 1L;
        var project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .build();
        var user = User.UserBuilder.anUser()
                .withId(id)
                .build();
        var task = Task.TaskBuilder.aTask()
                .withUser(user)
                .build();
        var taskRequestDto = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName(null)
                .build();
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);
        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.updateTaskById(id, id, taskRequestDto, user))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateTaskByIdShouldThrowIllegalInputExceptionWhenTasksTitleIsEmpty() {
        // given
        Long id = 1L;
        var project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .build();
        var user = User.UserBuilder.anUser()
                .withId(id)
                .build();
        var task = Task.TaskBuilder.aTask()
                .withUser(user)
                .build();
        var taskRequestDto = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("")
                .build();
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);
        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.updateTaskById(id, id, taskRequestDto, user))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateTaskByIdShouldThrowIllegalInputExceptionWhenTasksTitleIsBlank() {
        // given
        Long id = 1L;
        var project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .build();
        var user = User.UserBuilder.anUser()
                .withId(id)
                .build();
        var task = Task.TaskBuilder.aTask()
                .withUser(user)
                .build();
        var taskRequestDto = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("   ")
                .build();
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);
        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.updateTaskById(id, id, taskRequestDto, user))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateTaskByIdShouldThrowIllegalInputExceptionWhenTasksCommentIsNull() {
        // given
        Long id = 1L;
        var project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .build();
        var user = User.UserBuilder.anUser()
                .withId(id)
                .build();
        var task = Task.TaskBuilder.aTask()
                .withUser(user)
                .build();
        var taskRequestDto = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Name")
                .withComment(null)
                .build();
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);
        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.updateTaskById(id, id, taskRequestDto, user))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateTaskByIdShouldThrowIllegalInputExceptionWhenTasksDeadlineIsNull() {
        // given
        LocalDateTime now = LocalDateTime.of(2022, 01, 10, 0, 0);
        var fixedClock = Clock.fixed(now.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);

        Long id = 1L;
        var project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .build();
        var user = User.UserBuilder.anUser()
                .withId(id)
                .build();
        var task = Task.TaskBuilder.aTask()
                .withUser(user)
                .build();
        var taskRequestDto = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Name")
                .withComment("Comment")
                .withDeadline(null)
                .build();
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, fixedClock);
        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.updateTaskById(id, id, taskRequestDto, user))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");

    }

    @Test
    void updateTaskByIdShouldThrowIllegalInputExceptionWhenTasksDeadlineIsBeforeNow() {
        // given
        LocalDateTime now = LocalDateTime.of(2022, 01, 01, 0, 0);
        var fixedClock = Clock.fixed(now.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
        var fixedClockPlusDay = Clock.fixed(now.plusDays(1).toInstant(ZoneOffset.UTC), ZoneOffset.UTC);

        Long id = 1L;
        var project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .build();
        var user = User.UserBuilder.anUser()
                .withId(id)
                .build();
        var task = Task.TaskBuilder.aTask()
                .withUser(user)
                .build();
        var taskRequestDto = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Name")
                .withComment("Comment")
                .withDeadline(LocalDate.now(fixedClock))
                .build();
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, fixedClockPlusDay);
        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.updateTaskById(id, id, taskRequestDto, user))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateTaskByIdShouldNotThrowAnyException() {
        // given
        LocalDateTime now = LocalDateTime.of(2022, 01, 01, 0, 0);
        var fixedClock = Clock.fixed(now.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
        var fixedClockPlusDay = Clock.fixed(now.plusDays(1).toInstant(ZoneOffset.UTC), ZoneOffset.UTC);

        Long id = 1L;
        var project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .build();
        var user = User.UserBuilder.anUser()
                .withId(id)
                .build();
        var task = Task.TaskBuilder.aTask()
                .withUser(user)
                .build();
        var taskRequestDto = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Name")
                .withComment("Comment")
                .withDeadline(LocalDate.now(fixedClockPlusDay))
                .build();
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, fixedClock);

        // when
        // then
        assertThat(taskServiceImpl.updateTaskById(id, id, taskRequestDto, user))
                .isNotInstanceOfAny(
                        IllegalAccessException.class,
                        IllegalActionException.class,
                        IllegalInputException.class
                );
    }

    @Test
    void deleteTaskByIdShouldThrowIllegalAccessExceptionWhenUserTryToDeleteNotOwnTask() {
        // given
        Long id = 1L;
        var user1 = User.UserBuilder.anUser()
                .withId(id)
                .build();
        var user2 = User.UserBuilder.anUser()
                .withId(2L)
                .build();
        var task = Task.TaskBuilder.aTask().
                withUser(user2)
                .build();
        var mockProjectRepository = mock(ProjectRepository.class);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.deleteTaskById(id, id, user1))
                .isInstanceOf(IllegalAccessException.class)
                .hasMessageContaining("Access denied");
    }

    @Test
    void deleteTaskByIdShouldThrowIllegalActionExceptionWhenProjectIsDone() {
        // given
        Long id = 1L;
        var project = Project.ProjectBuilder.aProject()
                .withDone(true)
                .build();
        var user = User.UserBuilder.anUser()
                .withId(id)
                .build();
        var task = Task.TaskBuilder.aTask().
                withUser(user)
                .build();
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.deleteTaskById(id, id, user))
                .isInstanceOf(IllegalActionException.class)
                .hasMessageContaining("The action is impossible to execute");
    }

    @Test
    void deleteTaskByIdShouldBeSuccessful() {
        // given
        Long id = 1L;
        var user = User.UserBuilder.anUser()
                .withId(id)
                .build();
        var mockTaskServiceImpl = mock(TaskServiceImpl.class);

        // when
        mockTaskServiceImpl.deleteTaskById(id, id ,user);

        // then
        verify(mockTaskServiceImpl, times(1)).deleteTaskById(id, id ,user);
    }

    @Test
    void toggleTaskShouldThrowIllegalAccessExceptionWhenUserTryToUpdateNotOwnTask() {
        // given
        Long id = 1L;
        var user1 = User.UserBuilder.anUser()
                .withId(id)
                .build();
        var user2 = User.UserBuilder.anUser()
                .withId(2L)
                .build();
        var task = Task.TaskBuilder.aTask().
                withUser(user2)
                .build();
        var mockProjectRepository = mock(ProjectRepository.class);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.toggleTask(id, id, user1))
                .isInstanceOf(IllegalAccessException.class)
                .hasMessageContaining("Access denied");
    }

    @Test
    void toggleTaskShouldThrowIllegalActionExceptionWhenProjectIsDone() {
        // given
        Long id = 1L;
        var project = Project.ProjectBuilder.aProject()
                .withDone(true)
                .build();
        var user = User.UserBuilder.anUser()
                .withId(id)
                .build();
        var task = Task.TaskBuilder.aTask().
                withUser(user)
                .build();
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.toggleTask(id, id, user))
                .isInstanceOf(IllegalActionException.class)
                .hasMessageContaining("The action is impossible to execute");
    }

    @Test
    void toggleTaskByIdShouldBeSuccessful() {
        // given
        Long id = 1L;
        var user = User.UserBuilder.anUser()
                .withId(id)
                .build();
        var mockTaskServiceImpl = mock(TaskServiceImpl.class);

        // when
        mockTaskServiceImpl.toggleTask(id, id, user);

        // then
        verify(mockTaskServiceImpl, times(1)).toggleTask(id, id, user);
    }
}