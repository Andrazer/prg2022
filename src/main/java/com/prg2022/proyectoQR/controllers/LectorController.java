package com.prg2022.proyectoQR.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.MediaType;

import com.prg2022.proyectoQR.Repository.MovimientoRepository;
import com.prg2022.proyectoQR.Repository.UsuarioRepository;
import com.prg2022.proyectoQR.addons.cryptos;
import com.prg2022.proyectoQR.modelos.Movimiento;
import com.prg2022.proyectoQR.modelos.Usuario;
import com.prg2022.proyectoQR.services.UserDetailsImpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LectorController {
    
    @Autowired
    private MovimientoRepository mrepository;
    @Autowired
    private UsuarioRepository urepository;

    private void guardaMovimiento(Usuario obtenido, Usuario autorizador){
        mrepository.save(
            new Movimiento(
                !obtenido.getAbordo(),
                LocalDateTime.now (),
                obtenido,
                autorizador)
            );  
        obtenido.setAbordo(!obtenido.getAbordo());
        urepository.save(obtenido);
    }


    @GetMapping("/lector")
    public ModelAndView getLector(ModelAndView modelAndView, Model pagina) {
        pagina.addAttribute("titulo", "Lector QR");
        modelAndView.addObject("pagina", pagina);
        modelAndView.setViewName("lector");
        return modelAndView;
    }

    

    @PostMapping(value="/salidafrancos", produces= MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<Object> leeqr(String[] codigos, Object responseObj) throws Exception {
        UserDetailsImpl userDetails = 
        (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cryptos damecampos= new cryptos();
        Usuario validador = urepository.getById(userDetails.getId());
        int i=0;
        for (String codqr : codigos) {
            guardaMovimiento(
                urepository.getById(
                    Long.parseLong(
                        damecampos.decrypt(codqr)
                        )
                    ),
                validador
                );
            i++;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", i);
        return ResponseEntity.ok(map);
    }

    @PostMapping(value="/resultadoControl", produces= MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<Object> leeqr(Boolean modo, String codigoqr, Object responseObj) throws Exception {
        cryptos damecampos= new cryptos();
        long mistring= Long.parseLong(damecampos.decrypt(codigoqr));

        Optional<Usuario> usuario = urepository.findById(mistring);
        if (usuario.isEmpty()){
            return new ResponseEntity<>("NO", HttpStatus.NOT_FOUND);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Usuario obtenido = usuario.get();

        if (!modo){
            UserDetailsImpl userDetails = 
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                guardaMovimiento(obtenido,urepository.getById(userDetails.getId()));
            /*mrepository.save(
                new Movimiento(
                    !obtenido.getAbordo(),
                    LocalDateTime.now (),
                    obtenido,
                    urepository.getById(userDetails.getId()))
                );  
            obtenido.setAbordo(!obtenido.getAbordo());
            urepository.save(obtenido);*/
        }
        map.put("nombre", obtenido.getNombreCompleto());
        map.put("abordo", obtenido.getAbordo());
        map.put("numero", obtenido.getNumeroDeBrigada());
        map.put("brigada", obtenido.getGrupo()+obtenido.getLetra());
        map.put("foto", obtenido.getFoto());
        return ResponseEntity.ok(map);
    }
/* 
    @PostMapping("/resultadoLec")
    public ModelAndView metodo2(ModelAndView mimodelo, String result) throws Exception {
        cryptos damecampos= new cryptos();
        long mistring= Long.parseLong(damecampos.decrypt(result));

        Usuario usuario = urepository.getById(mistring);
        List<Movimiento> movimientos = mrepository.findTop10ByUsuario(usuario);

        mimodelo.addObject("movimientos", movimientos);
        mimodelo.addObject("usuario", usuario);
        mimodelo.addObject("id", mistring);
        mimodelo.setViewName("usuario_detail");
        return mimodelo;
    }*/

    

    /*@GetMapping("/resultadoLec")
    public String metodo2(Model mimodelo,String result) throws Exception {
        cryptos damecampos= new cryptos();

        String mistring= damecampos.decrypt(result);

        mimodelo.addAttribute("resultadoQR", mistring);
        return "resultadoLector";
    }*/


}
