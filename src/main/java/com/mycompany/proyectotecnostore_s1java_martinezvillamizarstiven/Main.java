package com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven;

import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.vista.Menu;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== TECNOSTORE - Sistema de Gestión ===");
        System.out.println("Versión 1.0");
        System.out.println("Iniciando sistema...\n");
        System.out.println("Bienvenido a TecnoSTore");
        
        Menu menu = new Menu();
        menu.mostrarMenuPrincipal();
    }
}