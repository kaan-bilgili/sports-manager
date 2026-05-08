package com.sportsmanager.ui;

import com.sportsmanager.app.MainApp;
import com.sportsmanager.basketball.BasketballSport;
import com.sportsmanager.engine.GameFacade;
import com.sportsmanager.football.FootballSport;
import com.sportsmanager.sport.Sport;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SportSelectionScreen {

    private VBox view;

    // Normal style
    private static final String BTN_NORMAL = "-fx-background-color: #16213e; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-border-color: #e94560; " +
            "-fx-border-width: 2px; " +
            "-fx-cursor: hand;";

    // Selected style
    private static final String BTN_SELECTED = "-fx-background-color: #e94560; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-border-color: #ff6b6b; " +
            "-fx-border-width: 3px; " +
            "-fx-effect: dropshadow(gaussian, #e94560, 15, 0.6, 0, 0); " +
            "-fx-cursor: hand;";

    public SportSelectionScreen() {
        buildUI();
    }

    private void buildUI() {
        view = new VBox(30);
        view.setAlignment(Pos.CENTER);
        view.setPadding(new Insets(50));
        view.setStyle("-fx-background-color: #1a1a2e;");
        view.setPrefHeight(650);
        view.setPrefWidth(900);

        Label title = new Label("Select Sport");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; "
                + "-fx-text-fill: #e94560;");

        Label subtitle = new Label("Choose the sport you want to manage");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #a0a0b0;");

        ToggleGroup group = new ToggleGroup();

        ToggleButton footballBtn = createToggleButton("⚽  Football", group);
        ToggleButton basketballBtn = createToggleButton("🏀  Basketball", group);

        // Update style on selection
        group.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            footballBtn.setStyle(BTN_NORMAL);
            basketballBtn.setStyle(BTN_NORMAL);
            if (newVal == footballBtn) {
                footballBtn.setStyle(BTN_SELECTED);
            } else if (newVal == basketballBtn) {
                basketballBtn.setStyle(BTN_SELECTED);
            }
        });

        HBox sportButtons = new HBox(20, footballBtn, basketballBtn);
        sportButtons.setAlignment(Pos.CENTER);

        Button startBtn = createButton("Start Game", "#e94560");
        Button backBtn = createButton("Back", "#16213e");

        startBtn.setOnAction(e -> {
            ToggleButton selected = (ToggleButton) group.getSelectedToggle();
            if (selected == null) {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.WARNING);
                alert.setTitle("No Sport Selected");
                alert.setHeaderText("Please select a sport");
                alert.setContentText(
                        "Choose Football or Basketball to continue.");
                alert.showAndWait();
                return;
            }

            Sport selectedSport;
            if (selected == footballBtn) {
                selectedSport = new FootballSport();
            } else {
                selectedSport = new BasketballSport();
            }

            GameFacade facade = new GameFacade(selectedSport);
            facade.initGame(8);

            TeamSelectionScreen teamSelection = new TeamSelectionScreen(facade);
            javafx.scene.Scene scene = new javafx.scene.Scene(
                    teamSelection.getView(), 900, 650);
            MainApp.primaryStage.setScene(scene);
            MainApp.primaryStage.sizeToScene();
        });

        backBtn.setOnAction(e -> MainApp.showMainMenu());

        VBox buttons = new VBox(10, startBtn, backBtn);
        buttons.setAlignment(Pos.CENTER);

        view.getChildren().addAll(title, subtitle, sportButtons, buttons);
    }

    private ToggleButton createToggleButton(String text, ToggleGroup group) {
        ToggleButton btn = new ToggleButton(text);
        btn.setToggleGroup(group);
        btn.setPrefWidth(200);
        btn.setPrefHeight(90);
        btn.setStyle(BTN_NORMAL);
        return btn;
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
        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: #e94560; "
                        + "-fx-text-fill: white; "
                        + "-fx-font-size: 14px; "
                        + "-fx-border-color: #e94560; "
                        + "-fx-border-width: 1px; "
                        + "-fx-cursor: hand;"));
        btn.setOnMouseExited(e -> btn.setStyle(
                "-fx-background-color: " + color + "; "
                        + "-fx-text-fill: white; "
                        + "-fx-font-size: 14px; "
                        + "-fx-border-color: #e94560; "
                        + "-fx-border-width: 1px; "
                        + "-fx-cursor: hand;"));
        return btn;
    }

    public VBox getView() {
        return view;
    }
}