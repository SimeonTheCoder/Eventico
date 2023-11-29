package com.eventico.service.impl;

import com.eventico.model.entity.User;
import com.eventico.repo.UserRepository;
import com.eventico.service.LoggedUser;
import com.eventico.service.PointsService;
import com.eventico.util.CodeValidator;
import org.springframework.stereotype.Service;

@Service
public class PointsServiceImpl implements PointsService {
    private final LoggedUser loggedUser;
    private final UserRepository userRepository;

    public PointsServiceImpl(LoggedUser loggedUser, UserRepository userRepository) {
        this.loggedUser = loggedUser;
        this.userRepository = userRepository;
    }

    @Override
    public boolean redeemPoints(String code) {
        int status = CodeValidator.validate(code);

        if(status == -1) return false;
        if(loggedUser == null) return false;

        User user = userRepository.findByUsername(loggedUser.getUsername());

        if(status == 0) {
            userRepository.updatePointsById(user.getPoints() + 10, user.getId());
        }else if(status == 1) {
            userRepository.updatePointsById(user.getPoints() + 20, user.getId());
        }else if(status == 2) {
            userRepository.updatePointsById(user.getPoints() + 50, user.getId());
        }

        return true;
    }
}
