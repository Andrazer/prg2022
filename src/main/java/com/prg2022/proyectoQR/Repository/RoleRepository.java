package com.prg2022.proyectoQR.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.prg2022.proyectoQR.modelos.EnumRole;
import com.prg2022.proyectoQR.modelos.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByDescripcion(EnumRole descripcion);
}