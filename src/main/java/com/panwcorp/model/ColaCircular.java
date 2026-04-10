package com.panwcorp.model;

public class ColaCircular {

    private static final int CAPACIDAD = 3;
    private final String[] estaciones;
    private int frente;
    private int fin;
    private int cantidad;

    public ColaCircular() {
        estaciones = new String[CAPACIDAD];
        frente = 0;
        fin = -1;
        cantidad = 0;
    }

    /** Agrega una estación. Retorna false si la cola está llena (evita sobreescritura). */
    public boolean encolar(String estacion) {
        if (estaLlena()) return false;
        fin = (fin + 1) % CAPACIDAD;
        estaciones[fin] = estacion;
        cantidad++;
        return true;
    }

    /** Elimina y retorna la estación del frente. Retorna null si está vacía. */
    public String desencolar() {
        if (estaVacia()) return null;
        String atendida = estaciones[frente];
        frente = (frente + 1) % CAPACIDAD;
        cantidad--;
        return atendida;
    }

    /**
     * Rota la cola circular: atiende el frente y lo reencola al final.
     * Garantiza que ninguna estación quede desatendida indefinidamente.
     */
    public String rotar() {
        if (estaVacia()) return null;
        String atendida = desencolar();
        encolar(atendida);
        return atendida;
    }

    /** Ver la siguiente estación sin eliminarla. */
    public String verSiguiente() {
        if (estaVacia()) return null;
        return estaciones[frente];
    }

    /** Retorna las estaciones en orden actual de atención. */
    public String[] getOrdenActual() {
        String[] orden = new String[cantidad];
        for (int i = 0; i < cantidad; i++) {
            orden[i] = estaciones[(frente + i) % CAPACIDAD];
        }
        return orden;
    }

    public boolean estaLlena()  { return cantidad == CAPACIDAD; }
    public boolean estaVacia()  { return cantidad == 0; }
    public int getCantidad()    { return cantidad; }
    public int getCapacidad()   { return CAPACIDAD; }
}
