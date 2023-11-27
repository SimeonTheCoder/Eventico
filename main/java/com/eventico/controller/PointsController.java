package com.eventico.controller;

import com.eventico.service.PointsService;
import com.eventico.service.impl.LoggedUser;
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
        return new ModelAndView("/redeem");
    }

    @PostMapping("/redeem")
    public String redeemCodePage(String code) {
        boolean result = pointsService.redeemPoints(code);
        return "redirect:/";
    }

    @GetMapping("/rewards")
    public ModelAndView rewardsPage() {
        return new ModelAndView("/rewards");
    }
}
