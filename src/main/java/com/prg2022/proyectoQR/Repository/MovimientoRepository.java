package com.prg2022.proyectoQR.Repository;

import java.util.List;

import com.prg2022.proyectoQR.modelos.Movimiento;
import com.prg2022.proyectoQR.modelos.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientoRepository  extends JpaRepository<Movimiento, Long>{

    Long countByIdGreaterThan(Long id);
    List<Movimiento> findTop10ByUsuarioOrderByIdDesc(Usuario usuario);
    List<Movimiento> findByUsuario(Usuario usuario);
    
}
