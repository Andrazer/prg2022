package com.prg2022.proyectoQR.security;

public class checkPasswd {
    public static boolean check(String password) {
        boolean flagUppercase = false;
        boolean flagLowercase = false;
        boolean flagDigit = false;
        boolean flagChar = false;
    
        if (password.length() >= 8) {
            if( password.matches("(?=.*[0-9]).*") )
                flagDigit = true;
            if( password.matches("(?=.*[a-z]).*") )
                flagLowercase = true;
            if( password.matches("(?=.*[A-Z]).*") )
                flagUppercase = true;  
            if( password.matches("(?=.*[~!@#$%^&*()_-]).*") )
                flagChar = true; 
        }
        if (flagDigit && flagUppercase && flagLowercase && flagChar) {
            return true;
        } 
        return false;
    }
}


