package com.prg2022.proyectoQR.addons.numeroBrigada;

public class generador {
    private int rancho;
    private int numero;
    private int grupo;   
    private String letra;  
    private int limite_rancho;

    public generador(int brigada, String letra){
        this.rancho=1;
        this.numero=1;
        this.grupo=brigada;
        this.letra=letra;
        this.limite_rancho=9;
    }
    public generador(int brigada, String letra, int limiteRancho){
        this.rancho=1;
        this.numero=1;
        this.grupo=brigada;
        this.letra=letra;
        this.limite_rancho=limiteRancho;
    }

    public Object[] getRancho(){
        Object[] ranchos = new Object[4]; 
        ranchos[0] = Integer.valueOf(this.rancho); 
        ranchos[1] = Integer.valueOf(this.numero); 
        ranchos[2] = Integer.valueOf(this.grupo); 
        ranchos[3] = this.letra; 
        if (numero==limite_rancho){
            rancho++;
            numero=1;
        } else {
            numero++;
        }
        return ranchos;
    }
    
}
