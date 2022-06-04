package com.prg2022.proyectoQR.Repository;

import java.util.List;
import java.util.Optional;


import com.prg2022.proyectoQR.modelos.Brigada;
import com.prg2022.proyectoQR.modelos.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
  Optional<Usuario> findByDni(String dni);
  Boolean existsByDni(String dni);
  List<Usuario> findByBrigada(Brigada brigada);
}