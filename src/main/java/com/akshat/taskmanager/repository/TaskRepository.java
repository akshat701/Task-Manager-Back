package com.akshat.taskmanager.repository;

import com.akshat.taskmanager.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository
        extends MongoRepository<Task, String> {

    List<Task> findByAssignedTo(String assignedTo);

    long countByAssignedTo(String assignedTo);

    long countByAssignedToAndStatus(
            String assignedTo,
            String status
    );

    long countByAssignedToAndStatusNot(
            String assignedTo,
            String status
    );
    
}