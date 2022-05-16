package com.prg2022.proyectoQR.controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.prg2022.proyectoQR.Repository.BrigadaRepository;
import com.prg2022.proyectoQR.modelos.Brigada;

@Controller
@RequestMapping("/brigada")
public class BrigadaController {
    @Autowired
    private BrigadaRepository brepository;

    @GetMapping("")
    public ModelAndView getBrigadas(ModelAndView modelAndView, Model pagina) {
        pagina.addAttribute("titulo", "Brigadas");
        modelAndView.addObject("pagina", pagina);
        modelAndView.setViewName("brigada");
        return modelAndView;
    }

    @GetMapping(value = "/all", produces= MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<Collection<Brigada>> findAll() {
        List<Brigada> brigada = (List<Brigada>) brepository.findAll();
        return ResponseEntity.ok(brigada);
    }
}

