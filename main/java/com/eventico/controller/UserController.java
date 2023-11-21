package com.eventico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @GetMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("/login");
    }

    @GetMapping("/register")
    public ModelAndView registerPage() {
        return new ModelAndView("/register");
    }

    @GetMapping("/account")
    public ModelAndView accountPage() {
        return new ModelAndView("/account");
    }
}
