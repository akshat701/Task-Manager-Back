package com.akshat.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class ProjectResponse {

    private String _id;

    private String name;

    private String description;

    private Date deadline;

    private String owner;

    private List<ProjectMemberResponse> members;

    private Date created_at;

    private Date createdAt;

    private Date updatedAt;
}