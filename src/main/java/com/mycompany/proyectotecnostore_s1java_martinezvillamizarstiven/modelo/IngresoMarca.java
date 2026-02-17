package com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.modelo;

public class IngresoMarca {
    private String marca;
    private double totalIngresos;

    public IngresoMarca(String marca, double totalIngresos) {
        this.marca = marca;
        this.totalIngresos = totalIngresos;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(double totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    @Override
    public String toString() {
        return String.format("%s → $%,.0f", marca, totalIngresos);
    }
}