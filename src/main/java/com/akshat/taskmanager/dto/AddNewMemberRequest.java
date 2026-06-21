package com.akshat.taskmanager.dto;

import lombok.Data;

@Data
public class AddNewMemberRequest {

    private String projectId;

    private String name;

    private String email;

    private String password;

    private String role;
}