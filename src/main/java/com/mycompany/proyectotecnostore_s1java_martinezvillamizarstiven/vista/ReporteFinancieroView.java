package com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.vista;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.servicio.ReporteFinancieroService;

public class ReporteFinancieroView {
    private ReporteFinancieroService servicio;
    private Scanner scanner;

    public ReporteFinancieroView(ReporteFinancieroService servicio) {
        this.servicio = servicio;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Menú principal del reporte
     */
    public void mostrarMenu() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("        MENÚ - REPORTES FINANCIEROS");
            System.out.println("=".repeat(60));
            System.out.println("1. Generar reporte del mes actual");
            System.out.println("2. Generar reporte de mes específico");
            System.out.println("3. Salir");
            System.out.println("=".repeat(60));
            System.out.print("Seleccione una opción: ");

            int opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1:
                        servicio.generarReporteMesActual();
                        break;
                    case 2:
                        generarReporteMesEspecifico();
                        break;
                    case 3:
                        salir = true;
                        System.out.println("👋 Saliendo del módulo de reportes...");
                        break;
                    default:
                        System.out.println("❌ Opción no válida.");
                }
            } catch (SQLException e) {
                System.err.println("❌ Error en la base de datos: " + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("❌ Error al generar archivo: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Solicita mes y año específico
     */
    private void generarReporteMesEspecifico() throws SQLException, IOException {
        System.out.print("\nIngrese el mes (1-12): ");
        int mes = leerOpcion();

        if (mes < 1 || mes > 12) {
            System.out.println("❌ Mes inválido. Debe estar entre 1 y 12.");
            return;
        }

        System.out.print("Ingrese el año: ");
        int anio = leerOpcion();

        LocalDate ahora = LocalDate.now();
        if (anio < 2020 || anio > ahora.getYear()) {
            System.out.println("❌ Año inválido. Debe estar entre 2020 y " + ahora.getYear());
            return;
        }

        servicio.generarReporte(mes, anio);
    }

    /**
     * Lee una opción del usuario
     */
    private int leerOpcion() {
        try {
            String entrada = scanner.nextLine().trim();
            return Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}