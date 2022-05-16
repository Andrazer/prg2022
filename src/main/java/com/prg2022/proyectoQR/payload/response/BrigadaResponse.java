package com.prg2022.proyectoQR.payload.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BrigadaResponse {
    private Long id;
    private String descripcion;   
    //private LocalDate inicio;
    //private LocalDate fin;
    //private LocalTime RF;
    @JsonFormat(pattern="dd-MM-yy HH:mm")
    private Date creada;
    @JsonFormat(pattern="dd-MM-yy HH:mm")
    private Date actualizada;    
    
	public BrigadaResponse(Long id, String descripcion, Date creada, Date actualizada) {
		this.id = id;
		this.descripcion = descripcion;
		this.creada = creada;
        this.actualizada = actualizada;
	}

	public Long getId() { return id; }
	public String getDescripcion() { return descripcion; }
    public Date getCreada() { return creada; }
    public Date getActualizada() { return actualizada; }

    public void setId(Long id) { this.id=id; }
	public void setDescripcion(String descripcion) { this.descripcion= descripcion; }
    public void setCreada(Date creada) { this.creada= creada; }
    public void setActualizada(Date actualizada) { this.actualizada= actualizada; }


}