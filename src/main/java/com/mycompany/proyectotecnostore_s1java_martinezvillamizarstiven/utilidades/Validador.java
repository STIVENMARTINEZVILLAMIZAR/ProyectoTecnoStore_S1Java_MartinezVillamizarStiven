package com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.utilidades;

import java.util.regex.Pattern;

public class Validador {
    
    private static final String REGEX_CORREO = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final int MIN_IDENTIFICACION = 8;
    private static final int MAX_IDENTIFICACION = 20;
    private static final int MIN_NOMBRE = 3;
    private static final int MAX_NOMBRE = 100;
    private static final int MIN_TELEFONO = 10;

    public static boolean validarCorreo(String correo) {
        return correo != null && !correo.trim().isEmpty() && Pattern.matches(REGEX_CORREO, correo);
    }

    public static boolean validarIdentificacion(String identificacion) {
        return identificacion != null && identificacion.length() >= MIN_IDENTIFICACION && 
               identificacion.length() <= MAX_IDENTIFICACION;
    }

    public static boolean validarPrecio(double precio) {
        return precio > 0;
    }

    public static boolean validarStock(int stock) {
        return stock >= 0;
    }

    public static boolean validarNombre(String nombre) {
        return nombre != null && nombre.trim().length() >= MIN_NOMBRE && 
               nombre.trim().length() <= MAX_NOMBRE;
    }

    public static boolean validarTelefono(String telefono) {
        return telefono != null && telefono.length() >= MIN_TELEFONO && 
               telefono.matches("\\d+");
    }

    public static boolean validarOpcion(int opcion, int minima, int maxima) {
        return opcion >= minima && opcion <= maxima;
    }

    public static boolean validarGama(String gama) {
        return gama != null && (gama.equalsIgnoreCase("Baja") || 
                               gama.equalsIgnoreCase("Media") || 
                               gama.equalsIgnoreCase("Alta"));
    }
}