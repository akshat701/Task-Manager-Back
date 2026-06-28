package com.akshat.taskmanager.service;

import com.akshat.taskmanager.service.assistant.ProjectAssistant;
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

}