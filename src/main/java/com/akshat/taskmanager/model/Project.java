package com.akshat.taskmanager.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "projects")
public class Project {

    @Id
    private String id;

    private String name;

    private String description = "";

    private Date deadline;

    private String owner;

    private List<ProjectMember> members =
            new ArrayList<>();

    private Date created_at = new Date();

    private Date createdAt = new Date();

    private Date updatedAt = new Date();
}