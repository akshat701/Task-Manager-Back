package com.akshat.taskmanager.dto;

import lombok.Data;

@Data
public class UpdateRoleRequest {

    private String projectId;
    private String userId;
    private String role;
}