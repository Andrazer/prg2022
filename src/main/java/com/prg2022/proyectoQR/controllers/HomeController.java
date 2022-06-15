package com.prg2022.proyectoQR.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.prg2022.proyectoQR.modelos.Usuario;
import com.prg2022.proyectoQR.services.UserDetailsImpl;
import com.prg2022.proyectoQR.Repository.UsuarioRepository;
import com.prg2022.proyectoQR.modelos.EnumRole;
import com.prg2022.proyectoQR.modelos.Role;

@Controller

public class HomeController {
    @Autowired
    UsuarioRepository urepository;
    @GetMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String home(){
        UserDetailsImpl userDetails = 
        (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario validador = urepository.getById(userDetails.getId());
        Set<Role> pepito = validador.getRoles();
        if (pepito.contains(EnumRole.ROLE_USER)){
            System.out.println("OK; ES USUARIO");
        }
        
        return "master";


    }
    
}
