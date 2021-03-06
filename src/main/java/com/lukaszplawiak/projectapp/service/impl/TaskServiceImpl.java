package com.lukaszplawiak.projectapp.service.impl;


import com.lukaszplawiak.projectapp.dto.TaskRequestDto;
import com.lukaszplawiak.projectapp.dto.TaskResponseDto;
import com.lukaszplawiak.projectapp.exception.IllegalAccessException;
import com.lukaszplawiak.projectapp.exception.IllegalInputException;
import com.lukaszplawiak.projectapp.exception.IllegalModificationException;
import com.lukaszplawiak.projectapp.model.Project;
import com.lukaszplawiak.projectapp.model.Task;
import com.lukaszplawiak.projectapp.model.User;
import com.lukaszplawiak.projectapp.repository.ProjectRepository;
import com.lukaszplawiak.projectapp.repository.TaskRepository;
import com.lukaszplawiak.projectapp.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.lukaszplawiak.projectapp.service.impl.mapper.TaskEntityMapper.mapToTaskEntity;
import static com.lukaszplawiak.projectapp.service.impl.mapper.TaskResponseDtoMapper.mapToTaskResponseDto;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final ProjectServiceImpl projectServiceImpl;
    private final Clock clock;
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    public TaskServiceImpl(final TaskRepository taskRepository, final ProjectRepository projectRepository, final ProjectServiceImpl projectServiceImpl, final Clock clock) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.projectServiceImpl = projectServiceImpl;
        this.clock = clock;
    }

    @Override
    public TaskResponseDto createTask(Long projectId, TaskRequestDto taskRequestDto, User user) {
        Project project = projectRepository.getById(projectId);
        projectIsDoneCheck(projectId, project);
        taskDeadlineValidation(taskRequestDto);
        Task task = mapToTaskEntity(taskRequestDto);
        task.setProject(project);
        task.setUser(user);
        Task newTask = taskRepository.save(task);
        logger.info("Created task of id: " + newTask.getId());
        return mapToTaskResponseDto(newTask);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public TaskResponseDto getTaskById(Long projectId, Long taskId) {
        Task task = taskRepository.getById(taskId);
        return mapToTaskResponseDto(task);
    }

    @Override
    public List<Task> getTasksByProjectId(Long id) {
        return taskRepository.findByProjectId(id);
    }

    @Override
    public List<TaskResponseDto> getTasksDtoByProjectId(Long projectId, Pageable pageable) {
        List<Task> tasks = taskRepository.findByProjectId(projectId);
        List<TaskResponseDto> collect = tasks.stream()
                .map(task -> mapToTaskResponseDto(task))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<TaskResponseDto> getTasksByDoneIsFalseAndProjectId(Long projectId, boolean done, Pageable pageable) {
        List<Task> byDoneAndProjectId = taskRepository.findByDoneAndProjectId(done, projectId);
        List<TaskResponseDto> taskResponseDtoList = byDoneAndProjectId.stream()
                .map(task -> mapToTaskResponseDto(task))
                .collect(Collectors.toList());
        return taskResponseDtoList;
    }

    @Override
    public TaskResponseDto updateTaskById(Long projectId, Long taskId, TaskRequestDto taskRequestDto, User user) {
        Project project = projectRepository.getById(projectId);
        Task task = taskRepository.getById(taskId);
        userAccessCheck(user, task);
        projectIsDoneCheck(projectId, project);
        taskDeadlineValidation(taskRequestDto);
        task.setId(taskId);
        task.setName(taskRequestDto.getName());
        task.setComment(taskRequestDto.getComment());
        task.setDeadline(taskRequestDto.getDeadline());
        task.setUser(user);
        logger.trace("Updated task of id: " + taskId);
        return mapToTaskResponseDto(task);
    }

    @Override
    public void deleteTaskById(Long projectId, Long taskId, User user) {
        Project project = projectRepository.getById(projectId);
        Task task = taskRepository.getById(taskId);
        userAccessCheck(user, task);
        projectIsDoneCheck(projectId, project);
        taskRepository.delete(task);
        logger.trace("Deleted task of id: " + taskId);
    }

    public void toggleTask(Long projectId, Long taskId, User user) {
        Project project = projectRepository.getById(projectId);
        Task task = taskRepository.getById(taskId);
        userAccessCheck(user, task);
        projectIsDoneCheck(projectId, project);
        task.setDone(!task.isDone());
        logger.trace("Toggled task of id: " + taskId);
        projectServiceImpl.toggleProject(projectId);
    }

    private void projectIsDoneCheck(Long projectId, Project project) {
        if (project.isDone()) {
            logger.trace("Project of id: " + projectId + " is done. The action is impossible to execute");
            throw new IllegalModificationException(projectId);
        }
    }

    private void userAccessCheck(User user, Task task) {
        if (!(Objects.equals(task.getUser().getId(), user.getId()))) {
            logger.trace("Access denied");
            throw new IllegalAccessException();
        }
    }

    private void taskDeadlineValidation(TaskRequestDto taskRequestDto) {
        if (taskRequestDto.getDeadline() == null || taskRequestDto.getDeadline().isBefore(ChronoLocalDate.from(LocalDateTime.now(clock)))) {
            logger.trace("Task's deadline must be set after now");
            throw new IllegalInputException();
        }
    }
}