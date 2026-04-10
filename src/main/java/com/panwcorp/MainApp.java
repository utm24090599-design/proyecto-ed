package com.panwcorp;

import com.panwcorp.view.EstacionesView;
import com.panwcorp.view.HistorialView;
import com.panwcorp.view.Turnosview;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        new Turnosview().mostrar(stage);

        Stage ventanaEstaciones = new Stage();
        new EstacionesView().mostrar(ventanaEstaciones);

        Stage ventanaHistorial = new Stage();
        new HistorialView().mostrar(ventanaHistorial);
    }

    public static void main(String[] args) {
        launch();
    }
}