package com.eventico.controller;

import com.eventico.model.dto.UserLoginBinding;
import com.eventico.model.dto.UserRegisterBinding;
import com.eventico.service.EventService;
import com.eventico.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    private final UserService userService;
    private final EventService eventsService;

    public UserController(UserService userService, EventService eventsService) {
        this.userService = userService;
        this.eventsService = eventsService;
    }

    @GetMapping("/login")
    public ModelAndView loginPage(@ModelAttribute("userLoginBindingModel") UserLoginBinding userLoginBindingModel) {
        return new ModelAndView("/login");
    }

    @PostMapping("/login")
    public String loginForm(@ModelAttribute("userRegisterBindingModel") @Valid UserLoginBinding userLoginBindingModel, BindingResult bindingResult) {
        boolean result = userService.login(userLoginBindingModel);

        System.out.println(result ? "Success" : "Error");

        return "redirect:/home";
    }

    @GetMapping("/register")
    public ModelAndView registerPage(@ModelAttribute("userRegisterBindingModel") UserRegisterBinding userRegisterBindingModel) {
        return new ModelAndView("/register");
    }

    @PostMapping("/register")
    public String registerForm(@ModelAttribute("userRegisterBindingModel") @Valid UserRegisterBinding userRegisterBindingModel, BindingResult bindingResult) {
        userService.register(userRegisterBindingModel);
        return "redirect:/login";
    }

    @GetMapping("/account")
    public ModelAndView accountPage() {
        return new ModelAndView("/account");
    }
}
