package com.prg2022.proyectoQR.payload.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BrigadaResponse {
    private Long id;
    private String descripcion;   
    @JsonFormat(pattern="dd-MM-yy HH:mm")
    private Date creada;
    @JsonFormat(pattern="dd-MM-yy HH:mm")
    private Date actualizada;    
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate inicio;
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate fin;
    private int grupo; 
    private String letra;  

    
	public BrigadaResponse(
        Long id, String descripcion, 
        Date creada, Date actualizada,
        LocalDate inicio, LocalDate fin,
        int grupo, String letra) {
		this.id = id;
		this.descripcion = descripcion;
		this.creada = creada;
        this.actualizada = actualizada;
        this.inicio = inicio;
        this.fin = fin;
        this.grupo = grupo;
        this.letra = letra;
	}

	public Long getId() { return id; }
	public String getDescripcion() { return descripcion; }
    public Date getCreada() { return creada; }
    public Date getActualizada() { return actualizada; }
    public LocalDate getInicio() { return inicio; }
    public LocalDate getFin() { return fin; }
    public int getGrupo() { return grupo; }
    public String getLetra() { return letra; }

    public void setId(Long id) { this.id=id; }
	public void setDescripcion(String descripcion) { this.descripcion= descripcion; }
    public void setCreada(Date creada) { this.creada= creada; }
    public void setActualizada(Date actualizada) { this.actualizada= actualizada; }
    public void setInicio(LocalDate inicio) { this.inicio= inicio; }
    public void setFin(LocalDate fin) { this.fin= fin; }
    public void setGrupo(int grupo) { this.grupo= grupo; }
    public void setLetra(String letra) { this.letra= letra; }




}