package com.prg2022.proyectoQR.modelos;

import java.io.Serializable;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.HashSet;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;




@Entity
@Table(name = "usuarios")

public class Usuario implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 60)
    private String nombre;     

    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    private boolean Abordo;

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

    @Column(columnDefinition = "TINYINT")
        private int rancho;
    @Column(columnDefinition = "TINYINT")
        private int numero;
    @Column(columnDefinition = "TINYINT")
        private int grupo;   
    private String letra;     
    


    public Usuario() {
    }

    public Usuario(String nombre, String dni, Brigada brigada) {
        this.nombre = nombre;
        this.dni = dni;
        this.brigada = brigada;
        this.Abordo = false;
    }

    public Usuario(String nombre, String dni, Brigada brigada,int rancho,  int numero, int grupo, String letra) {
        this.nombre = nombre;
        this.dni = dni;
        this.brigada = brigada;
        this.Abordo = false;
        this.rancho=rancho;
        this.numero=numero;
        this.grupo=grupo;   
        this.letra=letra;  
    }

    public String getNumBrigada(){
        return this.rancho+""+this.numero+""+this.grupo+""+this.letra;
    }
    public void setNumBrigada(int rancho,  int numero, int grupo, String letra){
        this.rancho=rancho;
        this.numero=numero;
        this.grupo=grupo;   
        this.letra=letra;            
    }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave;}

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni;}

    public Long getId() { return id; }

    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }

    public Brigada getBrigada() { return brigada; }
    public String getBrigadaDescripcion() { return brigada.getDescripcion();}
    public Long getBrigadaId() { return brigada.getId();}
    public void setBrigada(Brigada brigada) { this.brigada = brigada;}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre;}    

    public Boolean getAbordo() { return Abordo; }
    public void setAbordo(Boolean Abordo) { this.Abordo = Abordo;}    
    
    public String getNombreCorto() { 
        StringTokenizer cortando = new StringTokenizer(this.nombre);
        String inicial = cortando.nextToken();
        return cortando.nextToken()+" "+cortando.nextToken()+", "+inicial.substring(0,1)+"."; 
    }

}
