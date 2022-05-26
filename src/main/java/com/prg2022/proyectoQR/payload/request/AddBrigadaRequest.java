package com.prg2022.proyectoQR.payload.request;

import javax.validation.constraints.*;


public class AddBrigadaRequest {
    @NotBlank
    @Size(min = 2, max = 20)
    private String descripcion;  

    public String getDescripcion() {
        return descripcion;
    }
 
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
