package com.eventico.service.impl;

import com.eventico.model.dto.UserLoginBinding;
import com.eventico.model.dto.UserRegisterBinding;
import com.eventico.model.entity.Country;
import com.eventico.model.entity.User;
import com.eventico.repo.CountryRepository;
import com.eventico.repo.EventRepository;
import com.eventico.repo.UserRepository;
import com.eventico.service.LoggedUser;
import com.eventico.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CountryRepository countryRepository;
    private final PasswordEncoder passwordEncoder;

    private final LoggedUser loggedUser;

    public UserServiceImpl(UserRepository userRepository, EventRepository eventRepository, CountryRepository countryRepository, PasswordEncoder passwordEncoder, LoggedUser loggedUser) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.countryRepository = countryRepository;
        this.passwordEncoder = passwordEncoder;
        this.loggedUser = loggedUser;
    }

    @Override
    @Transactional
    public boolean register(UserRegisterBinding binding) {
        if(!binding.getPassword().equals(binding.getRepeat())) return false;
        if(userRepository.findByUsername(binding.getUsername()) != null) return false;

        if(binding.getUsername().length() < 3 || binding.getUsername().length() > 20) return false;
        if(binding.getPassword().length() < 3 || binding.getPassword().length() > 20) return false;

        User user = new User();

        user.setUsername(binding.getUsername());
        user.setPassword(passwordEncoder.encode(binding.getPassword()));

        user.setEmail(binding.getEmail());
        user.setRole(binding.getRole());

        user.setPoints(0);
        user.setParticipationEvents(new ArrayList<>());
        user.setAddedEvents(new ArrayList<>());

        Country country = countryRepository.findByName(binding.getCountry());

        if(country == null) return false;

        user.setCountry(country);
        country.getUsers().add(user);

        userRepository.save(user);

        return true;
    }

    @Override
    public boolean login(UserLoginBinding binding) {
        User user = userRepository.findByUsername(binding.getUsername());

        if(user == null) return false;
        if (!passwordEncoder.matches(binding.getPassword(), user.getPassword())) return false;

        loggedUser.login(user);

        return true;
    }

    @Override
    public boolean follow(String username) {
        if(!loggedUser.isLogged()) return false;

        User followedUser = userRepository.findByUsername(username);

        if(followedUser == null) return false;

        User user = userRepository.findByUsername(loggedUser.getUsername());
        user.getFollowedUsers().add(followedUser);

        userRepository.save(user);

        loggedUser.login(user);

        return true;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
