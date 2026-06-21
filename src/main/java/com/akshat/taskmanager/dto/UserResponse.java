package com.akshat.taskmanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {

    @JsonProperty("_id")
    private String id;

    private String name;
    private String email;
    private String role;
}