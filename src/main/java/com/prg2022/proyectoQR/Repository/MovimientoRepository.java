package com.prg2022.proyectoQR.Repository;

import com.prg2022.proyectoQR.modelos.Movimiento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientoRepository  extends JpaRepository<Movimiento, Long>{

    Long countByIdGreaterThan(Long id);

    
}
