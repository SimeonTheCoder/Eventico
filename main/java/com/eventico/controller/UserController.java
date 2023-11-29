package com.eventico.controller;

import com.eventico.exceptions.AccessDeniedException;
import com.eventico.model.dto.UserLoginBinding;
import com.eventico.model.dto.UserRegisterBinding;
import com.eventico.repo.UserRepository;
import com.eventico.service.EventService;
import com.eventico.service.UserService;
import com.eventico.service.LoggedUser;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    private final LoggedUser loggedUser;
    private final UserService userService;
    private final EventService eventsService;
    private final UserRepository userRepository;

    public UserController(LoggedUser loggedUser, UserService userService, EventService eventsService, UserRepository userRepository) {
        this.loggedUser = loggedUser;
        this.userService = userService;
        this.eventsService = eventsService;
        this.userRepository = userRepository;
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

        return new ModelAndView("/account", "user", userRepository.findByUsername(loggedUser.getUsername()));
    }

    @PostMapping("/logout")
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
