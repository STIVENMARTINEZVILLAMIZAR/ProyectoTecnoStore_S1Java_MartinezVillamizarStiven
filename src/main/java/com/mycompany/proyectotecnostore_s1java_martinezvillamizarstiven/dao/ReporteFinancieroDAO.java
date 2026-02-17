package com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.modelo.IngresoMarca;

public class ReporteFinancieroDAO {
    private Connection conexion;

    public ReporteFinancieroDAO(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Obtiene los ingresos por marca del mes actual
     */
    public List<IngresoMarca> obtenerIngresosPorMarcaMesActual() throws SQLException {
        List<IngresoMarca> resultados = new ArrayList<>();

        String sql = "SELECT c.marca, SUM(v.precio_venta) AS total_ingresos "
                + "FROM venta v "
                + "JOIN celular c ON v.id_celular = c.id "
                + "WHERE MONTH(v.fecha_venta) = MONTH(CURDATE()) "
                + "AND YEAR(v.fecha_venta) = YEAR(CURDATE()) "
                + "GROUP BY c.marca "
                + "ORDER BY total_ingresos DESC";

        try (Statement stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String marca = rs.getString("marca");
                double totalIngresos = rs.getDouble("total_ingresos");
                resultados.add(new IngresoMarca(marca, totalIngresos));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ingresos del mes actual: " + e.getMessage());
            throw e;
        }

        return resultados;
    }

    /**
     * Obtiene los ingresos por marca de un mes específico
     */
    public List<IngresoMarca> obtenerIngresosPorMarca(int mes, int anio) throws SQLException {
        List<IngresoMarca> resultados = new ArrayList<>();

        String sql = "SELECT c.marca, SUM(v.precio_venta) AS total_ingresos "
                + "FROM venta v "
                + "JOIN celular c ON v.id_celular = c.id "
                + "WHERE MONTH(v.fecha_venta) = ? "
                + "AND YEAR(v.fecha_venta) = ? "
                + "GROUP BY c.marca "
                + "ORDER BY total_ingresos DESC";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, mes);
            pstmt.setInt(2, anio);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String marca = rs.getString("marca");
                    double totalIngresos = rs.getDouble("total_ingresos");
                    resultados.add(new IngresoMarca(marca, totalIngresos));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ingresos por marca: " + e.getMessage());
            throw e;
        }

        return resultados;
    }
}