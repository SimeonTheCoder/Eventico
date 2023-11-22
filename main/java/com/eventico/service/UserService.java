package com.eventico.service;

import com.eventico.model.dto.UserRegisterBinding;

public interface UserService {
    public boolean register(UserRegisterBinding binding);
}
