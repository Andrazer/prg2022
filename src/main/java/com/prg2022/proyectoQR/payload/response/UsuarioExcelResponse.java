package com.prg2022.proyectoQR.payload.response;

public class UsuarioExcelResponse {
    private String nombre;
	private String apellido1;
    private String apellido2;
    private String dni;
    private int rancho;
    private int numero;

    public UsuarioExcelResponse(String nombre, String apellido1, String apellido2, String dni, int rancho, int numero) {
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
        this.dni = dni;
        this.rancho = rancho;
        this.numero = numero;
	}
    public String getNombre() { return nombre; }
    public String getApellido1() { return apellido1; }
    public String getApellido2() { return apellido2; }
    public String getDni() { return dni; }
    public int getRancho() { return rancho; }
    public int getNumero() { return numero; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido1(String apellido1) { this.apellido1 = apellido1; }
    public void setApellido2(String apellido2) { this.apellido2 = apellido2; }
    public void setDni(String dni) { this.dni = dni; } 
    public void setRancho(int rancho) { this.rancho = rancho; }
    public void setNumero(int numero) { this.numero = numero; }
    
    public String datos(){
        return this.nombre+" "+this.apellido1+" "+this.apellido2+" "+this.dni;
    }
}
