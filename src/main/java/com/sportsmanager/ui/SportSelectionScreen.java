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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class SportSelectionScreen {

    private VBox view;
    private Sport selectedSport = null;

    public SportSelectionScreen() {
        buildUI();
    }

    private void buildUI() {
        view = new VBox();
        view.setStyle("-fx-background-color: #1a1a2e;");

        // --- ÜST BAŞLIK BANDI ---
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(24, 40, 24, 40));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: #0f0f1a; -fx-border-color: #2a2a4a; "
                + "-fx-border-width: 0 0 1 0;");

        Label backArrow = new Label("← Back");
        backArrow.setStyle("-fx-font-size: 13px; -fx-text-fill: #a0a0b0; "
                + "-fx-cursor: hand;");
        backArrow.setOnMouseClicked(e -> MainApp.showMainMenu());

        Region topSpacer = new Region();
        HBox.setHgrow(topSpacer, Priority.ALWAYS);

        Label stepLabel = new Label("Step 1 of 2");
        stepLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #404060;");

        topBar.getChildren().addAll(backArrow, topSpacer, stepLabel);

        // --- ORTA İÇERİK ---
        VBox content = new VBox(32);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(60, 80, 40, 80));
        VBox.setVgrow(content, Priority.ALWAYS);

        Label title = new Label("Choose Your Sport");
        title.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; "
                + "-fx-text-fill: white; -fx-font-family: 'Segoe UI';");

        Label subtitle = new Label("Select the sport you want to manage");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #606080;");

        // Sport kartları
        ToggleGroup group = new ToggleGroup();

        ToggleButton footballBtn = createSportCard("⚽", "Football",
                "Premier League, La Liga, Serie A...", group);
        ToggleButton basketballBtn = createSportCard("🏀", "Basketball",
                "NBA, EuroLeague, FIBA...", group);

        HBox sportCards = new HBox(24, footballBtn, basketballBtn);
        sportCards.setAlignment(Pos.CENTER);

        // Alt butonlar
        Button startBtn = createPrimaryButton("Start Game →");
        startBtn.setOnAction(e -> {
            ToggleButton selected = (ToggleButton) group.getSelectedToggle();
            if (selected == null)
                return;

            selectedSport = (selected == footballBtn)
                    ? new FootballSport()
                    : new BasketballSport();

            GameFacade facade = new GameFacade(selectedSport);
            facade.initGame(8);

            LeagueDashboardScreen dashboard = new LeagueDashboardScreen(facade);
            javafx.scene.Scene scene = new javafx.scene.Scene(
                    dashboard.getView(), 900, 650);
            MainApp.primaryStage.setScene(scene);
        });

        content.getChildren().addAll(title, subtitle, sportCards, startBtn);

        view.getChildren().addAll(topBar, content);
    }

    private ToggleButton createSportCard(String icon, String name,
            String desc, ToggleGroup group) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(30, 20, 30, 20));

        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 40px;");

        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; "
                + "-fx-text-fill: white; -fx-font-family: 'Segoe UI';");

        Label descLabel = new Label(desc);
        descLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #606080; "
                + "-fx-text-alignment: center;");
        descLabel.setWrapText(true);
        descLabel.setMaxWidth(160);

        card.getChildren().addAll(iconLabel, nameLabel, descLabel);

        ToggleButton btn = new ToggleButton();
        btn.setGraphic(card);
        btn.setToggleGroup(group);
        btn.setPrefWidth(200);
        btn.setPrefHeight(180);
        btn.setStyle(
                "-fx-background-color: #16213e; "
                        + "-fx-border-color: #2a2a4a; "
                        + "-fx-border-width: 2px; "
                        + "-fx-border-radius: 8px; "
                        + "-fx-background-radius: 8px; "
                        + "-fx-cursor: hand;");

        // Seçili görünümü
        btn.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                btn.setStyle(
                        "-fx-background-color: #1e2d50; "
                                + "-fx-border-color: #e94560; "
                                + "-fx-border-width: 2px; "
                                + "-fx-border-radius: 8px; "
                                + "-fx-background-radius: 8px; "
                                + "-fx-cursor: hand;");
            } else {
                btn.setStyle(
                        "-fx-background-color: #16213e; "
                                + "-fx-border-color: #2a2a4a; "
                                + "-fx-border-width: 2px; "
                                + "-fx-border-radius: 8px; "
                                + "-fx-background-radius: 8px; "
                                + "-fx-cursor: hand;");
            }
        });

        return btn;
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
                        + "-fx-background-radius: 4px; "
                        + "-fx-cursor: hand;");
        return btn;
    }

    public VBox getView() {
        return view;
    }
}