package com.prg2022.proyectoQR.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.prg2022.proyectoQR.modelos.Usuario;
import com.prg2022.proyectoQR.Repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UsuarioRepository aRepository;
  @Override
  @Transactional
  public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {
    Usuario alumno = aRepository.findByDni(dni)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + dni));
    return UserDetailsImpl.build(alumno);
  }
}