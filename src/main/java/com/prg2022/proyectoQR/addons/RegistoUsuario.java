package com.prg2022.proyectoQR.addons;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.prg2022.proyectoQR.Repository.RoleRepository;
import com.prg2022.proyectoQR.Repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.prg2022.proyectoQR.modelos.EnumRole;
import com.prg2022.proyectoQR.modelos.Role;
import com.prg2022.proyectoQR.modelos.Usuario;


public class RegistoUsuario {
    private PasswordEncoder passwordEncoder;      
    private UsuarioRepository uRepository;
    private RoleRepository rRepository;

    public RegistoUsuario(UsuarioRepository uRepository, PasswordEncoder passwordEncoder) {
        this.uRepository = uRepository;
        this.passwordEncoder = passwordEncoder;
      }

    public RegistoUsuario(UsuarioRepository uRepository, RoleRepository rRepository, PasswordEncoder passwordEncoder) {
    this.uRepository = uRepository;
    this.rRepository = rRepository;
    this.passwordEncoder = passwordEncoder;
    }      
    
    public void habilita(Long id){
        Optional<Usuario> buscado = uRepository.findById(id);
        if(!buscado.isEmpty()){
            Usuario encontrado = buscado.get();
            encontrado.setClave(passwordEncoder.encode(encontrado.getDni()));
            uRepository.save(encontrado);
        }        
    }

    public Boolean puedeRegistrarse(String dniusuario){
        Optional<Usuario> buscado = uRepository.findByDni(dniusuario);
        if(!buscado.isEmpty()){
            Usuario encontrado = buscado.get();
           //---COMPRUEBA QUE EL USUARIO NO HA SIDO HABILITADO ANTES-----//
            if (BCrypt.checkpw(dniusuario, encontrado.getClave())){
                return true;
            }
        }
        return false;
    } 
    
    public boolean altaUsuario (String pwdusr, String referencia){
        Optional<Usuario> buscado = uRepository.findByDni(referencia);
        if(!buscado.isEmpty()){
            Usuario encontrado = buscado.get();
           //---COMPRUEBA QUE EL USUARIO NO HA SIDO HABILITADO ANTES-----//
           
            if (BCrypt.checkpw(referencia, encontrado.getClave())){
                //aqui empieza el registro
                encontrado.setClave(passwordEncoder.encode(pwdusr));
                /*
                if (encontrado.getRoles().isEmpty()){
                    Set<Role> permisos = new HashSet<>();
                    Role adminRole = rRepository.findByDescripcion(EnumRole.ROLE_ADMIN).get();
                    permisos.add(adminRole);
                    encontrado.setRoles(permisos);
                }*/
                uRepository.save(encontrado);
                //fin del registro
                return true;
            }
        }
        return false;
    }

}
