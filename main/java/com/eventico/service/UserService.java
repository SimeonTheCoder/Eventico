package com.eventico.service;

import com.eventico.model.dto.UserLoginBinding;
import com.eventico.model.dto.UserRegisterBinding;

public interface UserService {
    public boolean register(UserRegisterBinding binding);

    public boolean login(UserLoginBinding binding);

    public boolean follow(String username);
}
