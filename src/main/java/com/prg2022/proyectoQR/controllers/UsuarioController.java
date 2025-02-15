package com.prg2022.proyectoQR.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import com.prg2022.proyectoQR.Repository.BrigadaRepository;
import com.prg2022.proyectoQR.Repository.MovimientoRepository;
import com.prg2022.proyectoQR.Repository.RoleRepository;
import com.prg2022.proyectoQR.Repository.UsuarioRepository;
import com.prg2022.proyectoQR.modelos.Brigada;
import com.prg2022.proyectoQR.modelos.EnumRole;
import com.prg2022.proyectoQR.modelos.Estados;
import com.prg2022.proyectoQR.modelos.Movimiento;
import com.prg2022.proyectoQR.modelos.Role;
import com.prg2022.proyectoQR.modelos.Usuario;
import com.prg2022.proyectoQR.payload.request.AddUsuarioRequest;
import com.prg2022.proyectoQR.payload.response.MessageResponse;
import com.prg2022.proyectoQR.security.checkPasswd;
import com.prg2022.proyectoQR.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.prg2022.proyectoQR.payload.request.UploadFileRequest;

@Controller
@RequestMapping("/usuarios")

public class UsuarioController {
    @Autowired
    private UsuarioRepository urepository;
    @Autowired
    private BrigadaRepository brepository;
    @Autowired
    private MovimientoRepository mrepository;
    @Autowired
    private RoleRepository rrepository;    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/add")
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> registerUser(@Valid @RequestBody AddUsuarioRequest addusuariorequest) {
      if (!urepository.findByDni(addusuariorequest.getDni()).isEmpty()) {
        return ResponseEntity.badRequest().body(new MessageResponse("Error: Esa usuario ya existe"));
      }
      //urepository.save(new Usuario(addusuariorequest.getDescripcion()));
      return ResponseEntity.ok(new MessageResponse("Usuario registrado!"));
    }

    @GetMapping("/detalle/{id}")
    public ModelAndView showUser(@PathVariable Long id, ModelAndView modelAndView) {
        boolean enespera = false;
        boolean registrable = false;
        Usuario usuario = urepository.getById(id);
        List<Movimiento> movimientos = mrepository.findTop10ByUsuarioOrderByIdDesc(usuario);
        //List<Usuario> usuarios = urepository.findByBrigada(brigada);
        UploadFileRequest subeFoto = new UploadFileRequest();
        modelAndView.addObject("movimientos", movimientos);
        modelAndView.addObject("usuario", usuario);
        modelAndView.addObject("id", id);
        modelAndView.addObject("estados", Estados.values());
        modelAndView.setViewName("usuario_detail");
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
        return modelAndView;
    }

    @DeleteMapping(value = "/del/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> Borrar(@PathVariable Long id) {
      Usuario aborrar = urepository.getById(id);
      if (aborrar.getId()>1){
        List<Movimiento> movida = mrepository.findByUsuario(aborrar);
        mrepository.deleteAll(movida);
        urepository.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
      }
      return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/roles/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestParam(name="rol",required=false) String rol){
      Usuario rolear = urepository.getById(id);
      if (rolear.getId()>1){
            Set<Role> permisosar = new HashSet<>();
            if (rol!=null
            ){
              Role userRols = rrepository.findByDescripcion(EnumRole.valueOf(rol)).get();
              if (rrepository.count()>0){
                permisosar.add(userRols);
                rolear.setRoles(permisosar);    
              }
            } else {
              rolear.setRoles(null);
            }
            urepository.save(rolear);
        return new ResponseEntity<>("ok", HttpStatus.OK);
      }
      return new ResponseEntity<>("no", HttpStatus.NOT_FOUND);
    }
 

    @PostMapping(value = "/update/{id}")
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@PathVariable Long id, 
                                @RequestParam("nombre") String nombre,
                                @RequestParam("dni") String dni,
                                @RequestParam("ape") String ape,
                                @RequestParam("ape2") String ape2,
                                @RequestParam("numero") int numero,
                                @RequestParam("rancho") int rancho
                                ) {
      Usuario actual = urepository.getById(id);
      if ( (nombre!=actual.getNombre()) && (nombre!="") ) { actual.setNombre(nombre);}
      if ( (dni!=actual.getDni()) && (dni!="") ) { actual.setDni(dni);}
      if ( (ape!=actual.getApellido1()) && (ape!="") ) { actual.setApellido1(ape);}
      if ( (ape2!=actual.getApellido2()) && (ape2!="") ) { actual.setApellido2(ape2);}
      if ( (numero!=actual.getNumero()) && (numero>0) ) { actual.setNumero(numero);}
      if ( (rancho!=actual.getRancho()) && (rancho>0) ) { actual.setRancho(rancho);}
      urepository.save(actual);

      /*
      if (actual.getId()>1){
        if ( (actual.getDescripcion()!=Upd.getDescripcion()) && (
          Upd.getDescripcion().length()>0
        )){
          actual.setDescripcion(Upd.getDescripcion());
        }          
        urepository.save(actual);*/
        return new ResponseEntity<>("ok", HttpStatus.OK);
      /* }
      return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);*/
    }    

    @PostMapping(value = "/cambiapass")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> pwd(
      @RequestParam("passwd") String passwd, 
      @RequestParam("oldpasswd") String oldpasswd) {
      //comprobar dificultad de la contraseña >8, letras, numeros, digito
      if (!checkPasswd.check(passwd)){
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
      }
      //obtener usuario actual logeado
      UserDetailsImpl userDetails = 
        (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      
      //comprueba clave anterior
      Usuario usrActual = urepository.getById(userDetails.getId());
      if (!passwordEncoder.matches(oldpasswd, usrActual.getClave())){
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
      }

        usrActual.setClave(passwordEncoder.encode(passwd));  
        urepository.save(usrActual);
      return new ResponseEntity<>("", HttpStatus.OK);
    }        

    @PostMapping(value = "/addfull/{id}")
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addfull(@PathVariable Long id, 
                                @RequestParam("nombre") String nombre,
                                @RequestParam("dni") String dni,
                                @RequestParam("ape") String ape,
                                @RequestParam("ape2") String ape2,
                                @RequestParam("numero") int numero,
                                @RequestParam("rancho") int rancho
                                ) {
      Optional<Brigada> buscabrigada = brepository.findById(id);
      if (buscabrigada.isEmpty()){
        return new ResponseEntity<>("no", HttpStatus.BAD_REQUEST);
      }
      Brigada brigada = buscabrigada.get();
      Usuario usuario = urepository.save( 
        new Usuario(nombre,ape,ape2, dni, 
        brigada,
        rancho,
        numero,
        brigada.getGrupo(),
        brigada.getLetra()));

      return new ResponseEntity<>(usuario.getNombre(), HttpStatus.OK);
    }    


}
