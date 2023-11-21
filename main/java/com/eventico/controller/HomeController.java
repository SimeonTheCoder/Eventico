package com.eventico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @GetMapping("/home")
    public ModelAndView homePage() {
        return new ModelAndView("home");
    }

    @GetMapping("/browse")
    public ModelAndView browsePage() {
        return new ModelAndView("browse");
    }

    @GetMapping("/event-info")
    public ModelAndView eventInfoPage() {
        return new ModelAndView("event-info");
    }

    @GetMapping("/")
    public ModelAndView indexPage(){
        return new ModelAndView("index");
    }
}
