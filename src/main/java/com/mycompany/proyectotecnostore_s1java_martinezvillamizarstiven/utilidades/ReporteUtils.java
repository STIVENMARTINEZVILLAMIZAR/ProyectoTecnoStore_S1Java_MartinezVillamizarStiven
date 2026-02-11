package com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.utilidades;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.controlador.GestorVentas;
import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.modelo.Celular;
import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.modelo.Venta;

public class ReporteUtils {
    public static void generarReporteVentas(GestorVentas gestor) throws SQLException, IOException {
        List<Venta> ventas = gestor.obtenerTodasLasVentas();
        
        try (FileWriter writer = new FileWriter("reporte_ventas.txt")) {
            writer.write("========== REPORTE DE VENTAS - TECNOSTORE ==========\n");
            writer.write("Fecha generación: " + java.time.LocalDate.now() + "\n\n");
            
            double totalGeneral = 0;
            for (Venta venta : ventas) {
                writer.write("ID Venta: " + venta.getId() + "\n");
                writer.write("Cliente: " + venta.getIdCliente() + "\n");
                writer.write("Fecha: " + venta.getFecha() + "\n");
                writer.write("Total: $" + String.format("%.2f", venta.getTotal()) + "\n");
                writer.write("-------------------------------------------\n");
                totalGeneral += venta.getTotal();
            }
            
            writer.write("\nTOTAL GENERAL: $" + String.format("%.2f", totalGeneral) + "\n");
            writer.write("Total de ventas: " + ventas.size() + "\n");
        }
        
        System.out.println("Reporte guardado en: reporte_ventas.txt");
    }

    public static void mostrarStockBajo(List<Celular> celulares) {
        System.out.println("\n========== CELULARES CON STOCK BAJO ==========");
        if (celulares.isEmpty()) {
            System.out.println("No hay celulares con stock bajo.");
            return;
        }
        
        for (Celular c : celulares) {
            System.out.println("ID: " + c.getId() + " | " + c.getMarca() + " " + c.getModelo() + 
                             " | Stock: " + c.getStock() + " | Gama: " + c.getGama());
        }
    }
}