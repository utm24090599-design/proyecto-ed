package com.panwcorp.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pedido {
    private int id;
    private String cliente;
    private String descripcion;
    private LocalDateTime fecha;
    private double monto;

    public Pedido(int id, String cliente, String descripcion, double monto) {
        this.id = id;
        this.cliente = cliente;
        this.descripcion = descripcion;
        this.fecha = LocalDateTime.now();
        this.monto = monto;
    }

    public int getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public double getMonto() {
        return monto;
    }

    public String getFechaFormato() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return fecha.format(formatter);
    }

    @Override
    public String toString() {
        return String.format("Pedido #%d | Cliente: %s | %s | Monto: $%.2f | Hora: %s",
                id, cliente, descripcion, monto, getFechaFormato());
    }
}
