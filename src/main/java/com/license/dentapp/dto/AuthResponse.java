package com.license.dentapp.dto;

public class AuthResponse {
    private String token;
    private Integer clientId;
    private String role;

    public AuthResponse() {
    }

    public AuthResponse(String token, Integer clientId, String role) {
        this.token = token;
        this.clientId = clientId;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
