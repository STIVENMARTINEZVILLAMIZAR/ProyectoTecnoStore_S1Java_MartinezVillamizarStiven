package com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.persistencia;

import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.modelo.Venta;
import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.modelo.DetalleVenta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class VentaDAO {
    public int guardar(Venta venta) throws SQLException {
        String sql = "INSERT INTO ventas (id_cliente, fecha, total) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, venta.getIdCliente());
            stmt.setDate(2, java.sql.Date.valueOf(venta.getFecha()));
            stmt.setDouble(3, venta.getTotal());
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

    public void guardarDetalle(DetalleVenta detalle) throws SQLException {
        String sql = "INSERT INTO detalle_ventas (id_venta, id_celular, cantidad, subtotal) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, detalle.getIdVenta());
            stmt.setInt(2, detalle.getIdCelular());
            stmt.setInt(3, detalle.getCantidad());
            stmt.setDouble(4, detalle.getSubtotal());
            stmt.executeUpdate();
        }
    }

    public Venta obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM ventas WHERE id=?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Venta(rs.getInt("id"), rs.getInt("id_cliente"), 
                        rs.getDate("fecha").toLocalDate(), rs.getDouble("total"));
            }
        }
        return null;
    }

    public List<Venta> obtenerTodas() throws SQLException {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM ventas";
        try (Connection conn = ConexionDB.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ventas.add(new Venta(rs.getInt("id"), rs.getInt("id_cliente"), 
                        rs.getDate("fecha").toLocalDate(), rs.getDouble("total")));
            }
        }
        return ventas;
    }

    public List<Venta> obtenerPorMes(int mes, int anio) throws SQLException {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM ventas WHERE MONTH(fecha)=? AND YEAR(fecha)=?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, mes);
            stmt.setInt(2, anio);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ventas.add(new Venta(rs.getInt("id"), rs.getInt("id_cliente"), 
                        rs.getDate("fecha").toLocalDate(), rs.getDouble("total")));
            }
        }
        return ventas;
    }
}