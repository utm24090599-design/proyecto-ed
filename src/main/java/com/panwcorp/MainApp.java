package com.panwcorp;

import com.panwcorp.view.Turnosview;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        new Turnosview().mostrar(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}