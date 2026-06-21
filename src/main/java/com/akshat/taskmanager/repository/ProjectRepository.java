package com.akshat.taskmanager.repository;

import com.akshat.taskmanager.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProjectRepository
        extends MongoRepository<Project, String> {

    List<Project> findByOwner(String owner);

    @Query("{ 'members.user': ?0 }")
    List<Project> findByMember(String userId);
}