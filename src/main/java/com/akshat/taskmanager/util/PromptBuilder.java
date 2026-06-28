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

    public static String projectChatPrompt(

            String projectName,

            String description,

            String projectContext,

            String question

    ){

        return """

You are a Senior AI Project Manager.

Always answer only from provided project context.

If information is unavailable,
say

"I don't have enough information."

Keep answers short.

----------------------------------

Project

%s

Description

%s

----------------------------------

Context

%s

----------------------------------

Question

%s

"""
                .formatted(

                        projectName,

                        description,

                        projectContext,

                        question

                );

    }

    public static String projectSummaryPrompt(

            String projectName,

            String description,

            long total,

            long todo,

            long inProgress,

            long completed,

            long high,

            String tasks

    ){

        return """

You are a Senior Software Engineering Manager.

Analyze the following software project.

Return ONLY plain English.

Mention:

1. Overall project health

2. Current progress

3. Risks

4. What should be done next

5. Short recommendation

-------------------------------------

Project

%s

Description

%s

-------------------------------------

Statistics

Total Tasks : %d

Todo : %d

In Progress : %d

Completed : %d

High Priority Pending : %d

-------------------------------------

Task List

%s

"""
                .formatted(

                        projectName,

                        description,

                        total,

                        todo,

                        inProgress,

                        completed,

                        high,

                        tasks

                );

    }

    public static String taskActionPrompt(

            String projectName,

            String question

    ){

        return """

You are an AI Project Assistant.

The user may ask to

- complete task
- update task
- assign task
- delete task

If possible use available tools.

Project

%s

User Request

%s

"""
                .formatted(
                        projectName,
                        question
                );

    }

    public static String pdfPrompt(

            String pdf,

            String question

    ){

        return """

You are an AI Software Analyst.

Answer ONLY using PDF.

If answer is unavailable say

I couldn't find it inside document.

-------------------------

Document

%s

-------------------------

Question

%s

"""
                .formatted(
                        pdf,
                        question
                );

    }

    public static String dashboardPrompt(

            Object stats

    ){

        return """

You are an Expert Software Project Manager.

Analyze dashboard statistics.

Give only short recommendations.

Mention

1. Productivity

2. Pending Work

3. Risks

4. Today's Priority

Dashboard

%s

"""
                .formatted(stats);

    }

    public static String commandPrompt(

            String command

    ){

        return """

You are an AI Project Assistant.

Always use available tools.

Never guess.

If user asks

Create Task

Delete Task

Update Task

Assign Task

Use tool calling.

User Command

%s

"""
                .formatted(command);

    }
}