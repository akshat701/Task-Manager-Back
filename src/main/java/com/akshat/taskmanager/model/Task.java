package com.akshat.taskmanager.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "tasks")
public class Task {

    @Id
    private String id;

    private String title;
    private String status;
    private String priority;

    private String assignedTo;
    private String projectId;

    private Date dueDate;

    private Date createdAt = new Date();
    private Date updatedAt = new Date();

    private Integer __v = 0;
}