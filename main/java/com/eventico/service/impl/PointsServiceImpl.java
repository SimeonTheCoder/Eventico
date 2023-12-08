package com.eventico.service.impl;

import com.eventico.model.entity.RedeemableCode;
import com.eventico.model.entity.User;
import com.eventico.repo.RedeemableCodeRepository;
import com.eventico.repo.UserRepository;
import com.eventico.service.LoggedUser;
import com.eventico.service.PointsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PointsServiceImpl implements PointsService {
    private final LoggedUser loggedUser;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RedeemableCodeRepository codeRepository;

    public PointsServiceImpl(LoggedUser loggedUser, PasswordEncoder passwordEncoder, UserRepository userRepository, RedeemableCodeRepository codeRepository) {
        this.loggedUser = loggedUser;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.codeRepository = codeRepository;
    }

    @Override
    public boolean redeemPoints(String code) {
        if(loggedUser == null) return false;

        User user = userRepository.findByUsername(loggedUser.getUsername());

        RedeemableCode codeEntity = codeRepository.findAll().stream()
                .filter((c) -> {
                    return passwordEncoder.matches(code, c.getCode());
                }).findFirst().orElse(null);

        if(codeEntity.isUsed()) return false;

        codeEntity.setUsed(true);
        codeRepository.save(codeEntity);

        userRepository.updatePointsById(user.getPoints() + codeEntity.getValue(), user.getId());

        return true;
    }
}
