package com.prg2022.proyectoQR.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AddUsuarioRequest {
    @NotBlank
    @Size(min = 3, max = 60)
    private String nombre;   
    private String dni; 

    public String getNombre() {
        return nombre;
    }
 
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni(){
        return dni;
    }
}
