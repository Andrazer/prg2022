package com.prg2022.proyectoQR.controllers;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.prg2022.proyectoQR.modelos.EnumRole;
import com.prg2022.proyectoQR.modelos.Role;
import com.prg2022.proyectoQR.modelos.Usuario;

import com.prg2022.proyectoQR.payload.request.LoginRequest;
import com.prg2022.proyectoQR.payload.request.SignupRequest;
import com.prg2022.proyectoQR.payload.response.UserInfoResponse;
import com.prg2022.proyectoQR.payload.response.MessageResponse;

import com.prg2022.proyectoQR.Repository.RoleRepository;
import com.prg2022.proyectoQR.Repository.UsuarioRepository;
import com.prg2022.proyectoQR.addons.jwt.JwtUtils;
import com.prg2022.proyectoQR.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;
  @Autowired
  UsuarioRepository userRepository;
  @Autowired
  RoleRepository roleRepository;
  @Autowired
  PasswordEncoder encoder;
  @Autowired
  JwtUtils jwtUtils;
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
        .body(new UserInfoResponse(userDetails.getId(),
                                   userDetails.getUsername(),
                                   roles));
  }/* 
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByDni(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: EL DNI ya está registrado!"));
    }
    // Create new user's account
    Usuario user = new Usuario(signUpRequest.getUsername(),
                         encoder.encode(signUpRequest.getPassword()),
                         signUpRequest.getBrigada());
    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();
    if (strRoles == null) {
      Role userRole = roleRepository.findByDescripcion(EnumRole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Roles."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByDescripcion(EnumRole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Roles."));
          roles.add(adminRole);
          break;
        case "mod":
          Role modRole = roleRepository.findByDescripcion(EnumRole.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error: Roles."));
          roles.add(modRole);
          break;
        default:
          Role userRole = roleRepository.findByDescripcion(EnumRole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Roles."));
          roles.add(userRole);
        }
      });
    }
    user.setRoles(roles);
    userRepository.save(user);
    return ResponseEntity.ok(new MessageResponse("Usuario registrado!"));
  }*/
  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new MessageResponse("Salida correcta!"));
  }
}