package com.myoidc.auth_server.models;

import com.myoidc.auth_server.dto.UserEntityDTO;

public class AuthResponse {
    private String token;

    private long expiresIn;

    private boolean success;

    private UserEntityDTO user;

    public AuthResponse(){}

    public UserEntityDTO getUser() {
        return user;
    }

    public void setUser(UserEntityDTO user) {
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    public String getToken() {
        return token;
    }

    public AuthResponse setToken(String token) {
        this.token = token;
        return this;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public AuthResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }
}
