package com.sportsmanager.ui;

import com.sportsmanager.app.MainApp;
import com.sportsmanager.basketball.BasketballSport;
import com.sportsmanager.engine.GameFacade;
import com.sportsmanager.football.FootballSport;
import com.sportsmanager.sport.Sport;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
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
        view.setStyle("-fx-background-color: " + AppStyle.BG_MAIN + ";");
        view.setPrefHeight(650);
        view.setPrefWidth(900);

        Label title = new Label("Select Sport");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; "
                + "-fx-text-fill: " + AppStyle.ACCENT_BLUE + ";");

        Label subtitle = new Label("Choose the sport you want to manage");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: "
                + AppStyle.TEXT_SECONDARY + ";");

        ToggleGroup group = new ToggleGroup();
        ToggleButton footballBtn = createToggleButton("⚽  Football", group);
        ToggleButton basketballBtn = createToggleButton("🏀  Basketball", group);

        group.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            footballBtn.setStyle(AppStyle.BTN_NORMAL);
            basketballBtn.setStyle(AppStyle.BTN_NORMAL);
            if (newVal == footballBtn) footballBtn.setStyle(AppStyle.BTN_SELECTED);
            else if (newVal == basketballBtn) basketballBtn.setStyle(AppStyle.BTN_SELECTED);
        });

        HBox sportButtons = new HBox(20, footballBtn, basketballBtn);
        sportButtons.setAlignment(Pos.CENTER);

        Button startBtn = createActionButton("Start Game →", true);
        Button backBtn = createActionButton("Back", false);

        startBtn.setOnAction(e -> {
            ToggleButton selected = (ToggleButton) group.getSelectedToggle();
            if (selected == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Sport Selected");
                alert.setHeaderText("Please select a sport");
                alert.setContentText("Choose Football or Basketball.");
                alert.showAndWait();
                return;
            }

            Sport selectedSport = selected == footballBtn
                    ? new FootballSport() : new BasketballSport();

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
        btn.setStyle(AppStyle.BTN_NORMAL + "-fx-font-size: 18px;");
        return btn;
    }

    private Button createActionButton(String text, boolean primary) {
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