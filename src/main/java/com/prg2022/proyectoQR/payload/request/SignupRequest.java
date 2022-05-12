package com.prg2022.proyectoQR.payload.request;


import java.util.Set;

import javax.validation.constraints.*;

import com.prg2022.proyectoQR.modelos.Brigada;
 
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
 
    private Brigada brigada;
    
    private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
  
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRole() {
      return this.role;
    }
    
    public void setRole(Set<String> role) {
      this.role = role;
    }

 
    public Brigada getBrigada() {
        return brigada;
    }
 
    public void setBrigada(Brigada brigada) {
        this.brigada = brigada;
    }
}