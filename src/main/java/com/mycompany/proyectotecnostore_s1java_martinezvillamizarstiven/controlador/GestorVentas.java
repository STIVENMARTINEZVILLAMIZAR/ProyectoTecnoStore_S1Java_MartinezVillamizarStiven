package com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.controlador;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.modelo.Celular;
import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.modelo.DetalleVenta;
import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.modelo.Venta;
import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.persistencia.VentaDAO;

public class GestorVentas {
    private VentaDAO dao;
    private GestorCelulares gestorCelulares;
    private static final double IVA = 0.19;

    public GestorVentas() {
        this.dao = new VentaDAO();
        this.gestorCelulares = new GestorCelulares();
    }

    public int registrarVenta(int idCliente, List<DetalleVenta> detalles) throws SQLException {
        if (detalles == null || detalles.isEmpty()) {
            throw new IllegalArgumentException("La venta debe tener al menos un detalle");
        }

        double subtotal = calcularSubtotal(detalles);
        double total = subtotal * (1 + IVA);
        
        Venta venta = new Venta(0, idCliente, LocalDate.now(), total);
        int idVenta = dao.guardar(venta);
        
        for (DetalleVenta detalle : detalles) {
            detalle.setIdVenta(idVenta);
            dao.guardarDetalle(detalle);
            
            Celular celular = gestorCelulares.obtenerPorId(detalle.getIdCelular());
            if (celular != null) {
                int nuevoStock = celular.getStock() - detalle.getCantidad();
                if (nuevoStock < 0) {
                    throw new IllegalArgumentException("Stock insuficiente para " + celular.getModelo());
                }
                gestorCelulares.actualizar(celular.getId(), celular.getMarca(), celular.getModelo(),
                        celular.getPrecio(), nuevoStock, celular.getSistemaOperativo(), celular.getGama());
            }
        }
        
        return idVenta;
    }

    private double calcularSubtotal(List<DetalleVenta> detalles) {
        return detalles.stream().mapToDouble(DetalleVenta::getSubtotal).sum();
    }

    public List<Venta> obtenerVentasMes(int mes, int anio) throws SQLException {
        return dao.obtenerPorMes(mes, anio);
    }

    public List<Venta> obtenerTodasLasVentas() throws SQLException {
        return dao.obtenerTodas();
    }

    public double obtenerTotalVentasMes(int mes, int anio) throws SQLException {
        return obtenerVentasMes(mes, anio).stream()
                .mapToDouble(Venta::getTotal)
                .sum();
    }

    public List<Celular> obtenerTop3Vendidos() throws SQLException {
        return null;
    }
}
