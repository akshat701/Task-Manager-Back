package com.akshat.taskmanager.dto;

import lombok.Data;

@Data
public class AddMemberRequest {

    private String projectId;
    private String userId;
    private String role;
}