package com.akshat.taskmanager.repository;

import com.akshat.taskmanager.model.DocumentChunk;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DocumentChunkRepository
        extends MongoRepository<DocumentChunk,String> {

    List<DocumentChunk> findByProjectIdOrderByChunkIndex(
            String projectId
    );

}