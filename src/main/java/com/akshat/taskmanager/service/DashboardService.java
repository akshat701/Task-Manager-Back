package com.akshat.taskmanager.service;

import com.akshat.taskmanager.dto.DashboardStatsResponse;
import com.akshat.taskmanager.model.User;
import com.akshat.taskmanager.repository.TaskRepository;
import com.akshat.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public DashboardService(
            TaskRepository taskRepository,
            UserRepository userRepository
    ) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public DashboardStatsResponse getStats(String email){

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

        return new DashboardStatsResponse(
                totalTasks,
                completedTasks,
                pendingTasks,
                0
        );

    }

}