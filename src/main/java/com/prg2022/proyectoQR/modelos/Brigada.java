package com.prg2022.proyectoQR.modelos;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity

@Table(name = "brigada")

public class Brigada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String descripcion;   
    
    private LocalDate inicio;
    private LocalDate fin;
    private LocalTime RF;
    @JsonFormat(pattern="dd-MM-yy HH:mm")
    private Date creada;
    @JsonFormat(pattern="dd-MM-yy HH:mm")
    private Date actualizada;
  
    @PrePersist
    protected void onCreate() {
      creada = new Date();
    }
  
    @PreUpdate
    protected void onUpdate() {
      actualizada = new Date();
    }

    @Column(name = "activa", nullable = false)
    private boolean activa = true;

    @OneToMany(mappedBy = "brigada", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
      private Set<Alumno> alumno;

    public Brigada() {
    }

    public Brigada(String descripcion) {
        this.descripcion = descripcion;
      }

    public String getDescripcion() { return descripcion; }
    public LocalDate getInicio() { return inicio; }
    public LocalDate getFin() { return fin; }
    public LocalTime getRF() { return RF; }
    public Long getId() { return id; }
    public boolean getActiva() { return activa; }
    public Date getCreada() { return creada; }
    public Date getActualizada() { return actualizada; }


    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }      
    public void setInicio(LocalDate inicio) { this.inicio = inicio; } 
    public void setFin(LocalDate fin) { this.fin = fin; } 
    public void setRF(LocalTime RF) { this.RF = RF; } 
    public void setActive(boolean activa) { this.activa = activa; } 
    
}
