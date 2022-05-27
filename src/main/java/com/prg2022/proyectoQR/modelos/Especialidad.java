package com.prg2022.proyectoQR.modelos;

public enum Especialidad {
Alojamiento_y_Restauración("ALS"){ public String toString() {return "Alojamiento y Restauración";}},
Aprovisonamiento("APS"){ public String toString() {return "Aprovisonamiento";}},
Máquinas("MQS"){ public String toString() {return "Máquinas";}},
Electricidad("ELS"){ public String toString() {return "Electricidad";}},
Energía_y_Propulsión("EPS"){ public String toString() {return "Energía y Propulsión";}},
Sonarista("SOS"){ public String toString() {return "Sonarista";}},
Electrónico("ERS"){ public String toString() {return "Electrónico";}},
Dirección_de_tiro("DTS"){ public String toString() {return "Dirección de tiro";}},
Sistemas_Tácticos("STS"){ public String toString() {return "Sistemas Tácticos";}},
Operaciones_y_Sistemas("OSS"){ public String toString() {return "Operaciones y Sistemas";}},
Artillería_y_Misiles("AMS"){ public String toString() {return "Artillería y Misiles";}},
Armas_Submarinas("ASS"){ public String toString() {return "Armas Submarinas";}},
Armas("ARS"){ public String toString() {return "Armas";}},
Comunicaciones_y_Sistemas("CSS"){ public String toString() {return "Comunicaciones y Sistemas";}},
Comunicaciones("COS"){ public String toString() {return "Comunicaciones";}},
Tecnologías_de_la_Información("TIS"){ public String toString() {return "Tecnologías de la Información";}},
Administración("ADS"){ public String toString() {return "Administración";}},
Maniobra("MNS"){ public String toString() {return "Maniobra";}},
Comunicaciones_MPTM("COM"){ public String toString() {return "Comunicaciones MPTM";}},
Sonarista_MPTM("SOM"){ public String toString() {return "Sonarista MPTM";}},
Electrónico_MPTM("ERM"){ public String toString() {return "Electrónico MPTM";}},
Dirección_de_tiro_MPTM("DTM"){ public String toString() {return "Dirección de tiro MPTM";}},
Sistemas_Tácticos_MPTM("STM"){ public String toString() {return "Sistemas Tácticos MPTM";}},
Artillería_y_Misiles_MPTM("AMM"){ public String toString() {return "Artillería y Misiles MPTM";}},
Administración_MPTM("ADM"){ public String toString() {return "Administración MPTM";}},
Operaciones_y_Sistemas_MPTM("OSM"){ public String toString() {return "Operaciones y Sistemas MPTM";}},
Electricidad_MPTM("ELM"){ public String toString() {return "Electricidad MPTM";}},
Máquinas_MPTM("MQM"){ public String toString() {return "Máquinas MPTM";}},  
Energía_y_Propulsión_MPTM("EPM"){ public String toString() {return "Energía y Propulsión MPTM";}},
Maniobra_MPTM("MNM"){ public String toString() {return "Maniobra MPTM";}},
Aprovisonamiento_MPTM("APM"){ public String toString() {return "Aprovisonamiento MPTM";}},
Hostelería_MPTM("HAM"){ public String toString() {return "Hostelería MPTM";}},
None("-"){ public String toString() {return "";}};

    

    private String especialidad;

    private Especialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }  
}
