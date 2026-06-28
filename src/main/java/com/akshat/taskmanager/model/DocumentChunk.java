package com.akshat.taskmanager.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "document_chunks")
public class DocumentChunk {

    @Id
    private String id;

    private String projectId;

    private Integer chunkIndex;

    private String content;
}