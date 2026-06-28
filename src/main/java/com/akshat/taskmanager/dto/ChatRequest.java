package com.akshat.taskmanager.dto;

import lombok.Data;

@Data
public class ChatRequest {

    private String projectId;
    private String question;
    private String userId;

}