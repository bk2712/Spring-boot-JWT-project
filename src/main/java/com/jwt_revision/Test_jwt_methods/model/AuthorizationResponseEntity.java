package com.jwt_revision.Test_jwt_methods.model;

public class AuthorizationResponseEntity {
    private String token;
    private String message;

    public AuthorizationResponseEntity() {
    }

    public AuthorizationResponseEntity(String token, String errorMessage) {
        this.token = token;
        this.message = errorMessage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
