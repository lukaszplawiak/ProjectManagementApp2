package com.lukaszplawiak.projectapp.service.implementation;

import com.lukaszplawiak.projectapp.dto.TaskReadDto;
import com.lukaszplawiak.projectapp.dto.TaskWriteDto;
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
    public TaskWriteDto createTask(Long projectId, TaskWriteDto taskWriteDto) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalArgumentException("Project of id: " + projectId + " not found. Create task is an impossible"));
        if (project.isDone()) {
            logger.info("Project of id: " + projectId + " is done. Create task is impossible");
            throw new IllegalArgumentException("Project of id: " + projectId + " is done. Create task is impossible"); // tutaj potrzebny wlasny wyjatek !!!
        }
        Task task = mapToTaskEntity(taskWriteDto);
        task.setProject(project);
        Task newTask = taskRepository.save(task);
        logger.info("Created task of id: " + newTask.getId());
        return mapToTaskWriteDto(newTask);
    }

    @Override
    public TaskReadDto getTaskById(Long projectId, Long taskId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalArgumentException("Project of id: " + projectId + " not found"));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task of id: " + taskId + " not found"));
        if (!Objects.equals(task.getProject().getId(), project.getId())) {
            throw new IllegalArgumentException("Task does not belong to project");
        }
        logger.info("Exposed task of id: " + taskId);
        return mapToTaskReadDto(task);
    }

    @Override
    public List<TaskReadDto> getTasksByProject_Id(Long projectId, Pageable pageable) {
        List<Task> tasks = taskRepository.findByProjectId(projectId);
        List<TaskReadDto> collect = tasks.stream().map(task -> mapToTaskReadDto(task)).collect(Collectors.toList());
        logger.info("Exposed all tasks of project of id: " + projectId);
        return collect;
    }

    @Override
    public List<TaskReadDto> getTasksByDone(boolean done, Pageable pageable) {
        List<Task> taskList = taskRepository.findByDone(done);
        List<TaskReadDto> taskReadDtoList = taskList.stream()
                .map(task -> mapToTaskReadDto(task))
                .collect(Collectors.toList());
        logger.info("Exposed all the tasks by 'done' state");
        return taskReadDtoList;
    }

    @Override
    public List<TaskReadDto> getTasksByDoneAndProject_Id(Long projectId, boolean done, Pageable pageable) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalArgumentException("Project of id: " + projectId + " not found"));
        List<Task> collect = project.getTasks().stream().filter(task -> task.isDone()).collect(Collectors.toList());
        List<TaskReadDto> taskReadDtoList = collect.stream().map(task -> mapToTaskReadDto(task)).collect(Collectors.toList());
        logger.info("Exposed all the tasks by 'done' state of project id: " + projectId);
        return taskReadDtoList;
    }

    @Override
    public TaskWriteDto updateTaskById(Long projectId, Long taskId, TaskWriteDto taskWriteDto) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalArgumentException("Project of id: " + projectId + " not found"));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task of id: " + taskId + " not found"));
        if (!Objects.equals(task.getProject().getId(), project.getId())) {
            throw new IllegalArgumentException("Task does not belong to project");
        }
        task.setId(taskId);
        task.setName(taskWriteDto.getName());
        task.setComment(taskWriteDto.getComment());
        task.setDeadline(taskWriteDto.getDeadline());
        //task.setDone(taskWriteDto.isDone()); jesli chce/nie chce aby mozna bylo aktializowac done poprzez ,metode PUT
        Task updatedTask = taskRepository.save(task);
        logger.info("Updated task of id: " + taskId);
        return mapToTaskWriteDto(updatedTask);
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

    public void toggleTask(Long projectId, Long taskId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalArgumentException("Project of id: " + projectId + " not found"));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task of id: " + taskId + " not found"));
        if (project.isDone()) {
            logger.info("Project of id: " + projectId + " is done. Toggle task is impossible");
            throw new IllegalArgumentException("Project of id: " + projectId + " is done. Toggle task is impossible"); // tutaj potrzebny wlasny wyjatek !!!
        }
        task.setDone(!task.isDone());
        taskRepository.save(task);
        toggleProject(projectId);
        logger.info("Toggled task of id: " + taskId);
    }

    private void toggleProject(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalArgumentException("Project of id: " + projectId + " not found"));
        if (project.getTasks().stream().allMatch(b -> b.isDone())) {
            project.setDone(!project.isDone());
            projectRepository.save(project);
        }
    }


    private TaskWriteDto mapToTaskWriteDto(Task task) {
        TaskWriteDto taskWriteDto = new TaskWriteDto();
        taskWriteDto.setName(task.getName());
        taskWriteDto.setComment(task.getComment());
        taskWriteDto.setDeadline(task.getDeadline());
        taskWriteDto.setDone(task.isDone());
        return taskWriteDto;
    }

    private TaskReadDto mapToTaskReadDto(Task task) {
        TaskReadDto taskReadDto = new TaskReadDto();
        taskReadDto.setId(task.getId());
        taskReadDto.setName(task.getName());
        taskReadDto.setComment(task.getComment());
        taskReadDto.setDeadline(task.getDeadline());
        taskReadDto.setDone(task.isDone());
        return taskReadDto;
    }

    private Task mapToTaskEntity(TaskWriteDto taskWriteDto) {
        Task task = new Task();
        task.setName(taskWriteDto.getName());
        task.setComment(taskWriteDto.getComment());
        task.setDeadline(taskWriteDto.getDeadline());
        task.setDone(taskWriteDto.isDone());
        return task;
    }
}
