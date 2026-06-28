package com.akshat.taskmanager.controller;
import com.akshat.taskmanager.dto.AiDashboardResponse;
import com.akshat.taskmanager.dto.DashboardStatsResponse;
import com.akshat.taskmanager.model.User;
import com.akshat.taskmanager.repository.UserRepository;
import com.akshat.taskmanager.repository.TaskRepository;
import com.akshat.taskmanager.service.AiDashboardService;
import com.akshat.taskmanager.service.DashboardService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    private final AiDashboardService aiDashboardService;

    public DashboardController(
            DashboardService dashboardService,
            AiDashboardService aiDashboardService
    ) {
        this.dashboardService = dashboardService;
        this.aiDashboardService = aiDashboardService;
    }

    @GetMapping("/stats")
    public DashboardStatsResponse stats(
            Authentication authentication
    ){

        return dashboardService.getStats(
                authentication.getName()
        );

    }

    @GetMapping("/ai")
    public AiDashboardResponse aiDashboard(

            Authentication authentication

    ){

        return aiDashboardService.dashboard(

                authentication.getName()

        );

    }
}