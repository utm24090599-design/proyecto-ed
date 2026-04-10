package com.juanwcorp.model;

import java.util.ArrayList;
import java.util.List;

class Producto {
    String nombre;
    double precio;
    Producto siguiente;

    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
        this.siguiente = null;
    }
}

public class Catalogo {
    private Producto cabeza;

    public void agregar(String nombre, double precio) {
        Producto nuevo = new Producto(nombre, precio);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Producto temp = cabeza;
            while (temp.siguiente != null) temp = temp.siguiente;
            temp.siguiente = nuevo;
        }
    }

    public void eliminar(String nombre) {
        if (cabeza == null) return;
        if (cabeza.nombre.equalsIgnoreCase(nombre)) {
            cabeza = cabeza.siguiente;
            return;
        }
        Producto actual = cabeza;
        while (actual.siguiente != null && !actual.siguiente.nombre.equalsIgnoreCase(nombre)) {
            actual = actual.siguiente; 
        }
        if (actual.siguiente != null) actual.siguiente = actual.siguiente.siguiente;
    }

    // Método para convertir la lista enlazada a una lista que la UI pueda leer
    public List<String> obtenerListaParaVista() {
        List<String> lista = new ArrayList<>();
        Producto temp = cabeza;
        while (temp != null) {
            lista.add(temp.nombre + " - $" + temp.precio);
            temp = temp.siguiente;
        }
        return lista;
    }
}