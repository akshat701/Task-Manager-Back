package com.akshat.taskmanager.service;

import com.akshat.taskmanager.util.PromptBuilder;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PdfAiService {

    private final AiService aiService;
    private final DocumentChunkService documentChunkService;

    public PdfAiService(AiService aiService, DocumentChunkService documentChunkService) {
        this.aiService = aiService;
        this.documentChunkService = documentChunkService;
    }

    public String uploadPdf(
            String projectId,
            MultipartFile file
    ) throws IOException {

        PDDocument document =
                Loader.loadPDF(file.getBytes());

        PDFTextStripper stripper =
                new PDFTextStripper();

        String pdfText =
                stripper.getText(document);

        documentChunkService.saveChunks(
                projectId,
                pdfText
        );

        document.close();

        return "PDF uploaded successfully. Total document chunks created.";

    }

}