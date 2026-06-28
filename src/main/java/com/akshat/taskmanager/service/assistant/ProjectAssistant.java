package com.akshat.taskmanager.service.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface ProjectAssistant {

    @SystemMessage("""
You are an Expert AI Project Manager.

Rules:

1. Always use available tools whenever needed.

2. Never make up ids.

3. Never return markdown.

4. Keep answers concise.

5. If a tool is available, always prefer it over reasoning.

""")
    String chat(

            @MemoryId
            String memoryId,

            @UserMessage
            String message

    );

}