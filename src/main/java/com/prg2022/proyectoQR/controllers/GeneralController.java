package com.prg2022.proyectoQR.controllers;

import com.prg2022.proyectoQR.addons.cryptos;
import com.prg2022.proyectoQR.addons.QRCode.QRCodeGenerator;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class GeneralController {
    @GetMapping("/login")
    public ModelAndView getBrigadas(ModelAndView modelAndView) {
        modelAndView.setViewName("iniciar");
        return modelAndView;
    }    
    @GetMapping("/salir")
    public ModelAndView getSalir(ModelAndView modelAndView) {
        modelAndView.setViewName("salir");
        return modelAndView;
    }   
    
    @GetMapping(
        value = "/qr-{id}.png",
        produces = MediaType.IMAGE_PNG_VALUE
        )
        @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
        public @ResponseBody byte[] getImageWithMediaType(@PathVariable String id) throws Exception {
            cryptos Damecampos = new cryptos();
            return QRCodeGenerator.getQRCodeImage(Damecampos.encrypt(id), 450, 450);
        }
}
