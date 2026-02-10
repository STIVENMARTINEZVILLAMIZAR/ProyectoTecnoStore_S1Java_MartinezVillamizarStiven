package com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.modelo;

public class DetalleVenta {
    
    private int id;
    private int idVenta;
    private int idCelular;
    private int cantidad;
    private double subtotal;

    public DetalleVenta(int id, int idVenta, int idCelular, int cantidad, double subtotal) {
        this.id = id;
        this.idVenta = idVenta;
        this.idCelular = idCelular;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }
    public int getIdCelular() { return idCelular; }
    public void setIdCelular(int idCelular) { this.idCelular = idCelular; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    @Override
    public String toString() {
        return "DetalleVenta{celular=" + idCelular + ", cantidad=" + cantidad + ", subtotal=" + subtotal + "}";
    }
}