package com.akshat.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddMemberResponse {

    private String message;

    private UserResponse user;
}