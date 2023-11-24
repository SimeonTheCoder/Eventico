package com.eventico.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;

public class UserLoginBinding {
    @Column(unique = true, nullable = false)
    @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters!")
    private String username;

    @Column(nullable = false)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
