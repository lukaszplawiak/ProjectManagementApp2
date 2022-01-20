package com.lukaszplawiak.projectapp.service.impl;

import com.lukaszplawiak.projectapp.dto.TaskResponseDto;
import com.lukaszplawiak.projectapp.dto.TaskRequestDto;
import com.lukaszplawiak.projectapp.exception.IllegalCreateTaskException;
import com.lukaszplawiak.projectapp.model.Project;
import com.lukaszplawiak.projectapp.model.Task;
import com.lukaszplawiak.projectapp.repository.ProjectRepository;
import com.lukaszplawiak.projectapp.repository.TaskRepository;
import com.lukaszplawiak.projectapp.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.lukaszplawiak.projectapp.service.impl.mapper.TaskEntityMapper.mapToTaskEntity;
import static com.lukaszplawiak.projectapp.service.impl.mapper.TaskResponseDtoMapper.mapToTaskResponseDto;
import static com.lukaszplawiak.projectapp.service.impl.mapper.TaskRequestDtoMapper.mapToTaskRequestDto;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final ProjectServiceImpl projectServiceImpl;
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    public TaskServiceImpl(final TaskRepository taskRepository, final ProjectRepository projectRepository, ProjectServiceImpl projectServiceImpl) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.projectServiceImpl = projectServiceImpl;
    }

    @Override
    public TaskRequestDto createTask(Long projectId, TaskRequestDto taskRequestDto) {
        Project project = projectRepository.getById(projectId);
        if (project.isDone()) {
            logger.info("Project of id: " + projectId + " is done. Create task is impossible");
            throw new IllegalCreateTaskException("Project of id: " + projectId + " is done. Create task for this project is impossible");
        }
        Task task = mapToTaskEntity(taskRequestDto);
        task.setProject(project);
        Task newTask = taskRepository.save(task);
        logger.info("Created task of id: " + newTask.getId());
        return mapToTaskRequestDto(newTask);
    }

    @Override
    public TaskResponseDto getTaskById(Long projectId, Long taskId) {
        Project project = projectRepository.getById(projectId);
        Task task = taskRepository.getById(taskId);
        if (!Objects.equals(task.getProject().getId(), project.getId())) {
            throw new IllegalArgumentException("Task does not belong to project");
        }
        logger.info("Fetch task of id: " + taskId);
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
        logger.info("Fetch all the tasks of project of id: " + projectId);
        return collect;
    }

    @Override
    public List<TaskResponseDto> getTasksByDoneIsFalseAndProjectId(Long projectId, boolean done, Pageable pageable) {
        List<Task> byDoneAndProjectId = taskRepository.findByDoneAndProjectId(done, projectId);
        List<TaskResponseDto> taskResponseDtoList = byDoneAndProjectId.stream()
                .map(task -> mapToTaskResponseDto(task))
                .collect(Collectors.toList());
        logger.info("Fetch all the tasks by 'done' state of project id: " + projectId);
        return taskResponseDtoList;
    }

    @Override
    public TaskRequestDto updateTaskById(Long projectId, Long taskId, TaskRequestDto taskRequestDto) {
        Project project = projectRepository.getById(projectId);
        Task task = taskRepository.getById(taskId);
        if (!Objects.equals(task.getProject().getId(), project.getId())) {
            throw new IllegalArgumentException("Task does not belong to project");
        }
        task.setId(taskId);
        task.setName(taskRequestDto.getName());
        task.setComment(taskRequestDto.getComment());
        task.setDeadline(taskRequestDto.getDeadline());
        logger.info("Updated task of id: " + taskId);
        return mapToTaskRequestDto(task);
    }

    @Override
    public void deleteTaskById(Long projectId, Long taskId) {
        Project project = projectRepository.getById(projectId);
        Task task = taskRepository.getById(taskId);
        if (!Objects.equals(task.getProject().getId(), project.getId())) {
            throw new IllegalArgumentException("Task does not belong to project");
        }
        taskRepository.delete(task);
        logger.info("Deleted task of id: " + taskId);
    }

    public void toggleTask(Long projectId, Long taskId) {
        Project project = projectRepository.getById(projectId);
        Task task = taskRepository.getById(taskId);
        if (project.isDone()) {
            logger.info("Project of id: " + projectId + " is done. Toggle task is impossible");
            throw new IllegalArgumentException("Project of id: " + projectId + " is done. Toggle task is impossible"); // tutaj potrzebny wlasny wyjatek !!!
        }
        task.setDone(!task.isDone());
        logger.info("Toggled task of id: " + taskId);
        taskRepository.save(task);
        projectServiceImpl.toggleProject(projectId);
    }


}
