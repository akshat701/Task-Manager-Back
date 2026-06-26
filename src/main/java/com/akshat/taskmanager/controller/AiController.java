package com.akshat.taskmanager.controller;

import com.akshat.taskmanager.dto.AiGenerateTaskResponse;
import com.akshat.taskmanager.dto.GenerateTaskRequest;
import com.akshat.taskmanager.model.Task;
import com.akshat.taskmanager.service.AiTaskGeneratorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiTaskGeneratorService aiTaskGeneratorService;

    public AiController(
            AiTaskGeneratorService aiTaskGeneratorService
    ) {
        this.aiTaskGeneratorService =
                aiTaskGeneratorService;
    }

    @PostMapping("/generate-tasks")
    public AiGenerateTaskResponse generateTasks(
            @RequestBody GenerateTaskRequest request
    ) {

        return aiTaskGeneratorService.generateAndSaveTasks(
                request
        );

    }

}