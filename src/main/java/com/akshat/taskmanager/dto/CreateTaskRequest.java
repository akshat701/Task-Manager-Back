package com.akshat.taskmanager.dto;

import lombok.Data;
import java.util.Date;

@Data
public class CreateTaskRequest {

    private String title;

    private String status;

    private String priority;

    private String project_id;

    private String assignee_id;

    private Date due_date;
}