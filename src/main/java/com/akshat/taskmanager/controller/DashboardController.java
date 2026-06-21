package com.akshat.taskmanager.controller;
import com.akshat.taskmanager.model.User;
import com.akshat.taskmanager.repository.UserRepository;
import com.akshat.taskmanager.repository.TaskRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public DashboardController(
            TaskRepository taskRepository,
            UserRepository userRepository
    ) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/stats")
    public Map<String, Object> stats(
            Authentication authentication
    ) {

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        String userId = user.getId();

        long totalTasks =
                taskRepository.countByAssignedTo(userId);

        long completedTasks =
                taskRepository.countByAssignedToAndStatus(
                        userId,
                        "done"
                );

        long pendingTasks =
                taskRepository.countByAssignedToAndStatusNot(
                        userId,
                        "done"
                );

        Map<String, Object> response =
                new HashMap<>();

        response.put("totalTasks", totalTasks);
        response.put("completedTasks", completedTasks);
        response.put("pendingTasks", pendingTasks);
        response.put("overdueTasks", 0);

        return response;
    }
}