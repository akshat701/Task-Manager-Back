package com.akshat.taskmanager.service;

import com.akshat.taskmanager.dto.*;
import com.akshat.taskmanager.model.Project;
import com.akshat.taskmanager.model.Task;
import com.akshat.taskmanager.model.User;
import com.akshat.taskmanager.repository.ProjectRepository;
import com.akshat.taskmanager.repository.TaskRepository;
import com.akshat.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import java.util.Date;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public TaskService(
            TaskRepository taskRepository,
            UserRepository userRepository,
            ProjectRepository projectRepository
    ) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public Task create(CreateTaskRequest request) {

        Task task = new Task();

        task.setTitle(request.getTitle());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        task.setAssignedTo(request.getAssignee_id());
        task.setProjectId(request.getProject_id());
        task.setDueDate(request.getDue_date());

        return taskRepository.save(task);
    }

    public List<TaskResponse> getAll(
            Authentication authentication
    ) {

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<Task> tasks;

        if ("admin".equalsIgnoreCase(user.getRole())) {

            tasks = taskRepository.findAll();

        } else {

            tasks = taskRepository.findByAssignedTo(
                    user.getId()
            );
        }

        return tasks.stream()
                .map(this::mapTask)
                .toList();
    }

    public TaskResponse mapTask(Task task) {

        User user = null;

        if (task.getAssignedTo() != null) {
            user = userRepository
                    .findById(task.getAssignedTo())
                    .orElse(null);
        }

        Project project = null;

        if (task.getProjectId() != null) {
            project = projectRepository
                    .findById(task.getProjectId())
                    .orElse(null);
        }

        return new TaskResponse(
                task.getId(),                 // _id
                task.getId(),                 // id

                task.getTitle(),
                task.getStatus(),
                task.getPriority(),

                user == null ? null :
                        new UserSummary(
                                user.getId(),
                                user.getName(),
                                user.getEmail()
                        ),

                task.getAssignedTo(),         // assignee_id

                project == null ? null :
                        new ProjectSummary(
                                project.getId(),
                                project.getName()
                        ),

                task.getProjectId(),          // project_id

                task.getDueDate(),            // dueDate
                task.getDueDate(),            // due_date

                task.getCreatedAt(),
                task.getUpdatedAt(),

                task.get__v()
        );
    }

    public Task updateTask(
            String id,
            CreateTaskRequest request
    ) {

        Task task = taskRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Task not found"));

        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }

        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }

        if (request.getPriority() != null) {
            task.setPriority(request.getPriority());
        }

        // null bhi allow hai
        task.setAssignedTo(request.getAssignee_id());

        if (request.getProject_id() != null) {
            task.setProjectId(request.getProject_id());
        }

        if (request.getDue_date() != null) {
            task.setDueDate(request.getDue_date());
        }

        task.setUpdatedAt(new Date());

        if (task.get__v() == null) {
            task.set__v(0);
        }

        task.set__v(task.get__v() + 1);

        return taskRepository.save(task);
    }

    public List<TaskResponse> getMyTasks(
            Authentication authentication
    ) {

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return taskRepository.findAll()
                .stream()
                .filter(task ->
                        user.getId().equals(task.getAssignedTo()))
                .map(this::mapTask)
                .toList();
    }

    public Task createFromAi(
            String projectId,
            GeneratedTask generatedTask
    ) {

        if(taskRepository.existsByProjectIdAndTitle(
                projectId,
                generatedTask.getTitle()
        )){

            return null;

        }

        Task task = new Task();

        task.setTitle(
                generatedTask.getTitle()
        );

        task.setPriority(
                generatedTask.getPriority()
        );

        task.setStatus("todo");

        task.setProjectId(projectId);

        task.setAssignedTo(null);

        task.setDueDate(null);

        task.setCreatedAt(new Date());

        task.setUpdatedAt(new Date());

        task.set__v(0);

        return taskRepository.save(task);

    }
}