package com.sportsmanager.ui;

import com.sportsmanager.app.MainApp;
import com.sportsmanager.engine.GameFacade;
import com.sportsmanager.engine.GameSaveManager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainMenuScreen {

    private VBox view;

    public MainMenuScreen() {
        buildUI();
    }

    private void buildUI() {
        view = new VBox(20);
        view.setAlignment(Pos.CENTER);
        view.setPadding(new Insets(50));
        view.setStyle("-fx-background-color: #1a1a2e;");
        view.setPrefHeight(650);
        view.setPrefWidth(900);

        Label title = new Label("Sports Manager");
        title.setStyle("-fx-font-size: 42px; -fx-font-weight: bold; "
                + "-fx-text-fill: #e94560;");

        Label subtitle = new Label("Manage your team to glory");
        subtitle.setStyle("-fx-font-size: 16px; -fx-text-fill: #a0a0b0;");

        Button newGameBtn = createButton("New Game", "#e94560");
        Button loadGameBtn = createButton("Load Game", "#16213e");
        Button exitBtn = createButton("Exit", "#16213e");

        newGameBtn.setOnAction(e -> MainApp.showSportSelection());

        loadGameBtn.setOnAction(e -> {
            GameSaveManager saveManager = new GameSaveManager();
            if (!saveManager.saveExists()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Load Game");
                alert.setHeaderText("No save file found");
                alert.setContentText("Please start a new game first.");
                alert.showAndWait();
            } else {
                try {
                    GameFacade facade = saveManager.load();
                    LeagueDashboardScreen dashboard = new LeagueDashboardScreen(facade);
                    javafx.scene.Scene scene = new javafx.scene.Scene(
                            dashboard.getView(), 900, 650);
                    MainApp.primaryStage.setScene(scene);
                    MainApp.primaryStage.sizeToScene();
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Load Error");
                    alert.setHeaderText("Could not load save file");
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
                }
            }
        });

        exitBtn.setOnAction(e -> System.exit(0));

        VBox buttons = new VBox(10, newGameBtn, loadGameBtn, exitBtn);
        buttons.setAlignment(Pos.CENTER);

        view.getChildren().addAll(title, subtitle, buttons);
    }

    private Button createButton(String text, String color) {
        Button btn = new Button(text);
        btn.setPrefWidth(250);
        btn.setPrefHeight(45);
        btn.setStyle("-fx-background-color: " + color + "; "
                + "-fx-text-fill: white; "
                + "-fx-font-size: 14px; "
                + "-fx-border-color: #e94560; "
                + "-fx-border-width: 1px; "
                + "-fx-cursor: hand;");
        return btn;
    }

    public VBox getView() {
        return view;
    }
}