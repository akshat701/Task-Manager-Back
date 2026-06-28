package com.akshat.taskmanager.service;

import com.akshat.taskmanager.dto.ProjectSummaryResponse;
import com.akshat.taskmanager.model.Project;
import com.akshat.taskmanager.model.Task;
import com.akshat.taskmanager.repository.ProjectRepository;
import com.akshat.taskmanager.repository.TaskRepository;
import com.akshat.taskmanager.util.PromptBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiSummaryService {

    private final AiService aiService;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public AiSummaryService(
            AiService aiService,
            ProjectRepository projectRepository,
            TaskRepository taskRepository
    ) {

        this.aiService = aiService;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;

    }

    public ProjectSummaryResponse generateSummary(String projectId) {

        Project project = projectRepository
                .findById(projectId)
                .orElseThrow(() ->
                        new RuntimeException("Project not found"));

        List<Task> tasks =
                taskRepository.findByProjectId(projectId);

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

        long completed =
                taskRepository.countByProjectIdAndStatus(
                        projectId,
                        "done"
                );

        long high =
                taskRepository.countByProjectIdAndPriority(
                        projectId,
                        "high"
                );

        double completion = 0;

        if (total != 0) {
            completion = ((double) completed / total) * 100;
        }

        StringBuilder builder =
                new StringBuilder();

        for (Task task : tasks) {

            builder.append("Task : ")
                    .append(task.getTitle())
                    .append("\n");

            builder.append("Status : ")
                    .append(task.getStatus())
                    .append("\n");

            builder.append("Priority : ")
                    .append(task.getPriority())
                    .append("\n");

            builder.append("-------------------------\n");
        }

        String prompt =
                PromptBuilder.projectSummaryPrompt(

                        project.getName(),

                        project.getDescription(),

                        total,

                        todo,

                        inProgress,

                        completed,

                        high,

                        builder.toString()

                );

        String summary =
                aiService.ask(
                        "summary-agent",
                        prompt
                );

        return new ProjectSummaryResponse(

                project.getName(),

                total,

                completed,

                inProgress,

                todo,

                high,

                Math.round(completion * 100.0) / 100.0,

                summary

        );

    }

}