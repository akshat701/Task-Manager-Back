package com.akshat.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AiDashboardResponse {

    private DashboardStatsResponse stats;

    private String aiSuggestion;

}