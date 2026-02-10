package com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.controlador;


import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.modelo.Celular;
import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.persistencia.CelularDAO;
import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.utilidades.Validador;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class GestorCelulares {
    private CelularDAO dao;

    public GestorCelulares() {
        this.dao = new CelularDAO();
    }

    public void registrar(String marca, String modelo, double precio, int stock, String so, String gama) throws SQLException {
        if (!Validador.validarPrecio(precio)) {
            throw new IllegalArgumentException("El precio debe ser positivo");
        }
        if (!Validador.validarStock(stock)) {
            throw new IllegalArgumentException("El stock debe ser positivo o cero");
        }
        Celular celular = new Celular(marca, modelo, precio, stock, so, gama);
        dao.guardar(celular);
    }

    public void actualizar(int id, String marca, String modelo, double precio, int stock, String so, String gama) throws SQLException {
        if (!Validador.validarPrecio(precio) || !Validador.validarStock(stock)) {
            throw new IllegalArgumentException("Datos inválidos");
        }
        Celular celular = new Celular(id, marca, modelo, precio, stock, so, gama);
        dao.actualizar(celular);
    }

    public void eliminar(int id) throws SQLException {
        dao.eliminar(id);
    }

    public Celular obtenerPorId(int id) throws SQLException {
        return dao.obtenerPorId(id);
    }

    public List<Celular> listar() throws SQLException {
        return dao.obtenerTodos();
    }

    public List<Celular> obtenerStockBajo() throws SQLException {
        return dao.obtenerTodos().stream()
                .filter(c -> c.getStock() < 5)
                .collect(Collectors.toList());
    }

    public List<Celular> obtenerPorGama(String gama) throws SQLException {
        return dao.obtenerTodos().stream()
                .filter(c -> c.getGama().equalsIgnoreCase(gama))
                .collect(Collectors.toList());
    }
}