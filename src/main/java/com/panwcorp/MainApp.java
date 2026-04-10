package com.panwcorp;

import com.panwcorp.model.HistorialPedidos;
import com.panwcorp.view.Turnosview;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    private HistorialPedidos historial;

    @Override
    public void start(Stage stage) {
        // Instanciar el historial de pedidos
        historial = new HistorialPedidos();
        new Turnosview(historial).mostrar(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}