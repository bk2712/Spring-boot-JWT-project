package com.jwt_revision.Test_jwt_methods.model;

import lombok.Data;

@Data
public class UpdatePassword {
    private String password;
    private String confirmPassword;

    public UpdatePassword(String password, String confirmPassword) {
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public UpdatePassword() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
