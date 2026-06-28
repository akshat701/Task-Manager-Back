package com.akshat.taskmanager.controller;

import com.akshat.taskmanager.dto.*;
import com.akshat.taskmanager.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;
    private final AiChatService aiChatService;
    private final AiTaskGeneratorService aiTaskGeneratorService;
    private final AiSummaryService aiSummaryService;
    private final PdfAiService pdfAiService;

    public AiController(
            AiService aiService,
            AiChatService aiChatService,
            AiTaskGeneratorService aiTaskGeneratorService,
             AiSummaryService aiSummaryService,
            PdfAiService pdfAiService
    ) {
        this.aiService = aiService;
        this.aiChatService = aiChatService;
        this.aiTaskGeneratorService = aiTaskGeneratorService;
        this.aiSummaryService = aiSummaryService;
        this.pdfAiService = pdfAiService;
    }

    @PostMapping("/generate-tasks")
    public AiGenerateTaskResponse generateTasks(
            @RequestBody GenerateTaskRequest request
    ) {
        return aiTaskGeneratorService.generateAndSaveTasks(request);
    }

    @PostMapping("/chat")
    public ChatResponse chat(
            @RequestBody ChatRequest request
    ) {
        return aiChatService.chat(request);
    }

    @PostMapping("/agent")
    public String agent(

            Authentication authentication,

            @RequestBody ChatRequest request

    ){

        return aiService.ask(

                authentication.getName(),

                request.getQuestion()

        );

    }

    @PostMapping("/project-summary/{projectId}")
    public ProjectSummaryResponse projectSummary(

            @PathVariable
            String projectId

    ) {

        return aiSummaryService.generateSummary(
                projectId
        );

    }

    @PostMapping("/project/{projectId}/pdf")
    public String uploadPdf(

            @PathVariable String projectId,

            @RequestParam MultipartFile file

    ) throws Exception {

        return pdfAiService.uploadPdf(
                projectId,
                file
        );

    }

    @PostMapping("/command")
    public String command(

            Authentication authentication,

            @RequestBody ChatRequest request

    ){

        return aiService.executeCommand(

                authentication.getName(),

                request.getQuestion()

        );

    }

}