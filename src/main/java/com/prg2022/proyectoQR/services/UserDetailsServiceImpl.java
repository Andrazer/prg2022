package com.prg2022.proyectoQR.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.prg2022.proyectoQR.modelos.Alumno;
import com.prg2022.proyectoQR.Repository.AlumnoRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  AlumnoRepository alumnoRepository;
  @Override
  @Transactional
  public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {
    Alumno alumno = alumnoRepository.findByDni(dni)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + dni));
    return UserDetailsImpl.build(alumno);
  }
}