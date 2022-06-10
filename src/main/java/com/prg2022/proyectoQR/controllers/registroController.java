package com.prg2022.proyectoQR.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.prg2022.proyectoQR.Repository.UsuarioRepository;
import com.prg2022.proyectoQR.addons.RegistoUsuario;

@Controller
public class registroController {
    @Autowired
    private UsuarioRepository uRepository;
    
    //para habilitar usuarios
    @GetMapping("/habilitaUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> Habilitar(@PathVariable Long id) {
        RegistoUsuario regUsr = new RegistoUsuario(uRepository);
        regUsr.habilita(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }    

    //html para registrarse
    @GetMapping(value = "/registro")
    public String resgistro(Model modelo) {
       modelo.addAttribute("error",false);
       return "registro_dni";
    }

    //html para cambiar la clave a un usuario que puede registrarse
    @PostMapping(value = "/procesaregistro")
    public String buscausuario(String dniusuario,Model modelo ){
        RegistoUsuario regUsr = new RegistoUsuario(uRepository);
        if (regUsr.puedeRegistrarse(dniusuario)){
            modelo.addAttribute("referencia",dniusuario);
            modelo.addAttribute("error",false);
            return "registro_clave";
        } 
        modelo.addAttribute("error",true);
        return "registro_dni";
    }

    //cambia la clave a un usuario que puede registrarse y lo envia al login si todo ok
    @PostMapping(value = "/establecer")
    public String establecer(
                        String pwdusr,
                        String referencia,
                        Model modelo ){        
        RegistoUsuario regUsr = new RegistoUsuario(uRepository);
        if (regUsr.altaUsuario(pwdusr, referencia)){
            return "iniciar";
        } else {
            modelo.addAttribute("referencia",referencia);
            modelo.addAttribute("error",true);
            return "registro_clave";
        }
    }
}