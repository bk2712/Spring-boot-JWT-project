package com.jwt_revision.Test_jwt_methods.model;

import lombok.Data;

@Data
public class LoginUsers {
    private String email;
    private String password;

    public LoginUsers(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginUsers() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
