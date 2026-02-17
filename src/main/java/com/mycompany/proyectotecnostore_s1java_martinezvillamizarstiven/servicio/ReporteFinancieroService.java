package com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.servicio;

import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.dao.ReporteFinancieroDAO;
import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.modelo.IngresoMarca;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ReporteFinancieroService {
    private ReporteFinancieroDAO dao;
    private static final String RUTA_ARCHIVO = "reporte_ingresos_marca.txt";

    public ReporteFinancieroService(ReporteFinancieroDAO dao) {
        this.dao = dao;
    }

  
    public void generarReporteMesActual() throws SQLException, IOException {
        LocalDate hoy = LocalDate.now();
        generarReporte(hoy.getMonthValue(), hoy.getYear());
    }

   
    public void generarReporte(int mes, int anio) throws SQLException, IOException {
        System.out.println("\n Cargando datos del reporte...");
        
        List<IngresoMarca> ingresos = dao.obtenerIngresosPorMarca(mes, anio);

        if (ingresos == null || ingresos.isEmpty()) {
            System.out.println("  No hay ventas registradas para el mes solicitado.");
            return;
        }

  
        List<IngresoMarca> ingresoOrdenado = ingresos.stream()
                .sorted(Comparator.comparingDouble(IngresoMarca::getTotalIngresos).reversed())
                .collect(Collectors.toList());


        mostrarReporte(ingresoOrdenado, mes, anio);

       
        exportarReporte(ingresoOrdenado, mes, anio);
    }

    
    public List<IngresoMarca> obtenerIngresosMesActual() throws SQLException {
        return dao.obtenerIngresosPorMarcaMesActual();
    }

    public List<IngresoMarca> obtenerIngresos(int mes, int anio) throws SQLException {
        return dao.obtenerIngresosPorMarca(mes, anio);
    }

 
    public double calcularIngresoTotal(List<IngresoMarca> ingresos) {
        return ingresos.stream()
                .mapToDouble(IngresoMarca::getTotalIngresos)
                .sum();
    }

    private void mostrarReporte(List<IngresoMarca> ingresos, int mes, int anio) {
        String nombreMes = obtenerNombreMes(mes, anio);

        System.out.println("\n" + "=".repeat(70));
        System.out.println("--- REPORTE DE INGRESOS POR MARCA (" + nombreMes.toUpperCase() + ") ---");
        System.out.println("=".repeat(70));

        int posicion = 1;
        for (IngresoMarca ingreso : ingresos) {
            System.out.printf("%d. %s%n", posicion++, ingreso);
        }

        double total = calcularIngresoTotal(ingresos);
        System.out.println("-".repeat(70));
        System.out.printf("TOTAL INGRESOS: $%,.0f%n", total);
        System.out.println("=".repeat(70) + "\n");
    }

 
    private void exportarReporte(List<IngresoMarca> ingresos, int mes, int anio) throws IOException {
        LocalDate hoy = LocalDate.now();
        String nombreMes = obtenerNombreMes(mes, anio);

        try {
            File archivo = new File(RUTA_ARCHIVO);
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));

            writer.write("========================================================\n");
            writer.write("REPORTE DE INGRESOS MENSUALES POR MARCA\n");
            writer.write("Mes: " + nombreMes + "\n");
            writer.write("Generado: " + hoy.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n");
            writer.write("========================================================\n\n");

            int posicion = 1;
            for (IngresoMarca ingreso : ingresos) {
                writer.write(posicion + ". " + ingreso + "\n");
                posicion++;
            }

            double total = calcularIngresoTotal(ingresos);
            writer.write("\n" + "-".repeat(60) + "\n");
            writer.write(String.format("TOTAL INGRESOS: $%,.0f\n", total));
            writer.write("========================================================\n");

            writer.close();
            System.out.println(" Archivo generado exitosamente: " + RUTA_ARCHIVO);
        } catch (IOException e) {
            System.err.println(" Error al generar el archivo: " + e.getMessage());
            throw e;
        }
    }

 
    private String obtenerNombreMes(int mes, int anio) {
        try {
            return YearMonth.of(anio, mes).format(
                DateTimeFormatter.ofPattern("MMMM yyyy", new Locale("es", "ES"))
            );
        } catch (Exception e) {
            return String.format("%d/%d", mes, anio);
        }
    }
}