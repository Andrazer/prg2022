package com.prg2022.proyectoQR;



import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;



import com.prg2022.proyectoQR.Repository.UsuarioRepository;
import com.prg2022.proyectoQR.Repository.BrigadaRepository;
import com.prg2022.proyectoQR.Repository.MovimientoRepository;
import com.prg2022.proyectoQR.Repository.RoleRepository;
import com.prg2022.proyectoQR.modelos.Usuario;
import com.prg2022.proyectoQR.modelos.Brigada;
import com.prg2022.proyectoQR.modelos.EnumRole;
import com.prg2022.proyectoQR.modelos.Movimiento;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoField;

@Component
public class DataLoader implements ApplicationRunner {



    private UsuarioRepository aRepository;
    private BrigadaRepository bRepository;
    private RoleRepository rRepository;
    private MovimientoRepository mrepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    public DataLoader(UsuarioRepository aRepository, BrigadaRepository bRepository, 
                        RoleRepository rRepository, MovimientoRepository mrepository) {
        this.aRepository = aRepository;
        this.bRepository = bRepository;
        this.rRepository = rRepository;
        this.mrepository = mrepository;
    }




    @Override
    public void run(ApplicationArguments args) throws Exception {

        Optional<Usuario> alumno = aRepository.findByDni("Admin");
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
            
            Usuario agregado = aRepository.save(
                    new Usuario(
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
            List<Brigada> existe;                     
            existe = bRepository.findByDescripcion("1A");
        if (existe.isEmpty()){
            String[] valor;
            Usuario nuevos_alumnos;
            Set<Role> permisosar = new HashSet<>();
            Role userRols = rRepository.findByDescripcion(EnumRole.ROLE_USER).get();
            permisosar.add(userRols);            
            Resource resource = new ClassPathResource("data1.csv");
            FileInputStream f = new FileInputStream(resource.getFile());
            Scanner sc = new Scanner(f);             
            bRepository.save(new Brigada("1A"));
            while (sc.hasNext())   
            {  
                valor = sc.next().split(",");
                nuevos_alumnos = aRepository.save( 
                    new Usuario(valor[0]+" "+valor[1]+" "+valor[2], valor[3], 
                    bRepository.findByDescripcion("1A").get(0)));
                nuevos_alumnos.setRoles(permisosar);
                aRepository.save(nuevos_alumnos);
            }             
            sc.close();  //closes the scanner 
        }
        

        

        //ver si hay movimiento
        if (mrepository.countByIdGreaterThan(Long.valueOf(1))<1) {
            List<Usuario> UsuariosNuevos = aRepository.findByBrigada(bRepository.findByDescripcion("1A").get(0));
            for (int i=0;i<UsuariosNuevos.size();i++) {
        
                System.out.println(UsuariosNuevos.get(i).getId());
                Usuario autorize = aRepository.getById(Long.valueOf(1));
                

                for(int j=1;j<=YearMonth.of(2022, 04).lengthOfMonth();j++){
                    LocalDate dia = LocalDate.parse("2022-04-"+String.format("%1$02d",j));
                    switch(dia.get(ChronoField.DAY_OF_WEEK)){
                        case 7: break;
                        case 6: break;
                        default:
                        //de luneas a viernes, llegan por la mañana y se van por la tarde
                        // llegan entre las 07:00 y las 07:45
                        //se marchan entre las 14:00 y las 14:59
                        LocalDateTime llega = dia.atTime(07,(int) (Math.random() * 45));
                        LocalDateTime ssale = dia.atTime(14,(int) (Math.random() * 59));
                        mrepository.save(new Movimiento(true,llega,UsuariosNuevos.get(i),autorize));
                        mrepository.save(new Movimiento(false,ssale,UsuariosNuevos.get(i),autorize));
                        //System.out.println(llega);
                        //System.out.println(ssale);
                        //#SET ABORDO
                            break;
                    }          
                }
            }


          }




        }
        catch (IOException ex) {
        }
    }
}