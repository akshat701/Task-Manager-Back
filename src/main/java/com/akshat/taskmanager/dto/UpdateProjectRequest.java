package com.akshat.taskmanager.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateProjectRequest {

    private String name;
    private String description;
    private Date deadline;
}