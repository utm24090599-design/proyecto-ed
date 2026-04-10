package com.panwcorp.view;

import com.panwcorp.model.ColaCircular;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class EstacionesView {

    // ── Modelo ───────────────────────────────────────────────────────────────
    private final ColaCircular cola = new ColaCircular();

    private static final String[] NOMBRES = {"☕ Bebidas", "🍲 Comida Caliente", "🍿 Snacks"};
    private static final String[] COLORES = {"#4A90D9", "#E8724A", "#6BBF59"};

    // ── Nodos que se actualizan ──────────────────────────────────────────────
    private Label   lblSiguiente;
    private Label   lblEnRotacion;
    private Label   lblRonda;
    private HBox    cardsBox;
    private int     turnoPersonal = 1;

    // ── Punto de entrada — mismo patrón que Turnosview ───────────────────────
    public void mostrar(Stage stage) {

        inicializarCola();

        // HEADER — misma estructura que Turnosview
        Label titulo = new Label("Cafetería — Control de Estaciones");
        titulo.getStyleClass().add("titulo");
        HBox header = new HBox(titulo);
        header.setAlignment(Pos.CENTER);
        header.getStyleClass().add("header");

        // CARDS DE INFO — misma estructura que Turnosview

        // Card: Siguiente estación a atender
        lblSiguiente = new Label("—");
        lblSiguiente.getStyleClass().add("valor-siguiente");
        Label etqSiguiente = new Label("Siguiente estación");
        etqSiguiente.getStyleClass().add("etiqueta-card");
        VBox cardSiguiente = new VBox(5, etqSiguiente, lblSiguiente);
        cardSiguiente.getStyleClass().add("card-siguiente");
        cardSiguiente.setAlignment(Pos.CENTER);

        // Card: Estaciones en rotación
        lblEnRotacion = new Label("3");
        lblEnRotacion.getStyleClass().add("valor-espera");
        Label etqRotacion = new Label("En rotación");
        etqRotacion.getStyleClass().add("etiqueta-card");
        VBox cardRotacion = new VBox(5, etqRotacion, lblEnRotacion);
        cardRotacion.getStyleClass().add("card-espera");
        cardRotacion.setAlignment(Pos.CENTER);

        // Card: Ronda actual
        lblRonda = new Label("1");
        lblRonda.getStyleClass().add("valor-total");
        Label etqRonda = new Label("Ronda actual");
        etqRonda.getStyleClass().add("etiqueta-card");
        VBox cardRonda = new VBox(5, etqRonda, lblRonda);
        cardRonda.getStyleClass().add("card-total");
        cardRonda.setAlignment(Pos.CENTER);

        HBox panelInfo = new HBox(16, cardSiguiente, cardRotacion, cardRonda);
        panelInfo.setAlignment(Pos.CENTER);
        panelInfo.setPadding(new Insets(20, 30, 10, 30));

        // BOTONES — misma estructura que Turnosview
        Button btnAsignar   = new Button("▶  Asignar personal — Siguiente estación");
        btnAsignar.getStyleClass().add("btn-atender");

        Button btnReiniciar = new Button("↺  Reiniciar rotación");
        btnReiniciar.getStyleClass().add("btn-agregar");

        HBox controles = new HBox(12, btnAsignar, btnReiniciar);
        controles.setAlignment(Pos.CENTER);
        controles.setPadding(new Insets(10, 30, 10, 30));

        // COLA VISUAL de estaciones (equivalente al ListView de turnos)
        Label lblTituloCards = new Label("Orden de atención circular:");
        lblTituloCards.getStyleClass().add("titulo-lista");

        cardsBox = new HBox(16);
        cardsBox.setAlignment(Pos.CENTER);
        cardsBox.setPadding(new Insets(8, 0, 8, 0));

        VBox panelCards = new VBox(8, lblTituloCards, cardsBox);
        panelCards.setPadding(new Insets(0, 30, 15, 30));

        // LOG — mismo componente que Turnosview
        Label lblLog = new Label("Registro de asignaciones:");
        lblLog.getStyleClass().add("titulo-lista");

        TextArea logArea = new TextArea();
        logArea.setEditable(false);
        logArea.getStyleClass().add("log-area");
        logArea.setPrefRowCount(5);

        VBox panelLog = new VBox(5, lblLog, logArea);
        panelLog.setPadding(new Insets(0, 30, 20, 30));

        // LAYOUT PRINCIPAL — mismo patrón que Turnosview
        VBox root = new VBox(header, panelInfo, controles, panelCards, panelLog);
        root.getStyleClass().add("root");

        // LÓGICA DE BOTONES
        btnAsignar.setOnAction(e -> {
            if (cola.estaVacia()) {
                log(logArea, "[Estaciones] Cola vacía.");
                log(logArea, "---");
                return;
            }
            String atendida = cola.rotar();
            actualizarUI(atendida);
            log(logArea, "[Rotación] Estación atendida: " + atendida);
            log(logArea, "[Asignación] Empleado #" + (((turnoPersonal - 2) % 5) + 1)
                    + " asignado  |  Ronda #" + ((turnoPersonal - 2) / 3 + 1));
            log(logArea, "[Cola] Siguiente: " + cola.verSiguiente());
            log(logArea, "---");
        });

        btnReiniciar.setOnAction(e -> {
            while (!cola.estaVacia()) cola.desencolar();
            for (String nombre : NOMBRES) cola.encolar(nombre);
            turnoPersonal = 1;
            actualizarUI(null);
            log(logArea, "[Sistema] Cola circular reiniciada.");
            log(logArea, "[Cola] Orden: Bebidas → Comida Caliente → Snacks");
            log(logArea, "---");
        });

        // ESCENA — misma forma que Turnosview
        actualizarUI(null);
        Scene scene = new Scene(root, 660, 620);
        scene.getStylesheets().add(
            getClass().getResource("/styles.css").toExternalForm()
        );

        stage.setTitle("Cafetería — Control de Estaciones");
        stage.setScene(scene);
        stage.show();
    }

    // ── Inicialización ────────────────────────────────────────────────────────
    private void inicializarCola() {
        for (String nombre : NOMBRES) cola.encolar(nombre);
    }

    // ── Actualización de UI ───────────────────────────────────────────────────
    private void actualizarUI(String ultimaAtendida) {
        // Cards del estado
        String sig = cola.verSiguiente();
        lblSiguiente.setText(sig != null ? sig : "—");
        lblEnRotacion.setText(String.valueOf(cola.getCantidad()));
        lblRonda.setText(String.valueOf((turnoPersonal - 1) / 3 + 1));

        // Cards visuales de estaciones
        cardsBox.getChildren().clear();
        String[] orden = cola.getOrdenActual();
        for (int i = 0; i < orden.length; i++) {
            String nombre    = orden[i];
            int    idx       = indiceDeNombre(nombre);
            boolean esSig    = (i == 0);
            boolean fueAten  = nombre.equals(ultimaAtendida) && i == orden.length - 1;
            cardsBox.getChildren().add(crearCard(nombre, COLORES[idx], esSig, fueAten, i + 1));
        }
    }

    private VBox crearCard(String nombre, String color,
                           boolean esSiguiente, boolean fueAtendida, int posicion) {
        VBox card = new VBox(8);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(16, 14, 16, 14));
        card.setPrefWidth(160);
        card.setPrefHeight(140);

        String borderColor = esSiguiente ? "#007bff" : (fueAtendida ? "#28a745" : "#dee2e6");
        double borderWidth = esSiguiente ? 3 : 1.5;

        card.setStyle(String.format("""
                -fx-background-color: white;
                -fx-background-radius: 10;
                -fx-border-color: %s;
                -fx-border-radius: 10;
                -fx-border-width: %.1f;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.10), 6, 0, 0, 2);
                """, borderColor, borderWidth));

        // Círculo de color con número de posición
        Circle circulo = new Circle(20);
        circulo.setFill(Color.web(color));
        Label lblPos = new Label(String.valueOf(posicion));
        lblPos.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        lblPos.setTextFill(Color.WHITE);
        StackPane circuloPane = new StackPane(circulo, lblPos);

        Label lblNombre = new Label(nombre);
        lblNombre.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        lblNombre.setStyle("-fx-text-fill: #333333;");
        lblNombre.setWrapText(true);
        lblNombre.setAlignment(Pos.CENTER);

        Label lblTag = new Label(
            esSiguiente  ? "⭐ SIGUIENTE"  :
            fueAtendida  ? "✔ ATENDIDA"   : "En espera"
        );
        lblTag.setFont(Font.font("Segoe UI", 10));
        lblTag.setStyle(
            esSiguiente ? "-fx-text-fill: #007bff; -fx-font-weight: bold;" :
            fueAtendida ? "-fx-text-fill: #28a745; -fx-font-weight: bold;" :
                          "-fx-text-fill: #888888;"
        );

        card.getChildren().addAll(circuloPane, lblNombre, lblTag);
        return card;
    }

    private int indiceDeNombre(String nombre) {
        for (int i = 0; i < NOMBRES.length; i++) {
            if (NOMBRES[i].equals(nombre)) return i;
        }
        return 0;
    }

    private void log(TextArea area, String msg) {
        area.appendText(msg + "\n");
        turnoPersonal++;
    }
}
