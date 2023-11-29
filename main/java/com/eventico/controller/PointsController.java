package com.eventico.controller;

import com.eventico.exceptions.AccessDeniedException;
import com.eventico.service.PointsService;
import com.eventico.service.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PointsController {
    private final LoggedUser loggedUser;
    private final PointsService pointsService;

    public PointsController(LoggedUser loggedUser, PointsService pointsService) {
        this.loggedUser = loggedUser;
        this.pointsService = pointsService;
    }

    @GetMapping("/redeem")
    public ModelAndView redeemPage() {
        if(!loggedUser.isLogged()) throw new AccessDeniedException();

        return new ModelAndView("/redeem");
    }

    @PostMapping("/redeem")
    public String redeemCodePage(String code) {
        if(!loggedUser.isLogged()) throw new AccessDeniedException();

        boolean result = pointsService.redeemPoints(code);
        return "redirect:/";
    }

    @GetMapping("/rewards")
    public ModelAndView rewardsPage() {
        if(!loggedUser.isLogged()) throw new AccessDeniedException();

        return new ModelAndView("/rewards");
    }
}
