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

        ToggleButton footballBtn = createToggleButton("Football", group);
        ToggleButton basketballBtn = createToggleButton("Basketball", group);

        HBox sportButtons = new HBox(20, footballBtn, basketballBtn);
        sportButtons.setAlignment(Pos.CENTER);

        Button startBtn = createButton("Start Game", "#e94560");
        Button backBtn = createButton("Back", "#16213e");

        startBtn.setOnAction(e -> {
            ToggleButton selected = (ToggleButton) group.getSelectedToggle();
            if (selected == null)
                return;

            Sport selectedSport;
            if (selected == footballBtn) {
                selectedSport = new FootballSport();
            } else {
                selectedSport = new BasketballSport();
            }

            GameFacade facade = new GameFacade(selectedSport);
            facade.initGame(8);

            LeagueDashboardScreen dashboard = new LeagueDashboardScreen(facade);
            javafx.scene.Scene scene = new javafx.scene.Scene(
                    dashboard.getView(), 900, 650);
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
        btn.setPrefWidth(180);
        btn.setPrefHeight(80);
        btn.setStyle("-fx-background-color: #16213e; "
                + "-fx-text-fill: white; "
                + "-fx-font-size: 16px; "
                + "-fx-border-color: #e94560; "
                + "-fx-border-width: 1px; "
                + "-fx-cursor: hand;");
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
        return btn;
    }

    public VBox getView() {
        return view;
    }
}