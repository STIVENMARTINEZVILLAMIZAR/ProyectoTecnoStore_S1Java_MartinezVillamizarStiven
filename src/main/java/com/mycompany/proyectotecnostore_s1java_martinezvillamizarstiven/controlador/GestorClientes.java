package com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.controlador;

import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.modelo.Cliente;
import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.persistencia.ClienteDAO;
import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.utilidades.Validador;
import java.sql.SQLException;
import java.util.List;

public class GestorClientes {
    private ClienteDAO dao;

    public GestorClientes() {
        this.dao = new ClienteDAO();
    }

    public void registrar(String nombre, String identificacion, String correo, String telefono) throws SQLException {
        if (!Validador.validarCorreo(correo)) {
            throw new IllegalArgumentException("Correo inválido");
        }
        if (!Validador.validarIdentificacion(identificacion)) {
            throw new IllegalArgumentException("Identificación inválida");
        }
        if (!Validador.validarNombre(nombre)) {
            throw new IllegalArgumentException("Nombre inválido");
        }
        if (!Validador.validarTelefono(telefono)) {
            throw new IllegalArgumentException("Teléfono inválido");
        }
        if (dao.obtenerPorIdentificacion(identificacion) != null) {
            throw new IllegalArgumentException("Identificación ya registrada");
        }
        Cliente cliente = new Cliente(nombre, identificacion, correo, telefono);
        dao.guardar(cliente);
    }

    public void actualizar(int id, String nombre, String correo, String telefono) throws SQLException {
        Cliente cliente = dao.obtenerPorId(id);
        if (cliente != null) {
            if (!Validador.validarCorreo(correo)) {
                throw new IllegalArgumentException("Correo inválido");
            }
            if (!Validador.validarTelefono(telefono)) {
                throw new IllegalArgumentException("Teléfono inválido");
            }
            cliente.setNombre(nombre);
            cliente.setCorreo(correo);
            cliente.setTelefono(telefono);
            dao.actualizar(cliente);
        } else {
            throw new IllegalArgumentException("Cliente no existe");
        }
    }

    public void eliminar(int id) throws SQLException {
        dao.eliminar(id);
    }

    public Cliente obtenerPorId(int id) throws SQLException {
        return dao.obtenerPorId(id);
    }

    public List<Cliente> listar() throws SQLException {
        return dao.obtenerTodos();
    }

    public Cliente obtenerPorIdentificacion(String identificacion) throws SQLException {
        return dao.obtenerPorIdentificacion(identificacion);
    }
}