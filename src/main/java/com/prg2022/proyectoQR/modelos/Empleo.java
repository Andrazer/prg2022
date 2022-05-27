package com.prg2022.proyectoQR.modelos;

public enum Empleo {
Coronel("COL"){ public String toString() {return "Coronel";}}, 
CapitánNavio("CN"){ public String toString() {return "Capitán de Navio";}},  
TenienteCoronel("TCOL"){ public String toString() {return "Teniente Coronel";}},  
CapitánFragata("CF"){ public String toString() {return "Capitán de Fragata";}},  
Comandante("CTE"){ public String toString() {return "Comandante";}}, 
CapitánCorbeta("CC"){ public String toString() {return "Capitán de Corbeta";}}, 
Capitán("CAP"){ public String toString() {return "Capitán";}}, 
TenienteNavio("TN"){ public String toString() {return "Teniente de Navio";}}, 
Teniente("TTE"){ public String toString() {return "Teniente";}}, 
AlférezNavio("AN"){ public String toString() {return "Alférez de Navio";}},  
Alférez("ALF"){ public String toString() {return "Alférez";}}, 
AlférezFragata("AF"){ public String toString() {return "Alférez de Fragata";}},  
SuboficialMayor("SBMY"){ public String toString() {return "Suboficial Mayor";}},  
Subteniente("STTE"){ public String toString() {return "Subteniente";}}, 
Brigada("BG"){ public String toString() {return "Brigada";}}, 
SargentoPrimero("SGT1"){ public String toString() {return "Sargento Primero";}}, 
Sargento("SGTO"){ public String toString() {return "Sargento";}},  
CaboMayor("CBMY"){ public String toString() {return "Cabo Mayor";}},  
CaboPrimero("CB1"){ public String toString() {return "Cabo Primero";}},  
Cabo("CBO"){ public String toString() {return "Cabo";}}, 
Soldado("SDO"){ public String toString() {return "Soldado";}},  
Marinero("MRO"){ public String toString() {return "Marinero";}}, 
SargentoAlumno("SGTO AL"){ public String toString() {return "Sargento Alumno";}}, 
AlumnoSegundo("AL.2º"){ public String toString() {return "Alumno de 2º";}}, 
AlumnoPrimero("AL.1º"){ public String toString() {return "Alumno de 1º";}}, 
AspCaboPrimero("ASP.CB1º"){ public String toString() {return "Asp. CB1º";}}, 
AspCabo("ASP.CBO"){ public String toString() {return "Asp. Cabo";}}, 
AspMarinero("ASP.MRO"){ public String toString() {return "Asp. Mro.";}}, 
None("-"){ public String toString() {return "";}};


    private String empleo;

    private Empleo(String empleo) {
        this.empleo = empleo;
    }

    public String getEmpleo() {
        return empleo;
    }  
}