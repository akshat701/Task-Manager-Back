package com.akshat.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSummary {

    private String _id;
    private String name;
    private String email;
}