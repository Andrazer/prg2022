package com.prg2022.proyectoQR.modelos;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;




@Entity
@Table(name = "usuarios")

public class Usuario implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 60)
    private String nombre;   
    
    @Size(max = 60)
    private String apellido1;    

    @Size(max = 60)
    private String apellido2;      

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

    private String foto;

    

    @Column(columnDefinition = "TINYINT")
        private int rancho;
    @Column(columnDefinition = "TINYINT")
        private int numero;
    @Column(columnDefinition = "TINYINT")
        private int grupo;   
    private String letra;     
    
    private Empleo empleo;
    private Especialidad especialidad;

    @Formula( "CONCAT_WS( '', rancho, numero, grupo, letra ) " )
    private String numeroDeBrigada;

    
    public Usuario() {
    }

    public Usuario(String nombre, String dni, Brigada brigada) {
        this.nombre = nombre;
        this.apellido1 = "";
        this.apellido2 = "";        
        this.dni = dni;
        this.brigada = brigada;
        this.Abordo = false;
        this.empleo = Empleo.None;
        this.especialidad = Especialidad.None;
    }

    public Usuario(String nombre, String apellido1, String apellido2, String dni, Brigada brigada) {
        this.nombre = nombre;
        this.dni = dni;
        this.brigada = brigada;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.Abordo = false;
        this.empleo = Empleo.None;
        this.especialidad = Especialidad.None;        
    }
    
    public Usuario(String nombre, String apellido1, String apellido2, String dni, Brigada brigada,int rancho,  int numero, int grupo, String letra) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;        
        this.dni = dni;
        this.brigada = brigada;
        this.Abordo = false;
        this.rancho=rancho;
        this.numero=numero;
        this.grupo=grupo;   
        this.letra=letra;  
        this.empleo = Empleo.None;
        this.especialidad = Especialidad.None;        
    }

    public Usuario(String nombre, String apellido1, String apellido2, String dni, Brigada brigada,
        int rancho,  int numero, int grupo, String letra, Empleo empleo, Especialidad especialidad) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;        
        this.dni = dni;
        this.brigada = brigada;
        this.Abordo = false;
        this.rancho=rancho;
        this.numero=numero;
        this.grupo=grupo;   
        this.letra=letra;  
        this.empleo = empleo;
        this.especialidad = especialidad;        
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

    public int getRancho() { return rancho; }
    public void setRancho(int rancho) { this.rancho = rancho;}

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero;}

    public Long getId() { return id; }

    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }

    public void setEmpleo(Empleo empleo){ this.empleo = empleo; }
    public void setEspecialidad(Especialidad especialidad){ this.especialidad = especialidad; }




    public Brigada getBrigada() { return brigada; }
    public String getBrigadaDescripcion() { return brigada.getDescripcion();}
    public Long getBrigadaId() { return brigada.getId();}
    public void setBrigada(Brigada brigada) { this.brigada = brigada;}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre;}    

    public String getApellido1() { return apellido1; }
    public void setApellido1(String apellido1) { this.apellido1 = apellido1;}  
    
    public String getApellido2() { return apellido2; }
    public void setApellido2(String apellido2) { this.apellido2 = apellido2;}      

    public Boolean getAbordo() { return Abordo; }
    public void setAbordo(Boolean Abordo) { this.Abordo = Abordo;}    
    
    public String getNombreCorto() { 
        return this.apellido1+" "+this.apellido2+", "+this.nombre.substring(0,1)+"."; 
    }

    public String getNombreCompleto() { 
        return this.nombre+" "+this.apellido1+" "+this.apellido2;
    }    

    public Empleo getEmpleo(){ return this.empleo; }
    public Especialidad getEspecialidad(){ return this.especialidad; }

    public String getFoto(){ 
        if ((this.foto=="") || (this.foto==null)){
            return "default.jpg";
        }
        return this.foto; 
    }
    public void setFoto(String foto){ this.foto = foto; }

    public String getNumeroDeBrigada(){ return this.numeroDeBrigada;}

}
