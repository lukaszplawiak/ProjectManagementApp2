package com.lukaszplawiak.projectapp.service.impl;

import com.lukaszplawiak.projectapp.dto.ProjectRequestDto;
import com.lukaszplawiak.projectapp.exception.IllegalAccessException;
import com.lukaszplawiak.projectapp.exception.IllegalActionException;
import com.lukaszplawiak.projectapp.exception.IllegalInputException;
import com.lukaszplawiak.projectapp.model.Project;
import com.lukaszplawiak.projectapp.model.User;
import com.lukaszplawiak.projectapp.repository.ProjectRepository;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProjectServiceImplTest {

    ProjectRepository mockProjectRepository = mock(ProjectRepository.class);

    LocalDateTime now = LocalDateTime.of(2022, 01, 01, 0, 0);
    Clock fixedClock = Clock.fixed(now.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
    Clock fixedClockPlusDay = Clock.fixed(now.plusDays(1).toInstant(ZoneOffset.UTC), ZoneOffset.UTC);

    Long id = 1L;

    @Test
    void createProjectShouldThrowIllegalInputExceptionWhenProjectsTitleIsNull() {
        // given
        var projectRequestDto = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle(null)
                .build();

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.createProject(projectRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createProjectShouldThrowIllegalInputExceptionWhenProjectsTitleIsEmpty() {
        // given
        var projectRequestDto = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("")
                .build();

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.createProject(projectRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createProjectShouldThrowIllegalInputExceptionWhenProjectsTitleIsBlank() {
        // given
        var projectRequestDto = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("   ")
                .build();

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.createProject(projectRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createProjectShouldThrowIllegalInputExceptionWhenProjectsDescriptionIsNull() {
        // given
        var projectRequestDto = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("Title")
                .withDescription(null)
                .build();

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.createProject(projectRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createProjectShouldThrowIllegalInputExceptionWhenProjectsDeadlineIsNull() {
        // given
        var projectRequestDto = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("Title")
                .withDescription("Description")
                .withDeadline(null)
                .build();

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.createProject(projectRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createProjectShouldThrowIllegalInputExceptionWhenProjectsDeadlineIsBeforeNow() {
        // given
        var projectRequestDto = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("Title")
                .withDescription("Description")
                .withDeadline(LocalDate.now(fixedClock))
                .build();

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, fixedClockPlusDay);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.createProject(projectRequestDto, null))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void createProjectShouldNotThrowAnyException() {
        // given
        var projectRequestDto = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("Title")
                .withDescription("Description")
                .withDeadline(LocalDate.now(fixedClockPlusDay))
                .build();

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, fixedClock);

        // when
        // then
        assertThat(projectServiceImpl.createProject(projectRequestDto, null))
                .isNotInstanceOfAny(
                        IllegalAccessException.class,
                        IllegalActionException.class,
                        IllegalInputException.class
                );
    }

    @Test
    void updateProjectShouldThrowIllegalAccessExceptionWhenUserTryToUpdateNotOwnProject() {
        // given
        var user1 = User.UserBuilder.anUser()
                .withId(1L)
                .build();
        var user2 = User.UserBuilder.anUser()
                .withId(2L)
                .build();
        var project = Project.ProjectBuilder.aProject()
                .withUser(user2)
                .build();
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.updateProject( null , anyLong() , user1))
                .isInstanceOf(IllegalAccessException.class)
                .hasMessageContaining("Access denied");
    }

    @Test
    void updateProjectShouldThrowIllegalActionExceptionWhenProjectIsDone() {
        // given
        var user = User.UserBuilder.anUser()
                .withId(1L)
                .build();
        var project = Project.ProjectBuilder.aProject()
                .withDone(true)
                .withUser(user)
                .build();
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.updateProject(null, anyLong(), user))
                .isInstanceOf(IllegalActionException.class)
                .hasMessageContaining("The action is impossible to execute");
    }

    @Test
    void updateProjectShouldThrowIllegalActionExceptionWhenProjectsTitleIsNull() {
        // given
        var user = User.UserBuilder.anUser()
                .withId(1L)
                .build();
        var project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .withUser(user)
                .build();
        ProjectRequestDto projectRequestDto = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle(null)
                .build();
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.updateProject(projectRequestDto, anyLong(), user))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateProjectShouldThrowIllegalActionExceptionWhenProjectsTitleIsEmpty() {
        // given
        var user = User.UserBuilder.anUser()
                .withId(1L)
                .build();
        var project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .withUser(user)
                .build();
        ProjectRequestDto projectRequestDto = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("")
                .build();
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.updateProject(projectRequestDto, anyLong(), user))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateProjectShouldThrowIllegalActionExceptionWhenProjectsTitleIsBlank() {
        // given
        var user = User.UserBuilder.anUser()
                .withId(1L)
                .build();
        var project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .withUser(user)
                .build();
        ProjectRequestDto projectRequestDto = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("   ")
                .build();
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.updateProject(projectRequestDto, anyLong(), user))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateProjectShouldThrowIllegalActionExceptionWhenProjectsDescriptionIsNull() {
        // given
        var user = User.UserBuilder.anUser()
                .withId(1L)
                .build();
        var project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .withUser(user)
                .build();
        ProjectRequestDto projectRequestDto = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("Title")
                .withDescription(null)
                .build();
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.updateProject(projectRequestDto, anyLong(), user))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateProjectShouldThrowIllegalActionExceptionWhenProjectsDeadlineIsNull() {
        // given
        var user = User.UserBuilder.anUser()
                .withId(1L)
                .build();
        var project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .withUser(user)
                .build();
        ProjectRequestDto projectRequestDto = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("Title")
                .withDescription("Description")
                .withDeadline(null)
                .build();
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.updateProject(projectRequestDto, anyLong(), user))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateProjectShouldThrowIllegalActionExceptionWhenProjectsDeadlineIsBeforeNow() {
        // given
        var user = User.UserBuilder.anUser()
                .withId(1L)
                .build();
        var project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .withUser(user)
                .build();
        ProjectRequestDto projectRequestDto = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("Title")
                .withDescription("Description")
                .withDeadline(LocalDate.now(fixedClock))
                .build();
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, fixedClockPlusDay);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.updateProject(projectRequestDto, anyLong(), user))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateProjectShouldNotThrowAnyException() {
        // given
        var user = User.UserBuilder.anUser()
                .withId(1L)
                .build();
        var project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .withUser(user)
                .build();
        ProjectRequestDto projectRequestDto = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("Title")
                .withDescription("Description")
                .withDeadline(LocalDate.now(fixedClockPlusDay))
                .build();
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, fixedClock);

        // when
        // then
        assertThat(projectServiceImpl.updateProject(projectRequestDto, anyLong(), user))
                .isNotInstanceOfAny(
                        IllegalAccessException.class,
                        IllegalActionException.class,
                        IllegalInputException.class
                );
    }

    @Test
    void deleteProjectByIdShouldThrowIllegalAccessExceptionWhenUserTryToDeleteNotOwnProject() {
        // given
        var user1 = User.UserBuilder.anUser()
                .withId(1L)
                .build();
        var user2 = User.UserBuilder.anUser()
                .withId(2L)
                .build();
        var project = Project.ProjectBuilder.aProject()
                .withUser(user2)
                .build();
        when(mockProjectRepository.getById(anyLong())).thenReturn(project);

        var projectServiceImpl = new ProjectServiceImpl(mockProjectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.deleteProjectById(anyLong(), user1))
                .isInstanceOf(IllegalAccessException.class)
                .hasMessageContaining("Access denied");
    }

    @Test
    void deleteProjectByIdShouldBeSuccessful() {
        // given
        var user = User.UserBuilder.anUser()
                .withId(id)
                .build();
        var mockProjectServiceImpl = mock(ProjectServiceImpl.class);

        // when
        mockProjectServiceImpl.deleteProjectById(id ,user);

        // then
        verify(mockProjectServiceImpl, times(1)).deleteProjectById(id, user);
    }

    @Test
    void toggleProjectShouldBeSuccessful() {
        // given
        var mockProjectServiceImpl = mock(ProjectServiceImpl.class);

        // when
        mockProjectServiceImpl.toggleProject(1L);

        // then
        verify(mockProjectServiceImpl, times(1)).toggleProject(1L);
    }
}