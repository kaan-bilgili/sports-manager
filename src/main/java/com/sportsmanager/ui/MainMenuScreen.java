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
        view.setStyle("-fx-background-color: " + AppStyle.BG_MAIN + ";");
        view.setPrefHeight(650);
        view.setPrefWidth(900);

        Label title = new Label("Sports Manager");
        title.setStyle("-fx-font-size: 42px; -fx-font-weight: bold; "
                + "-fx-text-fill: " + AppStyle.ACCENT_ORANGE + ";");

        Label subtitle = new Label("Manage your team to glory");
        subtitle.setStyle("-fx-font-size: 16px; -fx-text-fill: "
                + AppStyle.TEXT_SECONDARY + ";");

        Button newGameBtn = createButton("New Game", true);
        Button loadGameBtn = createButton("Load Game", false);
        Button exitBtn = createButton("Exit", false);

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
                    LeagueDashboardScreen dashboard =
                            new LeagueDashboardScreen(facade);
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

        // Footer
        Label footer = new Label("Team Trinity  •  CE 216  •  Spring 2026");
        footer.setStyle("-fx-font-size: 11px; -fx-text-fill: #475569;");

        view.getChildren().addAll(title, subtitle, buttons, footer);
    }

    private Button createButton(String text, boolean primary) {
        Button btn = new Button(text);
        btn.setPrefWidth(250);
        btn.setPrefHeight(45);
        btn.setStyle(primary ? AppStyle.BTN_PRIMARY : AppStyle.BTN_NORMAL);
        btn.setOnMouseEntered(e -> btn.setStyle(
                primary ? AppStyle.BTN_PRIMARY_HOVER : AppStyle.BTN_HOVER));
        btn.setOnMouseExited(e -> btn.setStyle(
                primary ? AppStyle.BTN_PRIMARY : AppStyle.BTN_NORMAL));
        return btn;
    }

    public VBox getView() {
        return view;
    }
}