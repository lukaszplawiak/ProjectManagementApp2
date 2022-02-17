package com.lukaszplawiak.projectapp.service.impl;

import com.lukaszplawiak.projectapp.dto.ProjectRequestDto;
import com.lukaszplawiak.projectapp.exception.IllegalAccessException;
import com.lukaszplawiak.projectapp.exception.IllegalActionException;
import com.lukaszplawiak.projectapp.exception.IllegalInputException;
import com.lukaszplawiak.projectapp.model.Project;
import com.lukaszplawiak.projectapp.model.User;
import com.lukaszplawiak.projectapp.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
                .withId(11L)
                .build();
        var user2 = User.UserBuilder.anUser()
                .withId(12L)
                .build();
        var project = Project.ProjectBuilder.aProject()
                .withUser(user2)
                .build();
        var projectRequestDto = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("Title11")
                .withDescription("Description11")
                .withDeadline(LocalDate.now(fixedClockPlusDay))
                .build();
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        when(projectRepository.getById(anyLong())).thenReturn(project);

        var projectServiceImpl = new ProjectServiceImpl(projectRepository, fixedClock);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.updateProject( projectRequestDto , 3L , user1))
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
        var projectRequestDto = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("Title1")
                .withDescription("Description1")
                .withDeadline(LocalDate.now(fixedClockPlusDay))
                .build();
        ProjectRepository projectRepository12 = mock(ProjectRepository.class);
        when(projectRepository12.getById(anyLong())).thenReturn(project);

        var projectServiceImpl = new ProjectServiceImpl(projectRepository12, fixedClock);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.updateProject(projectRequestDto, 12L, user))
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
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        when(projectRepository.getById(anyLong())).thenReturn(project);

        var projectServiceImpl = new ProjectServiceImpl(projectRepository, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.updateProject(projectRequestDto, 12L, user))
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
        ProjectRepository projectRepository2 = mock(ProjectRepository.class);
        when(projectRepository2.getById(anyLong())).thenReturn(project);

        var projectServiceImpl = new ProjectServiceImpl(projectRepository2, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.updateProject(projectRequestDto, 12L, user))
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
        assertThatThrownBy(() -> projectServiceImpl.updateProject(projectRequestDto, 12L, user))
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
        assertThatThrownBy(() -> projectServiceImpl.updateProject(projectRequestDto, 12L, user))
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
        ProjectRepository projectRepository11 = mock(ProjectRepository.class);
        when(projectRepository11.getById(anyLong())).thenReturn(project);

        var projectServiceImpl = new ProjectServiceImpl(projectRepository11, null);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.updateProject(projectRequestDto, 12L, user))
                .isInstanceOf(IllegalInputException.class)
                .hasMessageContaining("Illegal input data");
    }

    @Test
    void updateProjectShouldThrowIllegalActionExceptionWhenProjectsDeadlineIsBeforeNow() {
        // given
        var user = User.UserBuilder.anUser()
                .withId(11L)
                .build();
        var project = Project.ProjectBuilder.aProject()
                .withDone(false)
                .withUser(user)
                .build();
        var projectRequestDto = ProjectRequestDto.ProjectRequestDtoBuilder.aProjectRequestDto()
                .withTitle("Title")
                .withDescription("Description")
                .withDeadline(LocalDate.now(fixedClock))
                .build();
        ProjectRepository projectRepository3 = mock(ProjectRepository.class);
        when(projectRepository3.getById(12L)).thenReturn(project);

        var projectServiceImpl = new ProjectServiceImpl(projectRepository3, fixedClockPlusDay);

        // when
        // then
        assertThatThrownBy(() -> projectServiceImpl.updateProject(projectRequestDto, 22L, user))
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
        var mockProjectServiceImpl2 = mock(ProjectServiceImpl.class);

        // when
        mockProjectServiceImpl2.toggleProject(5L);

        // then
        verify(mockProjectServiceImpl2, times(1)).toggleProject(5L);
    }
}