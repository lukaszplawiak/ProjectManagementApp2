package com.lukaszplawiak.projectapp.service.impl;

import com.lukaszplawiak.projectapp.dto.ProjectRequestDto;
import com.lukaszplawiak.projectapp.exception.IllegalAccessException;
import com.lukaszplawiak.projectapp.exception.IllegalActionException;
import com.lukaszplawiak.projectapp.exception.IllegalInputException;
import com.lukaszplawiak.projectapp.model.Project;
import com.lukaszplawiak.projectapp.model.Task;
import com.lukaszplawiak.projectapp.model.User;
import com.lukaszplawiak.projectapp.repository.ProjectRepository;
import com.lukaszplawiak.projectapp.repository.TaskRepository;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ProjectServiceImplTest {

    @Test
    void createProjectShouldThrowIllegalInputExceptionWhenProjectsTitleIsNull() {
        // given
        var mockProject = mock(Project.class);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockProjectRequestDto = mock(ProjectRequestDto.class);
        when(mockProjectRequestDto.getTitle()).thenReturn(null);

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.createProject(mockProjectRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createProjectShouldThrowIllegalInputExceptionWhenProjectsTitleIsEmpty() {
        // given
        var mockProject = mock(Project.class);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockProjectRequestDto = mock(ProjectRequestDto.class);
        when(mockProjectRequestDto.getTitle()).thenReturn("");

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.createProject(mockProjectRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createProjectShouldThrowIllegalInputExceptionWhenProjectsTitleIsBlank() {
        // given
        var mockProject = mock(Project.class);
        when(mockProject.isDone()).thenReturn(false);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockProjectRequestDto = mock(ProjectRequestDto.class);
        when(mockProjectRequestDto.getTitle()).thenReturn("   ");

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.createProject(mockProjectRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createProjectShouldThrowIllegalInputExceptionWhenProjectsDescriptionIsNull() {
        // given
        var mockProject = mock(Project.class);
        when(mockProject.isDone()).thenReturn(false);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockProjectRequestDto = mock(ProjectRequestDto.class);
        when(mockProjectRequestDto.getTitle()).thenReturn("Title");
        when(mockProjectRequestDto.getDescription()).thenReturn(null);

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.createProject(mockProjectRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createProjectShouldThrowIllegalInputExceptionWhenProjectsDeadlineIsNull() {
        // given
        var mockProject = mock(Project.class);
        when(mockProject.isDone()).thenReturn(false);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockProjectRequestDto = mock(ProjectRequestDto.class);
        when(mockProjectRequestDto.getTitle()).thenReturn("Title");
        when(mockProjectRequestDto.getDescription()).thenReturn("Description");
        when(mockProjectRequestDto.getDeadline()).thenReturn(null);

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.createProject(mockProjectRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createProjectShouldThrowIllegalInputExceptionWhenProjectsDeadlineIsBeforeNow() {
        // given
        LocalDateTime now = LocalDateTime.of(2022, 01, 01, 0, 0);
        var fixedClock = Clock.fixed(now.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
        var fixedClockPlusDay = Clock.fixed(now.plusDays(1).toInstant(ZoneOffset.UTC), ZoneOffset.UTC);

        var mockProject = mock(Project.class);
        when(mockProject.isDone()).thenReturn(false);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockProjectRequestDto = mock(ProjectRequestDto.class);
        when(mockProjectRequestDto.getTitle()).thenReturn("Title");
        when(mockProjectRequestDto.getDescription()).thenReturn("Description");
        when(mockProjectRequestDto.getDeadline()).thenReturn(LocalDate.now(fixedClock));

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, fixedClockPlusDay);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.createProject(mockProjectRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createProjectShouldNotThrowAnyException() {
        // given
        LocalDateTime now = LocalDateTime.of(2022, 01, 01, 0, 0);
        var fixedClock = Clock.fixed(now.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
        var fixedClockPlusDay = Clock.fixed(now.plusDays(1).toInstant(ZoneOffset.UTC), ZoneOffset.UTC);

        var mockProject = mock(Project.class);
        when(mockProject.isDone()).thenReturn(false);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockProjectRequestDto = mock(ProjectRequestDto.class);
        when(mockProjectRequestDto.getTitle()).thenReturn("Title");
        when(mockProjectRequestDto.getDescription()).thenReturn("Description");
        when(mockProjectRequestDto.getDeadline()).thenReturn(LocalDate.now(fixedClockPlusDay));

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, fixedClock);

        // when
        // then
        assertThat(projectServiceImpl.createProject(mockProjectRequestDto, null))
                .isNotInstanceOfAny(
                        IllegalAccessException.class,
                        IllegalActionException.class,
                        IllegalInputException.class
                );
    }

    @Test
    void updateProjectShouldThrowIllegalAccessExceptionWhenUserTryToUpdateNotOwnProject() {
        // given
        var mockUser1 = mock(User.class);
        when(mockUser1.getId()).thenReturn(1L);
        var mockUser2 = mock(User.class);
        when(mockUser2.getId()).thenReturn(2L);
        var mockProject = mock(Project.class);
        when(mockProject.getUser()).thenReturn(mockUser2);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.updateProject( null , 1L , mockUser1))
                .isInstanceOf(IllegalAccessException.class)
                .hasMessageContaining("Access denied");
    }

    @Test
    void updateProjectShouldThrowIllegalActionExceptionWhenProjectIsDone() {
        // given
        var mockUser1 = mock(User.class);
        when(mockUser1.getId()).thenReturn(1L);
        var mockUser2 = mock(User.class);
        when(mockUser2.getId()).thenReturn(2L);
        var mockProject = mock(Project.class);
        when(mockProject.getUser()).thenReturn(mockUser1);
        when(mockProject.isDone()).thenReturn(true);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.updateProject(null, 1L, mockUser1))
                .isInstanceOf(IllegalActionException.class)
                .hasMessageContaining("The action is impossible to execute");
    }

    @Test
    void updateProjectShouldThrowIllegalActionExceptionWhenProjectsTitleIsNull() {
        // given
        var mockUser1 = mock(User.class);
        when(mockUser1.getId()).thenReturn(1L);
        var mockUser2 = mock(User.class);
        when(mockUser2.getId()).thenReturn(2L);
        var mockProject = mock(Project.class);
        when(mockProject.getUser()).thenReturn(mockUser1);
        when(mockProject.isDone()).thenReturn(false);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockProjectRequestDto = mock(ProjectRequestDto.class);
        when(mockProjectRequestDto.getTitle()).thenReturn(null);



        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.updateProject(mockProjectRequestDto, 1L, mockUser1))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateProjectShouldThrowIllegalActionExceptionWhenProjectsTitleIsEmpty() {
        // given
        var mockUser1 = mock(User.class);
        when(mockUser1.getId()).thenReturn(1L);
        var mockUser2 = mock(User.class);
        when(mockUser2.getId()).thenReturn(2L);
        var mockProject = mock(Project.class);
        when(mockProject.getUser()).thenReturn(mockUser1);
        when(mockProject.isDone()).thenReturn(false);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockProjectRequestDto = mock(ProjectRequestDto.class);
        when(mockProjectRequestDto.getTitle()).thenReturn("");

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.updateProject(mockProjectRequestDto, 1L, mockUser1))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateProjectShouldThrowIllegalActionExceptionWhenProjectsTitleIsBlank() {
        // given
        var mockUser1 = mock(User.class);
        when(mockUser1.getId()).thenReturn(1L);
        var mockUser2 = mock(User.class);
        when(mockUser2.getId()).thenReturn(2L);
        var mockProject = mock(Project.class);
        when(mockProject.getUser()).thenReturn(mockUser1);
        when(mockProject.isDone()).thenReturn(false);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockProjectRequestDto = mock(ProjectRequestDto.class);
        when(mockProjectRequestDto.getTitle()).thenReturn("   ");

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.updateProject(mockProjectRequestDto, 1L, mockUser1))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateProjectShouldThrowIllegalActionExceptionWhenProjectsDescriptionIsNull() {
        // given
        var mockUser1 = mock(User.class);
        when(mockUser1.getId()).thenReturn(1L);
        var mockUser2 = mock(User.class);
        when(mockUser2.getId()).thenReturn(2L);
        var mockProject = mock(Project.class);
        when(mockProject.getUser()).thenReturn(mockUser1);
        when(mockProject.isDone()).thenReturn(false);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockProjectRequestDto = mock(ProjectRequestDto.class);
        when(mockProjectRequestDto.getTitle()).thenReturn("Title");
        when(mockProjectRequestDto.getDescription()).thenReturn(null);

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.updateProject(mockProjectRequestDto, 1L, mockUser1))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateProjectShouldThrowIllegalActionExceptionWhenProjectsDeadlineIsNull() {
        // given
        var mockUser1 = mock(User.class);
        when(mockUser1.getId()).thenReturn(1L);
        var mockUser2 = mock(User.class);
        when(mockUser2.getId()).thenReturn(2L);
        var mockProject = mock(Project.class);
        when(mockProject.getUser()).thenReturn(mockUser1);
        when(mockProject.isDone()).thenReturn(false);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockProjectRequestDto = mock(ProjectRequestDto.class);
        when(mockProjectRequestDto.getTitle()).thenReturn("Title");
        when(mockProjectRequestDto.getDescription()).thenReturn("Description");
        when(mockProjectRequestDto.getDeadline()).thenReturn(null);

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.updateProject(mockProjectRequestDto, 1L, mockUser1))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateProjectShouldThrowIllegalActionExceptionWhenProjectsDeadlineIsBeforeNow() {
        // given
        LocalDateTime now = LocalDateTime.of(2022, 01, 01, 0, 0);
        var fixedClock = Clock.fixed(now.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
        var fixedClockPlusDay = Clock.fixed(now.plusDays(1).toInstant(ZoneOffset.UTC), ZoneOffset.UTC);

        var mockUser1 = mock(User.class);
        when(mockUser1.getId()).thenReturn(1L);
        var mockUser2 = mock(User.class);
        when(mockUser2.getId()).thenReturn(2L);
        var mockProject = mock(Project.class);
        when(mockProject.getUser()).thenReturn(mockUser1);
        when(mockProject.isDone()).thenReturn(false);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockProjectRequestDto = mock(ProjectRequestDto.class);
        when(mockProjectRequestDto.getTitle()).thenReturn("Title");
        when(mockProjectRequestDto.getDescription()).thenReturn("Description");
        when(mockProjectRequestDto.getDeadline()).thenReturn(LocalDate.now(fixedClock));

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, fixedClockPlusDay);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.updateProject(mockProjectRequestDto, 1L, mockUser1))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateProjectShouldNotThrowAnyException() {
        // given
        LocalDateTime now = LocalDateTime.of(2022, 01, 01, 0, 0);
        var fixedClock = Clock.fixed(now.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
        var fixedClockPlusDay = Clock.fixed(now.plusDays(1).toInstant(ZoneOffset.UTC), ZoneOffset.UTC);

        var mockUser1 = mock(User.class);
        when(mockUser1.getId()).thenReturn(1L);
        var mockUser2 = mock(User.class);
        when(mockUser2.getId()).thenReturn(2L);
        var mockProject = mock(Project.class);
        when(mockProject.getUser()).thenReturn(mockUser1);
        when(mockProject.isDone()).thenReturn(false);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);
        var mockProjectRequestDto = mock(ProjectRequestDto.class);
        when(mockProjectRequestDto.getTitle()).thenReturn("Title");
        when(mockProjectRequestDto.getDescription()).thenReturn("Description");
        when(mockProjectRequestDto.getDeadline()).thenReturn(LocalDate.now(fixedClockPlusDay));

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, fixedClock);

        // when
        // then
        assertThat(projectServiceImpl.updateProject( mockProjectRequestDto , 1L , mockUser1))
                .isNotInstanceOfAny(
                        IllegalAccessException.class,
                        IllegalActionException.class,
                        IllegalInputException.class
                );
    }

    @Test
    void deleteProjectByIdShouldThrowIllegalAccessExceptionWhenUserTryToUpdateNotOwnProject() {
        // given
        var mockUser1 = mock(User.class);
        when(mockUser1.getId()).thenReturn(1L);
        var mockUser2 = mock(User.class);
        when(mockUser2.getId()).thenReturn(2L);
        var mockProject = mock(Project.class);
        when(mockProject.getUser()).thenReturn(mockUser1);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.deleteProjectById(22L, mockUser2))
                .isInstanceOf(IllegalAccessException.class)
                .hasMessageContaining("Access denied");
    }

    @Test
    void deleteProjectByIdShouldNotThrowAnyException() {
        // given
        var mockUser1 = mock(User.class);
        when(mockUser1.getId()).thenReturn(1L);
        var mockProject = mock(Project.class);
        when(mockProject.isDone()).thenReturn(false);
        when(mockProject.getUser()).thenReturn(mockUser1);
        var mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.getById(anyLong())).thenReturn(mockProject);


        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        projectServiceImpl.deleteProjectById(1L, mockUser1);

        // then
        verify(mockProjectRepository, times(1)).delete(mockProject);
    }

    @Test
    void toggleProjectShouldChangeToTrue() {

    }
}