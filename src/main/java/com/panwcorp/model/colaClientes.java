package com.panwcorp.model;

import java.util.ArrayList;
import java.util.List;

// PUNTO 1: Cola de clientes (implementacion manual con nodos)
public class colaClientes {

    private static class Nodo {
        int turno;
        Nodo siguiente;

        Nodo(int turno) {
            this.turno = turno;
            this.siguiente = null;
        }
    }

    private Nodo frente;
    private Nodo fin;
    private int size;
    private int totalPersonas; 

    public colaClientes() {
        frente = null;
        fin = null;
        size = 0;
        totalPersonas = 0;
    }

    // Equivalente a: cola.add(totalPersonas)
    public int encolar() {
        totalPersonas++;
        Nodo nuevo = new Nodo(totalPersonas);

        if (estaVacia()) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.siguiente = nuevo;
            fin = nuevo;
        }

        size++;
        return totalPersonas; // retorna el numero de turno asignado
    }

    // Equivalente a: cola.poll()
    public int desencolar() {
        if (estaVacia()) return -1;

        int turno = frente.turno;
        frente = frente.siguiente;
        if (frente == null) fin = null;
        size--;
        return turno;
    }

    // Equivalente a: cola.peek()
    public int verSiguiente() {
        if (estaVacia()) return -1;
        return frente.turno;
    }

    // Para mostrar toda la cola en el ListView
    public List<String> obtenerLista() {
        List<String> lista = new ArrayList<>();
        Nodo actual = frente;
        int pos = 1;
        while (actual != null) {
            lista.add("  #" + pos + "  →  Cliente #" + actual.turno);
            actual = actual.siguiente;
            pos++;
        }
        return lista;
    }

    public boolean estaVacia() { return frente == null; }
    public int getSize()       { return size; }
    public int getTotal()      { return totalPersonas; }
}