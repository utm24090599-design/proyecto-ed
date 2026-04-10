package com.panwcorp.view;

import com.panwcorp.model.Catalogo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CatalogoView {

    private final Catalogo catalogo = new Catalogo();

    private ListView<String> listaProductos;
    private Label lblTotalProductos;

    public void mostrar(Stage stage) {
        // HEADER
        Label titulo = new Label("Catálogo de Productos — Lista Enlazada");
        titulo.getStyleClass().add("titulo");
        HBox header = new HBox(titulo);
        header.setAlignment(Pos.CENTER);
        header.getStyleClass().add("header");

        // CARD: Información del catálogo
        lblTotalProductos = new Label("0");
        lblTotalProductos.getStyleClass().add("valor-espera");
        Label etqTotal = new Label("Productos en catálogo");
        etqTotal.getStyleClass().add("etiqueta-card");
        VBox cardTotal = new VBox(5, etqTotal, lblTotalProductos);
        cardTotal.getStyleClass().add("card-espera");
        cardTotal.setAlignment(Pos.CENTER);

        HBox panelInfo = new HBox(16, cardTotal);
        panelInfo.setAlignment(Pos.CENTER);
        panelInfo.setPadding(new Insets(20, 30, 10, 30));

        // CONTROLES PARA AGREGAR PRODUCTO
        Label lblNombre = new Label("Nombre:");
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Ej: Café Americano");

        Label lblPrecio = new Label("Precio:");
        TextField txtPrecio = new TextField();
        txtPrecio.setPromptText("Ej: 2.50");

        Label lblPosicion = new Label("Posición:");
        TextField txtPosicion = new TextField();
        txtPosicion.setPromptText("Ej: 0 (inicio), dejar vacío para final");

        Button btnAgregar = new Button("+ Agregar Producto");
        btnAgregar.getStyleClass().add("btn-agregar");

        HBox panelAgregar = new HBox(10, lblNombre, txtNombre, lblPrecio, txtPrecio, lblPosicion, txtPosicion, btnAgregar);
        panelAgregar.setAlignment(Pos.CENTER);
        panelAgregar.setPadding(new Insets(10, 30, 10, 30));

        // CONTROLES PARA ELIMINAR PRODUCTO
        Label lblEliminar = new Label("Eliminar producto:");
        TextField txtEliminar = new TextField();
        txtEliminar.setPromptText("Nombre del producto a eliminar");

        Button btnEliminar = new Button("🗑 Eliminar Producto");
        btnEliminar.getStyleClass().add("btn-atender");

        HBox panelEliminar = new HBox(10, lblEliminar, txtEliminar, btnEliminar);
        panelEliminar.setAlignment(Pos.CENTER);
        panelEliminar.setPadding(new Insets(0, 30, 10, 30));

        // LISTA DE PRODUCTOS
        Label lblTituloLista = new Label("Catálogo de productos:");
        lblTituloLista.getStyleClass().add("titulo-lista");

        listaProductos = new ListView<>();
        listaProductos.getStyleClass().add("lista-turnos");
        listaProductos.setPrefHeight(250);

        VBox panelLista = new VBox(8, lblTituloLista, listaProductos);
        panelLista.setPadding(new Insets(0, 30, 15, 30));

        // LOG
        Label lblLog = new Label("Registro de operaciones:");
        lblLog.getStyleClass().add("titulo-lista");

        TextArea logArea = new TextArea();
        logArea.setEditable(false);
        logArea.getStyleClass().add("log-area");
        logArea.setPrefRowCount(5);

        VBox panelLog = new VBox(5, lblLog, logArea);
        panelLog.setPadding(new Insets(0, 30, 20, 30));

        // LAYOUT PRINCIPAL
        VBox root = new VBox(header, panelInfo, panelAgregar, panelEliminar, panelLista, panelLog);
        root.getStyleClass().add("root");

        // LÓGICA DE BOTONES
        btnAgregar.setOnAction(e -> {
            String nombre = txtNombre.getText().trim();
            String precioStr = txtPrecio.getText().trim();
            String posicionStr = txtPosicion.getText().trim();
            if (nombre.isEmpty() || precioStr.isEmpty()) {
                log(logArea, "[Catálogo] Error: Nombre y precio son requeridos.");
                log(logArea, "---");
                return;
            }
            try {
                double precio = Double.parseDouble(precioStr);
                int posicion = posicionStr.isEmpty() ? catalogo.getSize() : Integer.parseInt(posicionStr);
                if (catalogo.agregarEnPosicion(nombre, precio, posicion)) {
                    actualizarUI();
                    log(logArea, "[Catálogo] Producto agregado en posición " + posicion + ": " + nombre + " - $" + precio);
                    log(logArea, "[Catálogo] Total productos: " + catalogo.getSize());
                    log(logArea, "---");
                    txtNombre.clear();
                    txtPrecio.clear();
                    txtPosicion.clear();
                } else {
                    log(logArea, "[Catálogo] Error: Posición inválida. Debe estar entre 0 y " + catalogo.getSize());
                    log(logArea, "---");
                }
            } catch (NumberFormatException ex) {
                log(logArea, "[Catálogo] Error: Precio y posición deben ser números válidos.");
                log(logArea, "---");
            }
        });

        btnEliminar.setOnAction(e -> {
            String nombre = txtEliminar.getText().trim();
            if (nombre.isEmpty()) {
                log(logArea, "[Catálogo] Error: Nombre del producto es requerido.");
                log(logArea, "---");
                return;
            }
            if (catalogo.eliminar(nombre)) {
                actualizarUI();
                log(logArea, "[Catálogo] Producto eliminado: " + nombre);
                log(logArea, "[Catálogo] Total productos: " + catalogo.getSize());
                log(logArea, "---");
                txtEliminar.clear();
            } else {
                log(logArea, "[Catálogo] Error: Producto '" + nombre + "' no encontrado.");
                log(logArea, "---");
            }
        });

        // ESCENA
        Scene scene = new Scene(root, 800, 700);
        scene.getStylesheets().add(
            getClass().getResource("/styles.css").toExternalForm()
        );

        stage.setTitle("Catálogo de Productos — Lista Enlazada");
        stage.setScene(scene);

        // Inicializar con algunos productos de ejemplo
        inicializarCatalogo();
        actualizarUI();

        stage.show();
    }

    private void inicializarCatalogo() {
        catalogo.agregar("Café Americano", 2.50);
        catalogo.agregar("Té Verde", 2.00);
        catalogo.agregar("Sándwich de Jamón", 5.00);
        catalogo.agregar("Pastel de Chocolate", 3.50);
        catalogo.agregar("Agua Mineral", 1.00);
    }

    private void actualizarUI() {
        listaProductos.getItems().setAll(catalogo.obtenerListaParaVista());
        lblTotalProductos.setText(String.valueOf(catalogo.getSize()));
    }

    private void log(TextArea area, String msg) {
        area.appendText(msg + "\n");
    }
}