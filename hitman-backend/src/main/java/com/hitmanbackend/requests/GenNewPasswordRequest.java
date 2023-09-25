package com.hitmanbackend.requests;

public class GenNewPasswordRequest {

    private String username;

    private String newPassword;

    public GenNewPasswordRequest(String username, String newPassword) {
        this.username = username;
        this.newPassword = newPassword;
    }

    public GenNewPasswordRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
