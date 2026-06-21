package com.akshat.taskmanager.model;

import lombok.Data;

@Data
public class ProjectMember {

    private String user;

    private String role = "member";
}