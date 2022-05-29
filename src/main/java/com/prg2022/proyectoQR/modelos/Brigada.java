package com.prg2022.proyectoQR.modelos;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
/*
* CLASE: BRIGADA -> BD-TABLA:BRIGADAS
* TABLA QUE CONTIENE LOS DATOS DE LA BRIGADA. 
* CONTENEDOR BASE DE CUALQUIER USUARIO.
*/

@Entity

@Table(name = "brigadas")

public class Brigada {
    //id autogenerado
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //descripción en texto de la brigada
    @NotBlank
    @Size(max = 20)
    private String descripcion;   
    //inicio del curso
    private LocalDate inicio;
    //fin del curso
    private LocalDate fin;
    //regreso de francos
    private LocalTime RF;
    //marca para borrado, al marcar se almacena tb fecha para borrado
    @Column(name = "activa", nullable = false)
    private boolean activa = true;
    private Date borrar;
    //también necesitamos el número de brigada y la letra que lo identifica
    //de esta forma al importar usuarios es más fácil generar los número de brigada
    @Column(columnDefinition = "TINYINT")
    private int grupo; 
    @Size(max = 4)  
    private String letra;   
    //campos autogenerados (on crate, on update)
    @JsonFormat(pattern="dd-MM-yy HH:mm")
    private Date creada;
    @JsonFormat(pattern="dd-MM-yy HH:mm")
    private Date actualizada;
    @PrePersist
    protected void onCreate() {
      creada = new Date();
      actualizada = new Date();
    }
    @PreUpdate
    protected void onUpdate() {
      actualizada = new Date();
    }
    //Relación con usuarios del sistema
    @OneToMany(mappedBy = "brigada", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Usuario> usuario;

    public Brigada() {}
    public Brigada(String descripcion) { this.descripcion = descripcion; }
    public Brigada(String descripcion, LocalDate inicio, LocalDate fin, int grupo, String letra) { 
      this.descripcion = descripcion; 
      this.inicio = inicio;
      this.fin = fin;
      this.grupo = grupo;
      this.letra = letra;
    }

    public String getDescripcion() { return descripcion; }
    public LocalDate getInicio() { return inicio; }
    public LocalDate getFin() { return fin; }
    public LocalTime getRF() { return RF; }
    public Long getId() { return id; }
    public boolean getActiva() { return activa; }
    public Date getCreada() { return creada; }
    public Date getActualizada() { return actualizada; }
    public int getGrupo() { return grupo; }
    public String getLetra() { return letra; }


    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }      
    public void setInicio(LocalDate inicio) { this.inicio = inicio; } 
    public void setFin(LocalDate fin) { this.fin = fin; } 
    public void setRF(LocalTime RF) { this.RF = RF; } 
    public void setActive(boolean activa) { 
      if (!activa){
        this.borrar=new Date();
      } else {
        this.borrar=null;
      }
      this.activa = activa;
    } 
    public void setGrupo(int grupo) { this.grupo = grupo; }
    public void setLetra(String letra) { this.letra = letra; }
    
}
