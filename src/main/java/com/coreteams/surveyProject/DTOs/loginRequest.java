package com.coreteams.surveyProject.DTOs;

import lombok.Data;

@Data
public class loginRequest {
    private String email;
    private String password;
}