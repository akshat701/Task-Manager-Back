package com.akshat.taskmanager.repository;

import com.akshat.taskmanager.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

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

    boolean existsByProjectIdAndTitle(
            String projectId,
            String title
    );

    List<Task> findByProjectId(String projectId);

    long countByProjectId(String projectId);

    long countByProjectIdAndStatus(
            String projectId,
            String status
    );

    long countByProjectIdAndPriority(
            String projectId,
            String priority
    );

    Optional<Task> findByProjectIdAndTitleIgnoreCase(
            String projectId,
            String title
    );

    List<Task> findByProjectIdAndStatus(
            String projectId,
            String status
    );

    List<Task> findByProjectIdAndPriority(
            String projectId,
            String priority
    );

    void deleteByProjectIdAndTitleIgnoreCase(
            String projectId,
            String title
    );
    
}