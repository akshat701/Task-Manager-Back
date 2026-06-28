package com.akshat.taskmanager.service;

import com.akshat.taskmanager.dto.AiDashboardResponse;
import com.akshat.taskmanager.dto.DashboardStatsResponse;
import com.akshat.taskmanager.util.PromptBuilder;
import org.springframework.stereotype.Service;

@Service
public class AiDashboardService {

    private final DashboardService dashboardService;
    private final AiService aiService;

    public AiDashboardService(
            DashboardService dashboardService,
            AiService aiService
    ) {
        this.dashboardService = dashboardService;
        this.aiService = aiService;
    }

    public AiDashboardResponse dashboard(String email){

        DashboardStatsResponse stats =
                dashboardService.getStats(email);

        String prompt =
                PromptBuilder.dashboardPrompt(stats);

        String answer =
                aiService.ask(
                        email,
                        prompt
                );

        return new AiDashboardResponse(
                stats,
                answer
        );

    }

}