package com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.persistencia;


import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.modelo.Celular;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CelularDAO {
    
    public void guardar(Celular celular) throws SQLException {
        String sql = "INSERT INTO celulares (marca, modelo, precio, stock, sistema_operativo, gama) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, celular.getMarca());
            stmt.setString(2, celular.getModelo());
            stmt.setDouble(3, celular.getPrecio());
            stmt.setInt(4, celular.getStock());
            stmt.setString(5, celular.getSistemaOperativo());
            stmt.setString(6, celular.getGama());
            stmt.executeUpdate();
        }
    }

    public void actualizar(Celular celular) throws SQLException {
        String sql = "UPDATE celulares SET marca=?, modelo=?, precio=?, stock=?, sistema_operativo=?, gama=? WHERE id=?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, celular.getMarca());
            stmt.setString(2, celular.getModelo());
            stmt.setDouble(3, celular.getPrecio());
            stmt.setInt(4, celular.getStock());
            stmt.setString(5, celular.getSistemaOperativo());
            stmt.setString(6, celular.getGama());
            stmt.setInt(7, celular.getId());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM celulares WHERE id=?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Celular obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM celulares WHERE id=?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Celular(rs.getInt("id"), rs.getString("marca"), rs.getString("modelo"),
                        rs.getDouble("precio"), rs.getInt("stock"), rs.getString("sistema_operativo"), rs.getString("gama"));
            }
        }
        return null;
    }

    public List<Celular> obtenerTodos() throws SQLException {
        List<Celular> celulares = new ArrayList<>();
        String sql = "SELECT * FROM celulares";
        try (Connection conn = ConexionDB.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                celulares.add(new Celular(rs.getInt("id"), rs.getString("marca"), rs.getString("modelo"),
                        rs.getDouble("precio"), rs.getInt("stock"), rs.getString("sistema_operativo"), rs.getString("gama")));
            }
        }
        return celulares;
    }
}
