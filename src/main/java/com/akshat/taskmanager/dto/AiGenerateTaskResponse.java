package com.akshat.taskmanager.dto;

import com.akshat.taskmanager.model.Task;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AiGenerateTaskResponse {

    private String message;

    private Integer totalTasks;

    private List<Task> tasks;

}