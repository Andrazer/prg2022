package com.prg2022.proyectoQR.Repository;

import java.util.List;
import java.util.Optional;


import com.prg2022.proyectoQR.modelos.Brigada;
import com.prg2022.proyectoQR.modelos.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {
  Optional<Usuario> findByDni(String dni);
  Boolean existsByDni(String dni);
  List<Usuario> findByBrigada(Brigada brigada);
  Optional<Usuario> findBynumeroDeBrigadaIs( String text );
  
  @Query(value = "select count(usuarios.id) as total, count(CASE WHEN abordo THEN 1 END ) as Abordo, "+
  "count(CASE WHEN not abordo THEN 0 END ) as Entierra, Brigada_id as brigadaid, brigadas.descripcion as nombre "+
  " from usuarios,brigadas where usuarios.brigada_id=brigadas.id and brigada_id>1 group by brigada_id", nativeQuery = true)
  public List<?> buscajames();
}