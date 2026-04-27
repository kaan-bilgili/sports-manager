package com.sportsmanager.ui;

import com.sportsmanager.app.MainApp;
import com.sportsmanager.engine.GameFacade;
import com.sportsmanager.engine.GameSaveManager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class MainMenuScreen {

    private VBox view;

    public MainMenuScreen() {
        buildUI();
    }

    private void buildUI() {
        // Root: yatayda iki panel
        javafx.scene.layout.HBox root = new HBox();
        root.setStyle("-fx-background-color: #1a1a2e;");

        // ---sol panel
        VBox leftPanel = new VBox();
        leftPanel.setPrefWidth(420);
        leftPanel.setAlignment(Pos.CENTER_LEFT);
        leftPanel.setPadding(new Insets(60, 40, 60, 60));
        leftPanel.setStyle("-fx-background-color: #0f0f1a;");

        Label appLabel = new Label("SPORTS");
        appLabel.setStyle("-fx-font-size: 64px; -fx-font-weight: bold; "
                + "-fx-text-fill: #e94560; -fx-font-family: 'Segoe UI';");

        Label appLabel2 = new Label("MANAGER");
        appLabel2.setStyle("-fx-font-size: 64px; -fx-font-weight: bold; "
                + "-fx-text-fill: white; -fx-font-family: 'Segoe UI';");

        Separator sep = new Separator();
        sep.setMaxWidth(80);
        sep.setStyle("-fx-background-color: #e94560;");

        Label tagline = new Label("Build your dynasty.\nConquer every league.");
        tagline.setStyle("-fx-font-size: 15px; -fx-text-fill: #a0a0b0; "
                + "-fx-line-spacing: 4px;");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Label version = new Label("v1.0.0");
        version.setStyle("-fx-font-size: 11px; -fx-text-fill: #404058;");

        leftPanel.getChildren().addAll(appLabel, appLabel2, sep,
                new Region() {
                    {
                        setMinHeight(20);
                    }
                },
                tagline, spacer, version);

        // --- SAĞ PANEL: Menü butonları ---
        VBox rightPanel = new VBox(16);
        rightPanel.setAlignment(Pos.CENTER);
        rightPanel.setPadding(new Insets(60, 60, 60, 60));
        HBox.setHgrow(rightPanel, Priority.ALWAYS);

        Label menuTitle = new Label("Main Menu");
        menuTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; "
                + "-fx-text-fill: #e0e0f0; -fx-font-family: 'Segoe UI';");

        Label menuSub = new Label("What would you like to do?");
        menuSub.setStyle("-fx-font-size: 13px; -fx-text-fill: #606080;");

        Region menuSpacer = new Region();
        menuSpacer.setMinHeight(20);

        Button newGameBtn = createPrimaryButton("▶  New Game");
        Button loadGameBtn = createSecondaryButton("⟳  Load Game");
        Button exitBtn = createSecondaryButton("✕  Exit");

        newGameBtn.setOnAction(e -> {
            SportSelectionScreen sportSelection = new SportSelectionScreen();
            javafx.scene.Scene scene = new javafx.scene.Scene(
                    sportSelection.getView(), 900, 650);
            MainApp.primaryStage.setScene(scene);
        });

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

        rightPanel.getChildren().addAll(menuTitle, menuSub, menuSpacer,
                newGameBtn, loadGameBtn, exitBtn);

        root.getChildren().addAll(leftPanel, rightPanel);

        view = new VBox(root);
        VBox.setVgrow(root, Priority.ALWAYS);
    }

    private Button createPrimaryButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(280);
        btn.setPrefHeight(50);
        btn.setStyle(
                "-fx-background-color: #e94560; "
                        + "-fx-text-fill: white; "
                        + "-fx-font-size: 14px; "
                        + "-fx-font-weight: bold; "
                        + "-fx-font-family: 'Segoe UI'; "
                        + "-fx-cursor: hand; "
                        + "-fx-background-radius: 4px;");
        return btn;
    }

    private Button createSecondaryButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(280);
        btn.setPrefHeight(50);
        btn.setStyle(
                "-fx-background-color: transparent; "
                        + "-fx-text-fill: #c0c0d0; "
                        + "-fx-font-size: 14px; "
                        + "-fx-font-family: 'Segoe UI'; "
                        + "-fx-border-color: #2a2a4a; "
                        + "-fx-border-width: 1px; "
                        + "-fx-border-radius: 4px; "
                        + "-fx-background-radius: 4px; "
                        + "-fx-cursor: hand;");
        return btn;
    }

    public VBox getView() {
        return view;
    }
}