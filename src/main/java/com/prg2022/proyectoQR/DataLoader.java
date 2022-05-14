package com.prg2022.proyectoQR;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.prg2022.proyectoQR.Repository.AlumnoRepository;
import com.prg2022.proyectoQR.Repository.BrigadaRepository;
import com.prg2022.proyectoQR.Repository.RoleRepository;
import com.prg2022.proyectoQR.modelos.Alumno;
import com.prg2022.proyectoQR.modelos.Brigada;
import com.prg2022.proyectoQR.modelos.EnumRole;
import com.prg2022.proyectoQR.modelos.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {



    private AlumnoRepository aRepository;
    private BrigadaRepository bRepository;
    private RoleRepository rRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    public DataLoader(AlumnoRepository aRepository, BrigadaRepository bRepository, RoleRepository rRepository) {
        this.aRepository = aRepository;
        this.bRepository = bRepository;
        this.rRepository = rRepository;
    }




    @Override
    public void run(ApplicationArguments args) throws Exception {

        Optional<Alumno> alumno = aRepository.findByDni("Admin");
        List<Brigada> brigada = bRepository.findByDescripcion("Sistema");
        Optional<Role> roles = rRepository.findByDescripcion(EnumRole.ROLE_ADMIN);
        if (brigada.isEmpty()) {
            bRepository.save(new Brigada("Sistema"));
            System.out.println("No hay Brigada de sistema, creando ...");
        }
        if (roles.isEmpty()) {
            System.out.println("No hay roles, creando roles...");
            for (EnumRole dir : EnumRole.values()) {
                rRepository.save(new Role(dir));
              }
        }    //(String nombre, String dni, Brigada brigada)
        if (alumno.isEmpty()) {
            System.out.println("No hay administrador, creando usuario Admin con password 1234");
            
            Alumno agregado = aRepository.save(
                    new Alumno(
                        "Administrador",
                        "Admin",
                        bRepository.findByDescripcion("Sistema").get(0)));
            agregado.setClave(passwordEncoder.encode("123456"));  
            Set<Role> permisos = new HashSet<>();
            Role adminRole = rRepository.findByDescripcion(EnumRole.ROLE_ADMIN).get();
            permisos.add(adminRole);
            agregado.setRoles(permisos);
            aRepository.save(agregado);
        }                    
    }
}