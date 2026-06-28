package com.akshat.taskmanager.service;

import com.akshat.taskmanager.model.DocumentChunk;
import com.akshat.taskmanager.repository.DocumentChunkRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentChunkService {

    private final DocumentChunkRepository repository;

    public DocumentChunkService(
            DocumentChunkRepository repository
    ) {
        this.repository = repository;
    }

    public void saveChunks(

            String projectId,

            String text

    ) {

        List<String> chunks = split(text,1000);

        int index = 0;

        for(String chunk : chunks){

            DocumentChunk documentChunk =
                    new DocumentChunk();

            documentChunk.setProjectId(projectId);

            documentChunk.setChunkIndex(index++);

            documentChunk.setContent(chunk);

            repository.save(documentChunk);

        }

    }

    private List<String> split(

            String text,

            int size

    ){

        List<String> list =
                new ArrayList<>();

        int start = 0;

        while(start < text.length()){

            int end =
                    Math.min(
                            start + size,
                            text.length()
                    );

            list.add(
                    text.substring(start,end)
            );

            start = end;

        }

        return list;

    }

}