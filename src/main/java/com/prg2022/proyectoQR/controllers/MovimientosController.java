package com.prg2022.proyectoQR.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.prg2022.proyectoQR.Repository.MovimientoRepository;

@Controller

@RequestMapping("/movimientos")

public class MovimientosController {
    @Autowired
    private MovimientoRepository mrepository;
    
    @GetMapping("")
    public ModelAndView getLector(ModelAndView modelAndView, Model pagina) {
        pagina.addAttribute("titulo", "Movimientos");
        //modelAndView.addObject("pagina", pagina);
        modelAndView.setViewName("movimientos");
        return modelAndView;
    }
}
