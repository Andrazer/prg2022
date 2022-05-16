package com.prg2022.proyectoQR.controllers;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.prg2022.proyectoQR.Repository.BrigadaRepository;
import com.prg2022.proyectoQR.modelos.Brigada;
import com.prg2022.proyectoQR.payload.request.AddBrigadaRequest;
import com.prg2022.proyectoQR.payload.response.MessageResponse;

@Controller
@RequestMapping("/brigada")
public class BrigadaController {
    @Autowired
    private BrigadaRepository brepository;

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
    public ResponseEntity<?> registerUser(@Valid @RequestBody AddBrigadaRequest addbrigadarequest) {
      if (!brepository.findByDescripcion(addbrigadarequest.getDescripcion()).isEmpty()) {
        return ResponseEntity.badRequest().body(new MessageResponse("Error: Esa Brigada ya existe"));
      }
      brepository.save(new Brigada(addbrigadarequest.getDescripcion()));
      // Create new user's account
      /*
      Alumno user = new Alumno(signUpRequest.getUsername(),
                           encoder.encode(signUpRequest.getPassword()),
                           signUpRequest.getBrigada());
      Set<String> strRoles = signUpRequest.getRole();
      Set<Role> roles = new HashSet<>();
      if (strRoles == null) {
        Role userRole = roleRepository.findByDescripcion(EnumRole.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
      } else {
        strRoles.forEach(role -> {
          switch (role) {
          case "admin":
            Role adminRole = roleRepository.findByDescripcion(EnumRole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
            break;
          case "mod":
            Role modRole = roleRepository.findByDescripcion(EnumRole.ROLE_MODERATOR)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(modRole);
            break;
          default:
            Role userRole = roleRepository.findByDescripcion(EnumRole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
          }
        });
      }
      user.setRoles(roles);
      userRepository.save(user);*/
      return ResponseEntity.ok(new MessageResponse("Brigada registrada!"));
    }
}

