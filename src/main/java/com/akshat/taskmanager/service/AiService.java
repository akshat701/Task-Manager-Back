package com.akshat.taskmanager.service;

import com.akshat.taskmanager.service.assistant.ProjectAssistant;
import com.akshat.taskmanager.util.PromptBuilder;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final ProjectAssistant assistant;

    public AiService(
            ProjectAssistant assistant
    ) {

        this.assistant = assistant;

    }

    public String ask(

            String userId,

            String prompt

    ){

        return assistant.chat(

                userId,

                prompt

        );

    }

    public String executeCommand(

            String user,

            String command

    ){

        return assistant.chat(

                user,

                PromptBuilder.commandPrompt(

                        command

                )

        );

    }

}