package com.prg2022.proyectoQR;



import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.io.FileInputStream;
import java.io.IOException;

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
        //creando usuarios de ejemplo
        try {

            String[] valor;
            Alumno nuevos_alumnos;
            Set<Role> permisosar = new HashSet<>();
            Role userRols = rRepository.findByDescripcion(EnumRole.ROLE_USER).get();
            permisosar.add(userRols);   
            List<Brigada> existe;                     
            
        Resource resource = new ClassPathResource("data1.csv");
        FileInputStream f = new FileInputStream(resource.getFile());
        Scanner sc = new Scanner(f);  

        existe = bRepository.findByDescripcion("1A");
        if (existe.isEmpty()){
            bRepository.save(new Brigada("1A"));
            while (sc.hasNext())   
            {  
                valor = sc.next().split(",");
                nuevos_alumnos = aRepository.save( 
                    new Alumno(valor[0]+" "+valor[1]+""+valor[2], valor[3], 
                    bRepository.findByDescripcion("1A").get(0)));
                System.out.print("Nombre: "+valor[0]+" "+valor[1]+" "+valor[2]+" DNI: "+valor[3]+"\n");  
                nuevos_alumnos.setRoles(permisosar);
                aRepository.save(nuevos_alumnos);
            }             
        }

        sc.close();  //closes the scanner 
        }
        catch (IOException ex) {
        }
    }
}