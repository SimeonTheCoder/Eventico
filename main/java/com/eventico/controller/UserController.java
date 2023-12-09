package com.eventico.controller;

import com.eventico.exceptions.AccessDeniedException;
import com.eventico.model.dto.UserLoginBinding;
import com.eventico.model.dto.UserRegisterBinding;
import com.eventico.service.CountryService;
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
    private final CountryService countryService;
    private final EventService eventService;

    public UserController(LoggedUser loggedUser, UserService userService, CountryService countryService, EventService eventsService) {
        this.loggedUser = loggedUser;
        this.userService = userService;
        this.countryService = countryService;
        this.eventService = eventsService;
    }

    @GetMapping("/login")
    public ModelAndView loginPage(@ModelAttribute("userLoginBindingModel") UserLoginBinding userLoginBindingModel) {
        return new ModelAndView("/login");
    }

    @PostMapping("/login")
    public ModelAndView loginForm(@ModelAttribute("userLoginBindingModel") @Valid UserLoginBinding userLoginBindingModel, BindingResult bindingResult) {
        boolean error;

        try{
            error = !userService.login(userLoginBindingModel);
        }catch(Exception e){
            error = true;
        }

        if(error){
            return new ModelAndView("/login", "error", error);
        }

        return new ModelAndView("home", "feed", eventService.getEventsForUser(loggedUser.getUsername()));
    }

    @GetMapping("/register")
    public ModelAndView registerPage(@ModelAttribute("userRegisterBindingModel") UserRegisterBinding userRegisterBindingModel) {
        return new ModelAndView("/register", "countries", countryService.getAll());
    }

    @PostMapping("/register")
    public ModelAndView registerForm(@ModelAttribute("userRegisterBindingModel") @Valid UserRegisterBinding userRegisterBindingModel, BindingResult bindingResult) {
        boolean error = false;

        try {
            error = !userService.register(userRegisterBindingModel);
        }catch (Exception exception) {
            error = true;
        }

        if(error) {
            ModelAndView newView = new ModelAndView("/register");
            newView.addObject("countries", countryService.getAll());
            newView.addObject("error", error);

            return newView;
        }

        return new ModelAndView("login", "userLoginBindingModel", new UserLoginBinding());
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
