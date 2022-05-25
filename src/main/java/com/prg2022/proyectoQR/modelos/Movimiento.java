package com.prg2022.proyectoQR.modelos;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

@Entity

@Table(name = "movimientos")

public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    

    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    private boolean aBordo;

    @JsonFormat(pattern="dd-MM-yy HH:mm")
    @Column(name = "cuando", nullable = false)
    private LocalDateTime cuando;

    @ManyToOne(targetEntity=Usuario.class) 
    @JoinColumn(name="usuario_id")
    private Usuario usuario; 

    @ManyToOne(targetEntity=Usuario.class) 
    @JoinColumn(name="autorizador_id")
    private Usuario autorizador; 

    public Movimiento(){

    }

    public Movimiento(boolean aBordo, LocalDateTime cuando, Usuario usuario, Usuario autorizador) {
        this.aBordo = aBordo;
        this.cuando = cuando;
        this.usuario = usuario;
        this.autorizador = autorizador;
    }

    public LocalDateTime getCuando() { return cuando; }
    public Boolean getAbordo() { return aBordo; }

}
