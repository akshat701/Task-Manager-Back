package com.akshat.taskmanager.dto;

import lombok.Data;

@Data
public class RemoveMemberRequest {

    private String projectId;
    private String userId;
}