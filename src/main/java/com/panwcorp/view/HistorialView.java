package com.panwcorp.view;

import com.panwcorp.model.HistorialPedidos;
import com.panwcorp.model.Pedido;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HistorialView {

    private HistorialPedidos historial;
    private ListView<String> listaPedidos;
    private Label lblInfo;
    private Label lblPedidoActual;
    private TextArea logArea;

    public HistorialView(HistorialPedidos historial) {
        this.historial = historial;
    }

    public void mostrar(Stage stage) {
        // HEADER
        Label titulo = new Label("Historial de Pedidos — Pila de Operaciones");
        titulo.getStyleClass().add("titulo");
        HBox header = new HBox(titulo);
        header.setAlignment(Pos.CENTER);
        header.getStyleClass().add("header");

        // CARD: Información actual
        lblPedidoActual = new Label("Sin pedidos");
        lblPedidoActual.getStyleClass().add("valor-siguiente");
        Label etqPedido = new Label("Pedido en el tope:");
        etqPedido.getStyleClass().add("etiqueta-card");
        VBox cardPedido = new VBox(5, etqPedido, lblPedidoActual);
        cardPedido.getStyleClass().add("card-siguiente");
        cardPedido.setAlignment(Pos.CENTER);

        // CARD: Info de la pila
        lblInfo = new Label("Pedidos: 0 | Total: 0");
        lblInfo.getStyleClass().add("valor-espera");
        Label etqInfo = new Label("Estado de la pila:");
        etqInfo.getStyleClass().add("etiqueta-card");
        VBox cardInfo = new VBox(5, etqInfo, lblInfo);
        cardInfo.getStyleClass().add("card-espera");
        cardInfo.setAlignment(Pos.CENTER);

        HBox panelInfo = new HBox(16, cardPedido, cardInfo);
        panelInfo.setAlignment(Pos.CENTER);
        panelInfo.setPadding(new Insets(20, 30, 10, 30));

        // BOTONES
        Button btnAgregar = new Button("+ Agregar Pedido");
        btnAgregar.getStyleClass().add("btn-agregar");

        Button btnDeshacer = new Button("⟲ Deshacer");
        btnDeshacer.getStyleClass().add("btn-atender");

        Button btnLimpiar = new Button("🗑 Limpiar Historial");
        btnLimpiar.getStyleClass().add("btn-agregar");

        HBox controles = new HBox(12, btnAgregar, btnDeshacer, btnLimpiar);
        controles.setAlignment(Pos.CENTER);
        controles.setPadding(new Insets(10, 30, 10, 30));

        // LISTA DE PEDIDOS
        Label lblTituloLista = new Label("Pila de pedidos (del más reciente al más antiguo):");
        lblTituloLista.getStyleClass().add("titulo-lista");

        listaPedidos = new ListView<>();
        listaPedidos.getStyleClass().add("lista-turnos");
        listaPedidos.setPrefHeight(200);

        VBox panelLista = new VBox(8, lblTituloLista, listaPedidos);
        panelLista.setPadding(new Insets(0, 30, 15, 30));
        VBox.setVgrow(listaPedidos, Priority.ALWAYS);

        // LOG
        Label lblLog = new Label("Registro de operaciones:");
        lblLog.getStyleClass().add("titulo-lista");

        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.getStyleClass().add("log-area");
        logArea.setPrefRowCount(5);

        VBox panelLog = new VBox(5, lblLog, logArea);
        panelLog.setPadding(new Insets(0, 30, 20, 30));

        // LAYOUT PRINCIPAL
        VBox root = new VBox(header, panelInfo, controles, panelLista, panelLog);
        root.getStyleClass().add("root");

        // LÓGICA DE BOTONES

        // Agregar un pedido de prueba
        btnAgregar.setOnAction(e -> {
            int numero = historial.getPedidosTotal() + 1;
            Pedido pedido = new Pedido(numero, "Cliente #" + numero, "Café - 2 piezas", 5.50);
            historial.agregarPedido(pedido);
            actualizarUI();
            log("[Pila] Pedido #" + numero + " agregado al tope.");
            log("[Pila] Estado: " + historial.getInfo());
            log("---");
        });

        // Deshacer: pop()
        btnDeshacer.setOnAction(e -> {
            if (historial.estaVacia()) {
                log("[Pila] La pila está vacía, no hay pedidos para deshacer.");
                log("---");
                return;
            }
            Pedido pedidoDesecho = historial.deshacer();
            actualizarUI();
            log("[Pila] Pedido desecho: " + pedidoDesecho);
            log("[Pila] Estado: " + historial.getInfo());
            log("---");
        });

        // Limpiar historial (útil al cambiar de turnos)
        btnLimpiar.setOnAction(e -> {
            if (historial.estaVacia()) {
                log("[Pila] El historial ya está vacío.");
                log("---");
                return;
            }
            int cantidad = historial.getTamano();
            historial.limpiar();
            actualizarUI();
            log("[Pila] Se eliminaron " + cantidad + " pedidos del historial.");
            log("[Pila] Inicio de nuevo turno (pila reiniciada).");
            log("---");
        });

        // ESCENA
        Scene scene = new Scene(root, 800, 700);
        stage.setTitle("Historial de Pedidos — Pila de Operaciones");
        stage.setScene(scene);

        // Actualizar UI inicial
        actualizarUI();

        stage.show();
    }

    private void actualizarUI() {
        // Actualizar pedido en el tope
        Pedido pedidoActual = historial.verTope();
        if (pedidoActual != null) {
            lblPedidoActual.setText(pedidoActual.toString());
        } else {
            lblPedidoActual.setText("Sin pedidos");
        }

        // Actualizar información de la pila
        lblInfo.setText(historial.getInfo());

        // Actualizar lista de pedidos
        listaPedidos.getItems().clear();
        int posicion = 1;
        for (Pedido p : historial.obtenerTodos()) {
            listaPedidos.getItems().add(String.format("[%d] %s", posicion, p));
            posicion++;
        }
    }

    private void log(String mensaje) {
        logArea.appendText(mensaje + "\n");
    }
}
