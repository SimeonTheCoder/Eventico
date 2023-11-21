package com.eventico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PointsController {
    @GetMapping("/redeem")
    public ModelAndView redeemPage() {
        return new ModelAndView("/redeem");
    }

    @GetMapping("/rewards")
    public ModelAndView rewardsPage() {
        return new ModelAndView("/rewards");
    }
}
