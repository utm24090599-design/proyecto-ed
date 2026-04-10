package com.panwcorp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * PUNTO 3: Historial de Pedidos Recientes
 * Implementacion de una pila (Stack) para gestionar pedidos recientes en cada caja.
 * Permite deshacer pedidos en caso de error, operación por operación.
 */
public class HistorialPedidos {

    private static class Nodo {
        Pedido pedido;
        Nodo siguiente;

        Nodo(Pedido pedido) {
            this.pedido = pedido;
            this.siguiente = null;
        }
    }

    private Nodo tope;
    private int size;
    private int pedidosTotal;

    public HistorialPedidos() {
        this.tope = null;
        this.size = 0;
        this.pedidosTotal = 0;
    }

    /**
     * Insertar un pedido en la pila (push)
     * Caso especial: inserción en una pila vacía al iniciar el turno
     */
    public void agregarPedido(Pedido pedido) {
        if (pedido == null) return;

        Nodo nuevoNodo = new Nodo(pedido);

        if (estaVacia()) {
            // Caso especial: primera inserción (pila vacía al iniciar turno)
            tope = nuevoNodo;
        } else {
            nuevoNodo.siguiente = tope;
            tope = nuevoNodo;
        }

        size++;
        pedidosTotal++;
    }

    /**
     * Extraer el pedido del tope de la pila (pop)
     * Deshacer el último pedido en caso de error
     */
    public Pedido deshacer() {
        if (estaVacia()) {
            return null; // No hay pedidos para deshacer
        }

        Pedido pedidoDesecho = tope.pedido;
        tope = tope.siguiente;
        size--;
        return pedidoDesecho;
    }

    /**
     * Consultar el pedido en el tope sin extraerlo (peek)
     */
    public Pedido verTope() {
        if (estaVacia()) {
            return null;
        }
        return tope.pedido;
    }

    /**
     * Verificar si la pila está vacía antes de extraer
     */
    public boolean estaVacia() {
        return tope == null;
    }

    /**
     * Obtener el tamaño actual de la pila
     */
    public int getTamano() {
        return size;
    }

    /**
     * Obtener el total de pedidos procesados
     */
    public int getPedidosTotal() {
        return pedidosTotal;
    }

    /**
     * Obtener todos los pedidos en la pila como una lista
     * (útil para mostrar en la UI)
     */
    public List<Pedido> obtenerTodos() {
        List<Pedido> lista = new ArrayList<>();
        Nodo actual = tope;
        while (actual != null) {
            lista.add(actual.pedido);
            actual = actual.siguiente;
        }
        return lista;
    }

    /**
     * Limpiar el historial (útil al cambiar de turno de cajero)
     */
    public void limpiar() {
        tope = null;
        size = 0;
        pedidosTotal = 0;
    }

    /**
     * Obtener información del historial como string
     */
    public String getInfo() {
        return String.format("Pedidos en pila: %d | Total procesados: %d", size, pedidosTotal);
    }
}
