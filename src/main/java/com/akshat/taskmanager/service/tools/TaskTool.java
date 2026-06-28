package com.akshat.taskmanager.service.tools;

import com.akshat.taskmanager.dto.GeneratedTask;
import com.akshat.taskmanager.model.Task;
import com.akshat.taskmanager.service.TaskService;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskTool {

    private final TaskService taskService;

    public TaskTool(TaskService taskService) {
        this.taskService = taskService;
    }

    @Tool(name = "createAiTask", value = "Create new task inside project")
    public Task createTask(

            String projectId,

            String title,

            String priority

    ) {

        GeneratedTask task =
                new GeneratedTask();

        task.setTitle(title);

        task.setPriority(priority);

        task.setDescription("");

        task.setEstimatedHours(0);

        return taskService.createFromAi(
                projectId,
                task
        );

    }

    @Tool("Mark task as completed")
    public String completeTask(

            String projectId,

            String taskTitle

    ) {

        Task task = taskService
                .findTask(projectId, taskTitle);

        if(task == null){
            return "Task not found";
        }

        task.setStatus("done");

        taskService.save(task);

        return task.getTitle() + " completed successfully.";

    }

    @Tool("Delete task from project")
    public String deleteTask(

            String projectId,

            String title

    ){

        taskService.deleteTask(

                projectId,

                title

        );

        return "Task deleted successfully.";

    }

    @Tool("Get all pending tasks")
    public List<Task> getPendingTasks(

            String projectId

    ){

        return taskService.getPendingTasks(

                projectId

        );

    }

    @Tool("Get all high priority tasks")
    public List<Task> getHighPriorityTasks(

            String projectId

    ){

        return taskService.getHighPriorityTasks(

                projectId

        );

    }

}