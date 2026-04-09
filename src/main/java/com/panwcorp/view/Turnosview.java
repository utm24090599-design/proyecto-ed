package com.panwcorp.view;

import com.panwcorp.model.colaClientes;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Turnosview {

    private final colaClientes cola = new colaClientes();

   
    private Label lblSiguiente;
    private Label lblEsperando;
    private Label lblTotal;
    private ListView<String> listaEspera;

    public void mostrar(Stage stage) {

        // HEADER 
        Label titulo = new Label("Cafeteria — Gestión de Turnos");
        titulo.getStyleClass().add("titulo");
        HBox header = new HBox(titulo);
        header.setAlignment(Pos.CENTER);
        header.getStyleClass().add("header");

        //CARDS DE INFO 

        // Card: Proximo a ser atendido (equivalente a cola.peek())
        lblSiguiente = new Label("Sin clientes");
        lblSiguiente.getStyleClass().add("valor-siguiente");
        Label etqSiguiente = new Label("Próximo a ser atendido");
        etqSiguiente.getStyleClass().add("etiqueta-card");
        VBox cardSiguiente = new VBox(5, etqSiguiente, lblSiguiente);
        cardSiguiente.getStyleClass().add("card-siguiente");
        cardSiguiente.setAlignment(Pos.CENTER);

        // Card: Clientes esperando (equivalente a cola.size())
        lblEsperando = new Label("0");
        lblEsperando.getStyleClass().add("valor-espera");
        Label etqEsperando = new Label("En espera");
        etqEsperando.getStyleClass().add("etiqueta-card");
        VBox cardEspera = new VBox(5, etqEsperando, lblEsperando);
        cardEspera.getStyleClass().add("card-espera");
        cardEspera.setAlignment(Pos.CENTER);

        // Card: Total atendidos
        lblTotal = new Label("0");
        lblTotal.getStyleClass().add("valor-total");
        Label etqTotal = new Label("Total atendidos");
        etqTotal.getStyleClass().add("etiqueta-card");
        VBox cardTotal = new VBox(5, etqTotal, lblTotal);
        cardTotal.getStyleClass().add("card-total");
        cardTotal.setAlignment(Pos.CENTER);

        HBox panelInfo = new HBox(16, cardSiguiente, cardEspera, cardTotal);
        panelInfo.setAlignment(Pos.CENTER);
        panelInfo.setPadding(new Insets(20, 30, 10, 30));

        // BOTONES 

        // Equivalente a: cola.add(totalPersonas)
        Button btnLlega = new Button("+ Cliente llega");
        btnLlega.getStyleClass().add("btn-agregar");

        // Equivalente a: cola.poll()
        Button btnAtender = new Button("  Cajero libre — Llamar siguiente");
        btnAtender.getStyleClass().add("btn-atender");

        HBox controles = new HBox(12, btnLlega, btnAtender);
        controles.setAlignment(Pos.CENTER);
        controles.setPadding(new Insets(10, 30, 10, 30));

        // COLA VISUAL
        Label lblTituloLista = new Label("Cola de espera:");
        lblTituloLista.getStyleClass().add("titulo-lista");

        listaEspera = new ListView<>();
        listaEspera.getStyleClass().add("lista-turnos");
        listaEspera.setPrefHeight(200);

        VBox panelLista = new VBox(8, lblTituloLista, listaEspera);
        panelLista.setPadding(new Insets(0, 30, 15, 30));

        // LOG (equivalente al System.out.println)
        Label lblLog = new Label("Registro de eventos:");
        lblLog.getStyleClass().add("titulo-lista");

        TextArea logArea = new TextArea();
        logArea.setEditable(false);
        logArea.getStyleClass().add("log-area");
        logArea.setPrefRowCount(5);

        VBox panelLog = new VBox(5, lblLog, logArea);
        panelLog.setPadding(new Insets(0, 30, 20, 30));

        // LAYOUT PRINCIPAL 
        VBox root = new VBox(header, panelInfo, controles, panelLista, panelLog);
        root.getStyleClass().add("root");

        // LOGICA DE BOTONES

        // Equivalente a: cola.add(totalPersonas)
        btnLlega.setOnAction(e -> {
            int turno = cola.encolar();
            actualizarUI();
            log(logArea, "[Cola] Cliente #" + turno + " entró a la fila.");
            log(logArea, "[Cola] Clientes esperando: " + cola.getSize());
            if (cola.verSiguiente() != -1)
                log(logArea, "[Cola] Próximo a ser atendido: Cliente #" + cola.verSiguiente());
            log(logArea, "---");
        });

        // Equivalente a: cola.poll()
        btnAtender.setOnAction(e -> {
            if (cola.estaVacia()) {
                log(logArea, "[Cola] No hay clientes en espera.");
                log(logArea, "---");
                return;
            }
            int atendido = cola.desencolar();
            actualizarUI();
            log(logArea, "[Ventanilla] Cliente #" + atendido + " llamado a la ventanilla.");
            log(logArea, "[Cola] Clientes en espera tras atención: " + cola.getSize());
            log(logArea, "---");
        });

        // ESCENA 
        Scene scene = new Scene(root, 660, 620);
       

        stage.setTitle("Cafetería — Sistema de Turnos");
        stage.setScene(scene);
        stage.show();
    }

    private void actualizarUI() {
        int sig = cola.verSiguiente();
        lblSiguiente.setText(sig == -1 ? "Sin clientes" : "Cliente #" + sig);
        lblEsperando.setText(String.valueOf(cola.getSize()));
        lblTotal.setText(String.valueOf(cola.getTotal() - cola.getSize()));
        listaEspera.getItems().setAll(cola.obtenerLista());
    }

    private void log(TextArea area, String msg) {
        area.appendText(msg + "\n");
    }
}