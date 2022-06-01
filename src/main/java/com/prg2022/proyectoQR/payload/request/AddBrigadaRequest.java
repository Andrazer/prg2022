package com.prg2022.proyectoQR.payload.request;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonFormat;


public class AddBrigadaRequest {
    @NotBlank
    @Size(max = 20)
    private String descripcion;

    //@JsonFormat(pattern="d-m-Y")  
    private LocalDate inicio;

    //@JsonFormat(pattern="d-m-Y")
    private LocalDate fin;


    private int grupo; 
    @Size(max = 4)  
    private String letra;   

    public String getDescripcion() { return descripcion; }
    public LocalDate getInicio() { return inicio; }
    public LocalDate getFin() { return fin; }
    public int getGrupo() { return grupo; }
    public String getLetra() { return letra; }
 
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setInicio(LocalDate inicio) { this.inicio = inicio; }
    public void setFin(LocalDate fin) { this.fin = fin; }
    public void setGrupo(int grupo) { this.grupo = grupo; }
    public void setLetra(String letra) { this.letra = letra; }
}
