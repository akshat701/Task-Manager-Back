package com.akshat.taskmanager.service;

import com.akshat.taskmanager.dto.ChatRequest;
import com.akshat.taskmanager.dto.ChatResponse;
import com.akshat.taskmanager.model.Project;
import com.akshat.taskmanager.model.Task;
import com.akshat.taskmanager.repository.ProjectRepository;
import com.akshat.taskmanager.repository.TaskRepository;
import com.akshat.taskmanager.util.PromptBuilder;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.message.AiMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiChatService {

    private final AiService aiService;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final ChatMemory chatMemory;

    public AiChatService(
            AiService aiService,
            ProjectRepository projectRepository,
            TaskRepository taskRepository,
            ChatMemory chatMemory
    ) {
        this.aiService = aiService;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.chatMemory = chatMemory;
    }

    public ChatResponse chat(
            ChatRequest request
    ) {

        Project project = projectRepository
                .findById(request.getProjectId())
                .orElseThrow(() ->
                        new RuntimeException("Project not found"));

        List<Task> tasks =
                taskRepository.findByProjectId(
                        request.getProjectId()
                );

        StringBuilder context =
                new StringBuilder();

        for (Task task : tasks) {

            context.append("Title : ")
                    .append(task.getTitle())
                    .append("\n");

            context.append("Status : ")
                    .append(task.getStatus())
                    .append("\n");

            context.append("Priority : ")
                    .append(task.getPriority())
                    .append("\n");

            context.append("------------------\n");
        }

        String stats =
                buildProjectStats(
                        request.getProjectId()
                );

        String prompt =
                PromptBuilder.projectChatPrompt(

                        project.getName(),

                        project.getDescription(),

                        stats
                                + "\n\n"
                                + context,

                        request.getQuestion()

                );

        chatMemory.add(
                UserMessage.from(prompt)
        );

        String userId = request.getUserId();

        if (userId == null || userId.isBlank()) {
            userId = "default-user";
        }

        String answer = aiService.ask(
                userId,
                prompt
        );

        chatMemory.add(
                AiMessage.from(answer)
        );

        return new ChatResponse(answer);

    }

    private String buildProjectStats(
            String projectId
    ){

        long total =
                taskRepository.countByProjectId(projectId);

        long todo =
                taskRepository.countByProjectIdAndStatus(
                        projectId,
                        "todo"
                );

        long inProgress =
                taskRepository.countByProjectIdAndStatus(
                        projectId,
                        "in-progress"
                );

        long done =
                taskRepository.countByProjectIdAndStatus(
                        projectId,
                        "done"
                );

        long high =
                taskRepository.countByProjectIdAndPriority(
                        projectId,
                        "high"
                );

        return """

            Project Statistics

            Total Tasks : %d

            Todo : %d

            In Progress : %d

            Completed : %d

            High Priority : %d

            """
                .formatted(
                        total,
                        todo,
                        inProgress,
                        done,
                        high
                );

    }

}