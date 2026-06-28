package com.akshat.taskmanager.service.tools;

import com.akshat.taskmanager.dto.CreateProjectRequest;
import com.akshat.taskmanager.dto.CreateTaskRequest;
import com.akshat.taskmanager.model.Project;
import com.akshat.taskmanager.model.Task;
import com.akshat.taskmanager.service.ProjectService;
import com.akshat.taskmanager.service.TaskService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class ProjectTool {

    private final ProjectService projectService;
    private final TaskService taskService;

    public ProjectTool(
            ProjectService projectService,
            TaskService taskService
    ) {
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @Tool("Create new project")
    public String createProject(

            @P("Project name")
            String name,

            @P("Project description")
            String description

    ) {

        CreateProjectRequest request =
                new CreateProjectRequest();

        request.setName(name);
        request.setDescription(description);

        Project project =
                projectService.createProject(

                        request,

                        new UsernamePasswordAuthenticationToken(
                                "akshat@gmail.com",
                                null
                        )

                );

        return project.getId();

    }

    @Tool(name = "createProjectTask", value = "Create task inside project")
    public String createProjectTask(

            @P("Project Id")
            String projectId,

            @P("Task title")
            String title,

            @P("Priority")
            String priority

    ) {

        CreateTaskRequest request = new CreateTaskRequest();

        request.setProject_id(projectId);
        request.setTitle(title);
        request.setPriority(priority);
        request.setStatus("todo");

        Task task = taskService.create(request);

        return task.getId();
    }

}