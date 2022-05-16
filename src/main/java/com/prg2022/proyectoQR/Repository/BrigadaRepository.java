package com.prg2022.proyectoQR.Repository;


import com.prg2022.proyectoQR.modelos.Brigada;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface BrigadaRepository extends JpaRepository<Brigada, Long>{
    List<Brigada> findByDescripcion(String descripcion);
}
