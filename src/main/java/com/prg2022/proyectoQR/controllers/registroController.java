package com.prg2022.proyectoQR.controllers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.prg2022.proyectoQR.Repository.RoleRepository;
import com.prg2022.proyectoQR.Repository.UsuarioRepository;
import com.prg2022.proyectoQR.addons.RegistoUsuario;
import com.prg2022.proyectoQR.modelos.EnumRole;
import com.prg2022.proyectoQR.modelos.Role;
import com.prg2022.proyectoQR.modelos.Usuario;
import com.prg2022.proyectoQR.security.checkPasswd;

@Controller
public class registroController {


    @Autowired
    UsuarioRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;   
    
    @Autowired
    private RoleRepository rRepository;
    @Autowired
    private UsuarioRepository aRepository;

    private String dniEnctiptado;

    private String dniIntroducido;

    //html para registrarse
    @GetMapping(value = "/registro")
    public String resgistro() {
       return "registro_dni";
    }


    //html para establecer clave
    @GetMapping(value = "/clave")
    public String resgistro2() {
       return "registro_clave";
    }        

    @GetMapping("/habilitaUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> Habilitar(@PathVariable Long id) {
        RegistoUsuario habilitar = new RegistoUsuario();
        habilitar.habilita(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping(value = "/procesaregistro")
    public String buscausuario(String dniusuario,Model modelo ){
        RegistoUsuario habilitar = new RegistoUsuario();
        if (habilitar.puedeRegistrarse(dniusuario)){
            return "registro_clave";
        } 
        return "registro_dni";
    }

/*
    @PostMapping(value = "/establecer")
    public String establecer(
                        String contrasenausuario,
                        String contrasenausuario2,
                        String referencia,
                        Model modelo ){         

      if(BCrypt.checkpw(dniIntroducido, referencia)){
    
        if(checkPasswd.check(contrasenausuario) && contrasenausuario.equals(contrasenausuario2)){

            Optional<Usuario> buscado = userRepository.findByDni(dniIntroducido);
            Usuario encontrado = buscado.get();
            String nombre= encontrado.getNombre();
            encontrado.setClave(passwordEncoder.encode(contrasenausuario));
            modelo.addAttribute("nombreusuario", nombre);

                // CODIGO NECESARIO PARA ESTABLECER UN ROL 
                Set<Role> permisos = new HashSet<>();
                Role adminRole = rRepository.findByDescripcion(EnumRole.ROLE_ADMIN).get();
                permisos.add(adminRole);
                encontrado.setRoles(permisos);
                aRepository.save(encontrado);

            return "ingreso";

        }else{
            return "contraseñadif";
        }
    }else{
        return "pirata";
    }
    }
    */
}