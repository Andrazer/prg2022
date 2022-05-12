package com.prg2022.proyectoQR.Repository;

import java.util.Optional;

import com.prg2022.proyectoQR.modelos.Alumno;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
  Optional<Alumno> findByDni(String dni);
  Boolean existsByDni(String dni);
}