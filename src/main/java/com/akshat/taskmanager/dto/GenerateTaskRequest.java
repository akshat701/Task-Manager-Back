package com.akshat.taskmanager.dto;

import lombok.Data;

@Data
public class GenerateTaskRequest {

    private String projectId;

    private String projectName;

    private String description;

    private Integer estimatedDays;

}