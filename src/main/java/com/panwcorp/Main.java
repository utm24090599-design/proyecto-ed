package com.panwcorp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Crear contenedor principal
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        // Crear TabPane
        TabPane tabPane = new TabPane();

        // Pestaña 1: Bienvenida
        Tab tab1 = new Tab("Bienvenida");
        VBox tab1Content = new VBox(10);
        tab1Content.setPadding(new Insets(15));

        Label nameLabel = new Label("Nombre:");
        nameLabel.getStyleClass().add("label-title");

        TextField nameInput = new TextField();
        nameInput.setPromptText("Ingresa tu nombre");
        nameInput.getStyleClass().add("input-field");

        Button submitButton = new Button("Enviar");
        submitButton.getStyleClass().add("btn-primary");

        Label messageLabel = new Label("");
        messageLabel.getStyleClass().add("message");

        // Lógica del botón
        submitButton.setOnAction(e -> {
            String name = nameInput.getText().trim();
            if (!name.isEmpty()) {
                messageLabel.setText("¡Hola, " + name + "!");
            } else {
                messageLabel.setText("Por favor, ingresa tu nombre.");
            }
        });

        tab1Content.getChildren().addAll(nameLabel, nameInput, submitButton, messageLabel);
        tab1.setContent(tab1Content);

        // Pestaña 2: Info
        Tab tab2 = new Tab("Info");
        VBox tab2Content = new VBox(10);
        tab2Content.setPadding(new Insets(15));
        tab2Content.setAlignment(javafx.geometry.Pos.CENTER);

        Label infoLabel = new Label("Esta es la pestaña de información.");
        infoLabel.getStyleClass().add("label-info");

        Button exampleButton = new Button("Botón de ejemplo");
        exampleButton.getStyleClass().add("btn-secondary");

        tab2Content.getChildren().addAll(infoLabel, exampleButton);
        tab2.setContent(tab2Content);

        // Añadir pestañas al TabPane
        tabPane.getTabs().addAll(tab1, tab2);

        // Añadir TabPane al root
        root.getChildren().add(tabPane);

        // Escena y mostrar
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("JavaFX sin FXML");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}