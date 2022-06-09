package com.prg2022.proyectoQR.addons;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.prg2022.proyectoQR.Repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import com.prg2022.proyectoQR.modelos.Usuario;

public class RegistoUsuario {
    @Autowired
    UsuarioRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;   
    
    public void habilita(Long id){
        Optional<Usuario> buscado = userRepository.findById(id);
        if(!buscado.isEmpty()){
            Usuario encontrado = buscado.get();
            encontrado.setClave(passwordEncoder.encode(encontrado.getDni()));
            userRepository.save(encontrado);
        }        
    }

    public Boolean puedeRegistrarse(String dniusuario){
        Optional<Usuario> buscado = userRepository.findByDni(dniusuario);
        if(!buscado.isEmpty()){
            Usuario encontrado = buscado.get();
           //---COMPRUEBA QUE EL USUARIO NO HA SIDO HABILITADO ANTES-----//
            if (BCrypt.checkpw(dniusuario, encontrado.getClave())){
                return true;
            }
        }
        return false;
    } 
}
