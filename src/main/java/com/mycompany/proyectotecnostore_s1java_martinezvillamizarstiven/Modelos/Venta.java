package com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.Modelos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private int id;
    private int idCliente;
    private LocalDate fecha;
    private double total;
    private List<ItemVenta> items;

    public Venta(int id, int idCliente, LocalDate fecha, double total) {
        this.id = id;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.total = total;
        this.items = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public List<ItemVenta> getItems() { return items; }
    public void agregarItem(ItemVenta item) { items.add(item); }

    @Override
    public String toString() {
        return "Venta{id=" + id + ", cliente=" + idCliente + ", fecha=" + fecha + ", total=" + total + "}";
    }
}