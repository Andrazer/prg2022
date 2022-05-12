package com.prg2022.proyectoQR.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class GeneralController {
    @GetMapping("/login")
    public ModelAndView getBrigadas(ModelAndView modelAndView) {
        modelAndView.setViewName("iniciar");
        return modelAndView;
    }    
    
}
