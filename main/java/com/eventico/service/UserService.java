package com.eventico.service;

import com.eventico.model.dto.UserLoginBinding;
import com.eventico.model.dto.UserRegisterBinding;
import com.eventico.model.entity.User;

public interface UserService {
    boolean register(UserRegisterBinding binding);

    boolean login(UserLoginBinding binding);

    boolean follow(String username);

    User findByUsername(String username);
}
