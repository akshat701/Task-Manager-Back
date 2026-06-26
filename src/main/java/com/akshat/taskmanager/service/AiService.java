package com.akshat.taskmanager.service;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final ChatModel model;

    public AiService(ChatModel model) {
        this.model = model;
    }

    public String ask(String prompt) {

        ChatRequest request = ChatRequest.builder()
                .messages(UserMessage.from(prompt))
                .build();

        ChatResponse response = model.chat(request);

        return response.aiMessage().text();
    }
}