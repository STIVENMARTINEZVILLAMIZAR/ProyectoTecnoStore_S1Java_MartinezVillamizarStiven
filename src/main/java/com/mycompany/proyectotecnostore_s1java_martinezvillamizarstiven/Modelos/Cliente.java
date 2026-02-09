package com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.Modelos;


import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.Modelos.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Cliente{
    
    public void guardar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes (nombre, identificacion, correo, telefono) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getIdentificacion());
            stmt.setString(3, cliente.getCorreo());
            stmt.setString(4, cliente.getTelefono());
            stmt.executeUpdate();
        }
    }

    public void actualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE clientes SET nombre=?, correo=?, telefono=? WHERE identificacion=?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getCorreo());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getIdentificacion());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id=?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Cliente obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM clientes WHERE id=?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("identificacion"),
                        rs.getString("correo"), rs.getString("telefono"));
            }
        }
        return null;
    }

    public List<Cliente> obtenerTodos() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Connection conn = ConexionDB.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clientes.add(new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("identificacion"),
                        rs.getString("correo"), rs.getString("telefono")));
            }
        }
        return clientes;
    }

    public Cliente obtenerPorIdentificacion(String identificacion) throws SQLException {
        String sql = "SELECT * FROM clientes WHERE identificacion=?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, identificacion);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("identificacion"),
                        rs.getString("correo"), rs.getString("telefono"));
            }
        }
        return null;
    }
}