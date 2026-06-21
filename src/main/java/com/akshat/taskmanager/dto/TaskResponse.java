package com.akshat.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TaskResponse {

    private String _id;

    private String id;

    private String title;

    private String status;

    private String priority;

    private UserSummary assignedTo;

    private String assignee_id;

    private ProjectSummary project;

    private String project_id;

    private Date dueDate;

    private Date due_date;

    private Date createdAt;

    private Date updatedAt;

    private Integer __v;
}