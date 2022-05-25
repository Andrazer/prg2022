package com.prg2022.proyectoQR.controllers;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.prg2022.proyectoQR.Repository.UsuarioRepository;
import com.prg2022.proyectoQR.Repository.BrigadaRepository;
import com.prg2022.proyectoQR.modelos.Usuario;
import com.prg2022.proyectoQR.modelos.Brigada;
import com.prg2022.proyectoQR.payload.request.AddBrigadaRequest;
import com.prg2022.proyectoQR.payload.request.UploadFileRequest;
import com.prg2022.proyectoQR.payload.response.MessageResponse;

import com.prg2022.proyectoQR.payload.response.BrigadaResponse;

@Controller
@RequestMapping("/brigada")
public class BrigadaController {
    @Autowired
    private BrigadaRepository brepository;
    @Autowired
    private UsuarioRepository urepository;

    @GetMapping("")
    public ModelAndView getBrigadas(ModelAndView modelAndView, Model pagina) {
        pagina.addAttribute("titulo", "Brigadas");
        modelAndView.addObject("pagina", pagina);
        modelAndView.setViewName("brigada");
        return modelAndView;
    }

    @GetMapping(value = "/all", produces= MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<Collection<Brigada>> findAll() {
        List<Brigada> brigada = (List<Brigada>) brepository.findAll();
        return ResponseEntity.ok(brigada);
    }
    @PostMapping("/add")
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> registerUser(@Valid @RequestBody AddBrigadaRequest addbrigadarequest) {
      if (!brepository.findByDescripcion(addbrigadarequest.getDescripcion()).isEmpty()) {
        return ResponseEntity.badRequest().body(new MessageResponse("Error: Esa Brigada ya existe"));
      }
      brepository.save(new Brigada(addbrigadarequest.getDescripcion()));
      return ResponseEntity.ok(new MessageResponse("Brigada registrada!"));
    }

    @DeleteMapping(value = "/del/{id}")
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Long> Borrar(@PathVariable Long id) {
      Brigada aborrar = brepository.getById(id);
      if (aborrar.getId()>1){
        aborrar.setActive(!aborrar.getActiva());
        brepository.save(aborrar);
        return new ResponseEntity<>(id, HttpStatus.OK);
      }
      return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/show/{id}")
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public ModelAndView showBrigada(@PathVariable Long id, ModelAndView modelAndView) {
        Brigada brigada = brepository.getById(id);
        List<Usuario> usuarios = urepository.findByBrigada(brigada);
        UploadFileRequest importarArchivo = new UploadFileRequest();
        modelAndView.addObject("usuarios", usuarios);
        modelAndView.addObject("id", id);
        modelAndView.setViewName("brigada_detail");
        modelAndView.addObject("importarArchivo", importarArchivo);
        return modelAndView;
    }

    @GetMapping(value = "/get/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<BrigadaResponse> findById(@PathVariable Long id) {
        Brigada brigada = brepository.getById(id);
        BrigadaResponse respuesta = new BrigadaResponse(id, brigada.getDescripcion(), brigada.getCreada(),brigada.getActualizada());
        return ResponseEntity.ok(respuesta);
    }    
    
}

