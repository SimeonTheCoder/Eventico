package com.eventico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EventsController {
    @GetMapping("add-event")
    public ModelAndView addModelPage() {
        return new ModelAndView("add-event");
    }

    @PostMapping("add-event")
    public String addModelForm() {
        return "redirect:/home";
    }
}
