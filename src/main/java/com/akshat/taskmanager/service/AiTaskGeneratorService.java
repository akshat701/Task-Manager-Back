package com.akshat.taskmanager.service;

import com.akshat.taskmanager.dto.AiGenerateTaskResponse;
import com.akshat.taskmanager.dto.GeneratedTask;
import com.akshat.taskmanager.util.PromptBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import com.akshat.taskmanager.dto.GenerateTaskRequest;
import com.akshat.taskmanager.model.Task;
import java.util.Collections;
import java.util.List;

@Service
public class AiTaskGeneratorService {

    private final AiService aiService;

    private final ObjectMapper objectMapper;

    private final TaskService taskService;

    public AiTaskGeneratorService(
            AiService aiService,
            ObjectMapper objectMapper,
            TaskService taskService
    ) {

        this.aiService = aiService;
        this.objectMapper = objectMapper;
        this.taskService = taskService;

    }

    public AiGenerateTaskResponse generateAndSaveTasks(
            GenerateTaskRequest request
    ) {

        if(request.getProjectId()==null
                || request.getProjectId().isBlank()){

            throw new RuntimeException(
                    "Project Id required"
            );
        }

        if(request.getProjectName()==null
                || request.getProjectName().isBlank()){

            throw new RuntimeException(
                    "Project Name required"
            );
        }

        String prompt = PromptBuilder.generateTasks(
                request.getProjectName(),
                request.getDescription(),
                request.getEstimatedDays()
        );

        String response = aiService.ask(
                "task-generator",
                prompt
        );

        response = response
                .replace("```json", "")
                .replace("```", "")
                .trim();

        try {

            List<GeneratedTask> generatedTasks =
                    objectMapper.readValue(
                            response,
                            new TypeReference<List<GeneratedTask>>() {}
                    );

            List<Task> savedTasks =
                    generatedTasks
                            .stream()
                            .map(task ->
                                    taskService.createFromAi(
                                            request.getProjectId(),
                                            task
                                    )
                            )
                            .filter(task -> task != null)
                            .toList();

            return new AiGenerateTaskResponse(
                    "Tasks generated successfully",
                    savedTasks.size(),
                    savedTasks
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to parse AI response",
                    e
            );

        }

    }

}