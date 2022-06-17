package com.prg2022.proyectoQR.modelos;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity

@Table(name = "autoprizaciones")

public class Autorizaciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    

    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    private boolean autorizado;

    @JsonFormat(pattern="dd-MM-yy HH:mm")
    @Column(name = "inicio", nullable = false)
    private LocalDateTime inicio;

    @JsonFormat(pattern="dd-MM-yy HH:mm")
    @Column(name = "fin")
    private LocalDateTime fin;    

    @ManyToOne(targetEntity=Usuario.class) 
    @JoinColumn(name="usuario_id")
    private Usuario usuario; 

    @ManyToOne(targetEntity=Usuario.class) 
    @JoinColumn(name="autorizador_id")
    private Usuario autorizador; 

    @Column(name = "estado")
    private Estados estado;    
    
    public Autorizaciones(){

    }

    public Autorizaciones(
            boolean autorizado, 
            LocalDateTime inicio, 
            LocalDateTime fin, 
            Usuario usuario, 
            Estados estado) {
        this.autorizado = autorizado;
        this.inicio = inicio;
        this.fin = fin;
        this.usuario = usuario;
        //this.autorizador = autorizador;
        this.estado = estado;
    }
}
