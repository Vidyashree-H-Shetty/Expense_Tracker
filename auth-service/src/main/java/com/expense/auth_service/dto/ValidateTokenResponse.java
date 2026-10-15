package com.expense.auth_service.dto;



import java.util.List;

public class ValidateTokenResponse {
    private String username;
    private List<String> roles;
    private boolean valid;

    public ValidateTokenResponse() {}

    public ValidateTokenResponse(String username, List<String> roles, boolean valid) {
        this.username = username;
        this.roles = roles;
        this.valid = valid;
    }

    public String getUsername() { return username; }
    public List<String> getRoles() { return roles; }
    public boolean isValid() { return valid; }

    public void setUsername(String username) { this.username = username; }
    public void setRoles(List<String> roles) { this.roles = roles; }
    public void setValid(boolean valid) { this.valid = valid; }
}

