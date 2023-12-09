package com.eventico.controller;

import com.eventico.exceptions.AccessDeniedException;
import com.eventico.model.dto.UserLoginBinding;
import com.eventico.model.dto.UserRegisterBinding;
import com.eventico.service.EventService;
import com.eventico.service.LoggedUser;
import com.eventico.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    private final LoggedUser loggedUser;
    private final UserService userService;
    private final EventService eventsService;

    public UserController(LoggedUser loggedUser, UserService userService, EventService eventsService) {
        this.loggedUser = loggedUser;
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
        if(!loggedUser.isLogged()) throw new AccessDeniedException();

        return new ModelAndView("/account", "user", userService.findByUsername(loggedUser.getUsername()));
    }

    @RequestMapping("/logout")
    public String logout() {
        if(!loggedUser.isLogged()) throw new AccessDeniedException();

        loggedUser.logout();
        return "redirect:/login";
    }

    @GetMapping("/follow/{username}")
    public String followUser(@PathVariable("username") String username) {
        if(!loggedUser.isLogged()) throw new AccessDeniedException();

        boolean result = userService.follow(username);

        if(!result) return "redirect:/error";

        return "redirect:/browse";
    }
}
