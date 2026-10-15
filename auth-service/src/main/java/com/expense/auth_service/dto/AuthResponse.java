package com.expense.auth_service.dto;

import java.util.List;

public class AuthResponse {
    private String token;

    public AuthResponse() {}
    
    public AuthResponse(String token, String email, List<String> roles) {
        this.token = token;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

}

