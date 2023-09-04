package com.hitmanbackend.responses;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class LoginResponse {

    private String username;
    private String token;

    String role;

    public LoginResponse(String username, String token, String role) {
        this.username = username;
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
