package com.akshat.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectSummaryResponse {

    private String projectName;

    private long totalTasks;

    private long completedTasks;

    private long inProgressTasks;

    private long todoTasks;

    private long highPriorityTasks;

    private double completionPercentage;

    private String aiSummary;

}