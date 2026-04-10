package com.panwcorp.model;

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
    private int size;

    public Catalogo() {
        this.cabeza = null;
        this.size = 0;
    }

    // Agregar al final (como antes)
    public void agregar(String nombre, double precio) {
        agregarEnPosicion(nombre, precio, size);
    }

    // Insertar en una posición específica (0-based)
    public boolean agregarEnPosicion(String nombre, double precio, int posicion) {
        if (posicion < 0 || posicion > size) return false;

        Producto nuevo = new Producto(nombre, precio);

        if (posicion == 0) {
            // Insertar al inicio
            nuevo.siguiente = cabeza;
            cabeza = nuevo;
        } else {
            // Insertar en medio o al final
            Producto actual = cabeza;
            for (int i = 0; i < posicion - 1; i++) {
                actual = actual.siguiente;
            }
            nuevo.siguiente = actual.siguiente;
            actual.siguiente = nuevo;
        }

        size++;
        return true;
    }

    // Eliminar por nombre
    public boolean eliminar(String nombre) {
        if (cabeza == null) return false;

        if (cabeza.nombre.equalsIgnoreCase(nombre)) {
            cabeza = cabeza.siguiente;
            size--;
            return true;
        }

        Producto actual = cabeza;
        while (actual.siguiente != null && !actual.siguiente.nombre.equalsIgnoreCase(nombre)) {
            actual = actual.siguiente;
        }

        if (actual.siguiente != null) {
            actual.siguiente = actual.siguiente.siguiente;
            size--;
            return true;
        }

        return false;
    }

    // Obtener el tamaño del catálogo
    public int getSize() {
        return size;
    }

    // Verificar si está vacío
    public boolean estaVacio() {
        return size == 0;
    }

    // Método para convertir la lista enlazada a una lista que la UI pueda leer
    public List<String> obtenerListaParaVista() {
        List<String> lista = new ArrayList<>();
        Producto temp = cabeza;
        int pos = 1;
        while (temp != null) {
            lista.add("[" + pos + "] " + temp.nombre + " - $" + temp.precio);
            temp = temp.siguiente;
            pos++;
        }
        return lista;
    }
}