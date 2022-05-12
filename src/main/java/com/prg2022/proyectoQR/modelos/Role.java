package com.prg2022.proyectoQR.modelos;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private EnumRole descripcion;
  public Role() {
  }
  public Role(EnumRole descripcion) {
    this.descripcion = descripcion;
  }
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public EnumRole getDescripcion() {
    return descripcion;
  }
  public void setDescripcion(EnumRole descripcion) {
    this.descripcion = descripcion;
  }
}