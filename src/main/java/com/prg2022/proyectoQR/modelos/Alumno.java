package com.prg2022.proyectoQR.modelos;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;




@Entity
@Table(name = "alumno")

public class Alumno implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String nombre;     

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brigada_id", nullable = false)
    private Brigada brigada;  

    /*@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "roles_id", nullable = false)
    private Roles roles; */ 

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", 
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    
    @NotBlank
    @NotNull
    @Column(unique=true)
    private String dni;

    @Size(max = 120)


    private String clave;

    public Alumno() {
    }

    public Alumno(String nombre, String dni, Brigada brigada) {
        this.nombre = nombre;
        this.dni = dni;
        this.brigada = brigada;
    }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave;}

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni;}

    public Long getId() { return id; }

    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }

    public Brigada getBrigada() { return brigada; }
    public void setBrigada(Brigada brigada) { this.brigada = brigada;}

}
