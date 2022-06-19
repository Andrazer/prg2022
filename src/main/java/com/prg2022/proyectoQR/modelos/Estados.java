package com.prg2022.proyectoQR.modelos;

public enum Estados {
    COMISION("COM"){ public String toString() {return "Comisión";}}, 
    LTPS("LTP"){ public String toString() {return "Limitación Temporal";}}, 
    HOSPITAL("HOS"){ public String toString() {return "Hospitalizado";}}, 
    REBAJADO("REB"){ public String toString() {return "Rebajado";}}, 
    RECMED("REC"){ public String toString() {return "Reconocimiento Médico";}}, 
    VACACIONES("VAC"){ public String toString() {return "Vacaciones";}}, 
    PERMISO("PER"){ public String toString() {return "Permiso";}}, 
    OTRAS("OTR"){ public String toString() {return "Otras situaciones";}};

    private String estados;

    private Estados(String estados) {
        this.estados = estados;
    }

    public String getEstados() {
        return estados;
    }  
}
