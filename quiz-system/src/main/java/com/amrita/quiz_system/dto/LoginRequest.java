package com.amrita.quiz_system.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String id; // This will be rollNo or tchrID
    private String password;
}