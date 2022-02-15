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

    ProjectRepository mockProjectRepository = mock(ProjectRepository.class);
    TaskRepository mockTaskRepository = mock(TaskRepository.class);

    LocalDateTime now = LocalDateTime.of(2022, 01, 01, 0, 0);
    Clock fixedClock = Clock.fixed(now.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
    Clock fixedClockPlusDay = Clock.fixed(now.plusDays(1).toInstant(ZoneOffset.UTC), ZoneOffset.UTC);

    Long id = 1L;

    @Test
    void createTask_WhenProjectIsDone_ShouldThrowIllegalActionException() {
        // given
        Project project = Project.ProjectBuilder.aProject()
                .withDone(true)
                .build();
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);

        var taskServiceImpl = new TaskServiceImpl(null, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.createTask(anyLong(), null, null))
                .isInstanceOf(IllegalActionException.class)
                .hasMessageContaining("The action is impossible to execute");
    }

    @Test
    void createTask_WhenTaskNameIsNull_ShouldThrowIllegalInputException() {
        // given
        Project project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .build();
        var taskRequestDto = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName(null)
                .build();
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);

        var taskServiceImpl = new TaskServiceImpl(null, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.createTask(anyLong(), taskRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createTask_WhenTaskNameIsEmpty_ShouldThrowIllegalInputException() {
        // given
        Project project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .build();
        var taskRequestDto = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("")
                .build();
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);

        var taskServiceImpl = new TaskServiceImpl(null, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.createTask(anyLong(), taskRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createTask_WhenTaskNameIsBlank_ShouldThrowIllegalInputException() {
        // given
        Project project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .build();
        var taskRequestDto = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("   ")
                .build();

        when(mockProjectRepository.getById(anyLong())).thenReturn(project);

        var taskServiceImpl = new TaskServiceImpl(null, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.createTask(anyLong(), taskRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createTask_WhenTaskCommentIsNull_ShouldThrowIllegalInputException() {
        // given
        Project project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .build();
        var taskRequestDto = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Name")
                .withComment(null)
                .build();
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);

        var taskServiceImpl = new TaskServiceImpl(null, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.createTask(anyLong(), taskRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createTask_WhenTasksDeadlineIsNull_ShouldThrowIllegalInputException() {
        // given
        Project project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .build();
        var taskRequestDto = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Name")
                .withComment("Comment")
                .withDeadline(null)
                .build();
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);

        var taskServiceImpl = new TaskServiceImpl(null, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.createTask(anyLong(), taskRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createTask_WhenTasksDeadlineIsBeforeNow_ShouldThrowIllegalInputException() {
        // given
        Project project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .build();
        var taskRequestDto = TaskRequestDto.TaskRequestDtoBuilder.aTaskRequestDto()
                .withName("Name")
                .withComment("Comment")
                .withDeadline(LocalDate.now(fixedClock))
                .build();
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);

        var taskServiceImpl = new TaskServiceImpl(null, mockProjectRepository, null, fixedClockPlusDay);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.createTask(anyLong(), taskRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createTask_WhenTaskIsCreatedWithCorrectData_ShouldBeCreate() {
        // given
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
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);
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
    void updateTaskById_WhenUserTryToUpdateNotOwnTask_ShouldThrowIllegalAccessException() {
        // given
        var user1 = User.UserBuilder.anUser()
                .withId(id)
                .build();
        var user2 = User.UserBuilder.anUser()
                .withId(2L)
                .build();
        var task = Task.TaskBuilder.aTask()
                .withUser(user2)
                .build();
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.updateTaskById(null, anyLong(), null, user1))
                .isInstanceOf(IllegalAccessException.class)
                .hasMessageContaining("Access denied");
    }

    @Test
    void updateTaskById_WhenProjectIsDone_ShouldThrowIllegalActionException() {
        // given
        var project = Project.ProjectBuilder.aProject()
                .withDone(true)
                .build();
        var user = User.UserBuilder.anUser()
                .withId(id)
                .build();
        var task = Task.TaskBuilder.aTask()
                .withUser(user)
                .build();
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.updateTaskById(id, id, null, user))
                .isInstanceOf(IllegalActionException.class)
                .hasMessageContaining("The action is impossible to execute");
    }

    @Test
    void updateTaskById_WhenTaskNameIsNull_ShouldThrowIllegalInputException() {
        // given
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
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);
        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.updateTaskById(id, id, taskRequestDto, user))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateTaskById_WhenTasksTitleIsEmpty_ShouldThrowIllegalInputException() {
        // given
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
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);
        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.updateTaskById(id, id, taskRequestDto, user))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateTaskById_WhenTasksTitleIsBlank_ShouldThrowIllegalInputException() {
        // given
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
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);
        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.updateTaskById(id, id, taskRequestDto, user))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateTaskById_WhenTasksCommentIsNull_ShouldThrowIllegalInputException() {
        // given
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
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);
        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.updateTaskById(id, id, taskRequestDto, user))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateTaskById_WhenTasksDeadlineIsNull_ShouldThrowIllegalInputException() {
        // given
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
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, fixedClock);
        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.updateTaskById(id, id, taskRequestDto, user))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");

    }

    @Test
    void updateTaskById_WhenTasksDeadlineIsBeforeNow_ShouldThrowIllegalInputException() {
        // given
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
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, fixedClockPlusDay);
        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.updateTaskById(id, id, taskRequestDto, user))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateTaskById_WhenTaskIsWithCorrectData_ShouldBeUpdated() {
        // given
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
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);
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
    void deleteTaskById_WhenUserTryToDeleteNotOwnTask_ShouldThrowIllegalAccessException() {
        // given
        var user1 = User.UserBuilder.anUser()
                .withId(id)
                .build();
        var user2 = User.UserBuilder.anUser()
                .withId(2L)
                .build();
        var task = Task.TaskBuilder.aTask().
                withUser(user2)
                .build();
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.deleteTaskById(id, id, user1))
                .isInstanceOf(IllegalAccessException.class)
                .hasMessageContaining("Access denied");
    }

    @Test
    void deleteTaskById_WhenProjectIsDone_ShouldThrowIllegalActionException() {
        // given
        var project = Project.ProjectBuilder.aProject()
                .withDone(true)
                .build();
        var user = User.UserBuilder.anUser()
                .withId(id)
                .build();
        var task = Task.TaskBuilder.aTask().
                withUser(user)
                .build();
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.deleteTaskById(id, id, user))
                .isInstanceOf(IllegalActionException.class)
                .hasMessageContaining("The action is impossible to execute");
    }

    @Test
    void deleteTaskById_WhenTaskAndProjectAreUndone_ShouldBeDeleted() {
        // given
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
    void toggleTask_WhenUserTryToUpdateNotOwnTask_ShouldThrowIllegalAccessException() {
        // given
        var user1 = User.UserBuilder.anUser()
                .withId(id)
                .build();
        var user2 = User.UserBuilder.anUser()
                .withId(2L)
                .build();
        var task = Task.TaskBuilder.aTask().
                withUser(user2)
                .build();
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.toggleTask(id, id, user1))
                .isInstanceOf(IllegalAccessException.class)
                .hasMessageContaining("Access denied");
    }

    @Test
    void toggleTask_WhenProjectIsDone_ShouldThrowIllegalActionException() {
        // given
        var project = Project.ProjectBuilder.aProject()
                .withDone(true)
                .build();
        var user = User.UserBuilder.anUser()
                .withId(id)
                .build();
        var task = Task.TaskBuilder.aTask().
                withUser(user)
                .build();
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);
        when(mockTaskRepository.getById(anyLong())).thenReturn(task);

        var taskServiceImpl = new TaskServiceImpl(mockTaskRepository, mockProjectRepository, null, null);

        // when
        // then
        assertThatThrownBy(() -> taskServiceImpl.toggleTask(id, id, user))
                .isInstanceOf(IllegalActionException.class)
                .hasMessageContaining("The action is impossible to execute");
    }

    @Test
    void toggleTaskById_WhenUserToggleOwnTask_ShouldBeToggle() {
        // given
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