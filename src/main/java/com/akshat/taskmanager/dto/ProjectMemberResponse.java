package com.akshat.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectMemberResponse {



    private UserSummary user;

    private String role;
}