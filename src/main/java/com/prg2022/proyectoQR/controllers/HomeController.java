package com.prg2022.proyectoQR.controllers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.prg2022.proyectoQR.Repository.MovimientoRepository;
import com.prg2022.proyectoQR.Repository.UsuarioRepository;
import com.prg2022.proyectoQR.modelos.EnumRole;
import com.prg2022.proyectoQR.modelos.Movimiento;
import com.prg2022.proyectoQR.modelos.Role;
import com.prg2022.proyectoQR.modelos.Usuario;
import com.prg2022.proyectoQR.payload.request.UploadFileRequest;
import com.prg2022.proyectoQR.services.UserDetailsImpl;

import io.jsonwebtoken.lang.Arrays;

@Controller

public class HomeController {
    @Autowired
    private UsuarioRepository urepository;
    @Autowired
    private MovimientoRepository mrepository;

        @GetMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ModelAndView home(ModelAndView modelAndView){
        UserDetailsImpl userDetails = 
        (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario validador = urepository.getById(userDetails.getId());
        Set<Role> pepito = validador.getRoles();
        if (pepito.contains(EnumRole.ROLE_USER)){
            System.out.println("OK; ES USUARIO");
        }
        
        List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

        String rol_actual=roles.toString();
        
        if(rol_actual.contains("ROLE_USER")){
        
            boolean enespera = false;
            boolean registrable = false;
            Usuario usuario = urepository.getById(userDetails.getId());
            List<Movimiento> movimientos = mrepository.findTop10ByUsuarioOrderByIdDesc(usuario);
            //List<Usuario> usuarios = urepository.findByBrigada(brigada);
            UploadFileRequest subeFoto = new UploadFileRequest();
            modelAndView.addObject("movimientos", movimientos);
            modelAndView.addObject("usuario", usuario);
            modelAndView.addObject("id", userDetails.getId());
            modelAndView.setViewName("usuario_basico");
            System.out.println("**"+usuario.getClave()+"**");
            if (usuario.getClave()==null){
              registrable=true;
            } else {
              if (BCrypt.checkpw(usuario.getDni(), usuario.getClave())){
                enespera=true;
              } 
            }
            modelAndView.addObject("registrable", registrable);
            modelAndView.addObject("enespera", enespera);
            modelAndView.addObject("subeFoto", subeFoto);       

        }else{
            modelAndView.setViewName("master");
        }
        return modelAndView;      

    }
    
}
