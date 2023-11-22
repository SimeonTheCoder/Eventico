package com.eventico.service.impl;

import com.eventico.model.dto.UserRegisterBinding;
import com.eventico.model.entity.User;
import com.eventico.repo.EventRepository;
import com.eventico.repo.UserRepository;
import com.eventico.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, EventRepository eventRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean register(UserRegisterBinding binding) {
        User user = new User();

        user.setUsername(binding.getUsername());
        user.setPassword(passwordEncoder.encode(binding.getPassword()));

        user.setEmail(binding.getEmail());
        user.setRole(binding.getRole());

        user.setPoints(0);
        user.setParticipationEvents(new ArrayList<>());
        user.setAddedEvents(new ArrayList<>());

        userRepository.save(user);

        System.out.println(binding.getUsername());
        System.out.println(binding.getPassword());
        System.out.println(binding.getEmail());
        System.out.println(binding.getRole());

        return false;
    }
}
