package com.eventico.model.dto;

import com.eventico.model.enums.UserRoles;
import com.eventico.service.UserService;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegisterBinding {
    private final UserService userService;

    @Column(unique = true, nullable = false)
    @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters!")
    private String username;

    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!")
    @Column(nullable = false)
    private String password;

    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!")
    @Column(nullable = false)
    private String repeat;

    @Email
    @NotBlank(message = "Email must not be blank!")
    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoles role;

    @Column(nullable = false)
    private String country;

    public UserRegisterBinding(UserService userService) {
        this.userService = userService;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public UserService getUserService() {
        return userService;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
