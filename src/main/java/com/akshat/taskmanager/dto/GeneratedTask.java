package com.akshat.taskmanager.dto;
import lombok.Data;

@Data
public class GeneratedTask {

    private String title;

    private String description;

    private String priority;

    private int estimatedHours;
}