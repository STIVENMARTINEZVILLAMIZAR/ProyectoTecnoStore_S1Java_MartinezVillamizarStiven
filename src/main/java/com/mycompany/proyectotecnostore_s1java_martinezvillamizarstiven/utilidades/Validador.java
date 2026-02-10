package com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.utilidades;

import java.util.regex.Pattern;

public class Validador {
    public static boolean validarCorreo(String correo) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return correo != null && Pattern.matches(regex, correo);
    }

    public static boolean validarIdentificacion(String identificacion) {
        return identificacion != null && identificacion.length() >= 8 && identificacion.length() <= 20;
    }

    public static boolean validarPrecio(double precio) {
        return precio > 0;
    }

    public static boolean validarStock(int stock) {
        return stock >= 0;
    }

    public static boolean validarNombre(String nombre) {
        return nombre != null && nombre.length() >= 3 && nombre.length() <= 100;
    }

    public static boolean validarTelefono(String telefono) {
        return telefono != null && telefono.length() >= 10;
    }

    public static boolean validarOpcion(int opcion, int minima, int maxima) {
        return opcion >= minima && opcion <= maxima;
    }
}
