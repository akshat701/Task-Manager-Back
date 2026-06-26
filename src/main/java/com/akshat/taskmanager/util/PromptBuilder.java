package com.akshat.taskmanager.util;

public class PromptBuilder {

    private PromptBuilder() {
    }

    public static String generateTasks(
            String projectName,
            String description,
            int estimatedDays
    ) {

        return """
                You are a Senior Software Project Manager.

                Your job is to break a software project into implementation tasks.

                Return ONLY valid JSON.

                Do not write markdown.
                Do not write explanation.
                Do not use ```json.

                Response format:

                [
                  {
                    "title":"",
                    "description":"",
                    "priority":"high",
                    "estimatedHours":8
                  }
                ]

                Rules:

                - priority must be low, medium or high
                - estimatedHours must be integer
                - create at least 8 tasks
                - every task title should be unique

                Project Name:
                %s

                Description:
                %s

                Estimated Duration:
                %d Days
                """.formatted(
                projectName,
                description,
                estimatedDays
        );
    }
}