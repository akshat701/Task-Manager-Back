package com.akshat.taskmanager.controller;

import com.akshat.taskmanager.dto.CreateTaskRequest;
import com.akshat.taskmanager.dto.TaskResponse;
import com.akshat.taskmanager.model.Task;
import com.akshat.taskmanager.service.TaskService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public Task createTask(
            @RequestBody CreateTaskRequest request
    ) {
        return taskService.create(request);
    }

    @GetMapping
    public List<TaskResponse> getTasks(
            Authentication authentication
    ) {
        return taskService.getAll(authentication);
    }

    @PatchMapping("/{id}")
    public Task updateTask(
            @PathVariable String id,
            @RequestBody CreateTaskRequest request
    ) {
        return taskService.updateTask(id, request);
    }

    @GetMapping("/my")
    public List<TaskResponse> getMyTasks(
            Authentication authentication
    ) {
        return taskService.getMyTasks(authentication);
    }
}