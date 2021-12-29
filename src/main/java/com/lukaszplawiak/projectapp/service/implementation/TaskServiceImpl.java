package com.lukaszplawiak.projectapp.service.implementation;

import com.lukaszplawiak.projectapp.dto.TaskDto;
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

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    public static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    public TaskServiceImpl(final TaskRepository taskRepository, final ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public TaskDto createTask(Long projectId, TaskDto taskDto) {
        Task task = mapToTaskEntity(taskDto);
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalArgumentException("Project of id: " + projectId + " not found. Create task is an impossible"));
        task.setProject(project);
        Task newTask = taskRepository.save(task);
        logger.info("Created task of id: " + newTask.getId());
        return mapToTaskDto(newTask);
    }

    @Override
    public TaskDto getTaskById(Long projectId, Long taskId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalArgumentException("Project of id: " + projectId + " not found"));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task of id: " + taskId + " not found"));
        if (!Objects.equals(task.getProject().getId(), project.getId())) {
            throw new IllegalArgumentException("Task does not belong to project");
        }
        logger.info("Exposed task of id: " + taskId);
        return mapToTaskDto(task);
    }

    @Override
    public List<TaskDto> getTasksByProject_Id(Long projectId, Pageable pageable) {
        List<Task> tasks = taskRepository.findByProjectId(projectId);
        List<TaskDto> collect = tasks.stream().map(task -> mapToTaskDto(task)).collect(Collectors.toList());
        logger.info("Exposed all tasks of project of id: " + projectId);
        return collect;
    }

    @Override
    public List<TaskDto> getTasksByDone(boolean done, Pageable pageable) {
        List<Task> taskList = taskRepository.findByDone(done);
        List<TaskDto> taskDtoList = taskList.stream()
                .map(task -> mapToTaskDto(task))
                .collect(Collectors.toList());
        logger.info("Exposed all the tasks by 'done' state");
        return taskDtoList;
    }

    @Override
    public List<TaskDto> getTasksByDoneAndProject_Id(Long projectId, boolean done, Pageable pageable) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalArgumentException("Project of id: " + projectId + " not found"));
        List<Task> collect = project.getTasks().stream().filter(task -> task.isDone()).collect(Collectors.toList());
        List<TaskDto> taskDtoList = collect.stream().map(task -> mapToTaskDto(task)).collect(Collectors.toList());
        logger.info("Exposed all the tasks by 'done' state of project id: " + projectId);
        return taskDtoList;
    }

    @Override
    public TaskDto updateTaskById(Long projectId, Long taskId, TaskDto taskDto) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalArgumentException("Project of id: " + projectId + " not found"));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task of id: " + taskId + " not found"));
        if (!Objects.equals(task.getProject().getId(), project.getId())) {
            throw new IllegalArgumentException("Task does not belong to project");
        }
        task.setId(taskDto.getId());
        task.setName(task.getName());
        task.setComment(taskDto.getComment());
        task.setDeadline(taskDto.getDeadline());
        task.setDone(taskDto.isDone());
        Task updatedTask = taskRepository.save(task);
        return mapToTaskDto(updatedTask);
    }

    @Override
    public void deleteTaskById(Long projectId, Long taskId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalArgumentException("Project of id: " + projectId + " not found"));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task of id: " + taskId + " not found"));
        if (!Objects.equals(task.getProject().getId(), project.getId())) {
            throw new IllegalArgumentException("Task does not belong to project");
        }
        logger.info("Deleted task of id: " + taskId);
        taskRepository.delete(task);
    }

    private TaskDto mapToTaskDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setName(task.getName());
        taskDto.setComment(task.getComment());
        taskDto.setDeadline(task.getDeadline());
        taskDto.setDone(task.isDone());

        return taskDto;
    }

    private Task mapToTaskEntity(TaskDto taskDto) {
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setName(taskDto.getName());
        task.setComment(taskDto.getComment());
        task.setDeadline(taskDto.getDeadline());
        task.setDone(taskDto.isDone());

        return task;
    }
}
