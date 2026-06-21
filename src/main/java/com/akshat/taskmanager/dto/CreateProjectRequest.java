package com.akshat.taskmanager.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CreateProjectRequest {

    private String name;

    private String description;

    private Date deadline;
    private List<String> members;
}