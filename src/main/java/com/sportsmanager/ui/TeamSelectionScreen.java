package com.sportsmanager.ui;

import com.sportsmanager.app.MainApp;
import com.sportsmanager.domain.Team;
import com.sportsmanager.engine.GameFacade;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class TeamSelectionScreen {

    private VBox view;
    private GameFacade facade;
    private Team selectedTeam;
    private Button activeBtn;

    private static final String BTN_NORMAL = "-fx-background-color: #16213e; -fx-text-fill: white; " +
            "-fx-font-size: 13px; -fx-border-color: #e94560; " +
            "-fx-border-width: 1px; -fx-cursor: hand;";

    private static final String BTN_SELECTED = "-fx-background-color: #e94560; -fx-text-fill: white; " +
            "-fx-font-size: 13px; -fx-font-weight: bold; " +
            "-fx-border-color: #ff6b6b; -fx-border-width: 2px; " +
            "-fx-effect: dropshadow(gaussian, #e94560, 10, 0.5, 0, 0); " +
            "-fx-cursor: hand;";

    public TeamSelectionScreen(GameFacade facade) {
        this.facade = facade;
        buildUI();
    }

    private void buildUI() {
        view = new VBox(25);
        view.setAlignment(Pos.CENTER);
        view.setPadding(new Insets(40));
        view.setStyle("-fx-background-color: #1a1a2e;");
        view.setPrefSize(900, 650);

        Label title = new Label("Select Your Team");
        title.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; "
                + "-fx-text-fill: #e94560;");

        Label subtitle = new Label("Choose the team you want to manage this season");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #a0a0b0;");

        // Team grid
        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);
        grid.setAlignment(Pos.CENTER);

        int col = 0;
        int row = 0;
        for (Team team : facade.getLeague().getTeams()) {
            Button btn = new Button(team.getName());
            btn.setPrefSize(180, 50);
            btn.setStyle(BTN_NORMAL);

            btn.setOnAction(e -> {
                if (activeBtn != null)
                    activeBtn.setStyle(BTN_NORMAL);
                activeBtn = btn;
                btn.setStyle(BTN_SELECTED);
                selectedTeam = team;
            });

            btn.setOnMouseEntered(e -> {
                if (btn != activeBtn) {
                    btn.setStyle("-fx-background-color: #2a2a4e; "
                            + "-fx-text-fill: white; -fx-font-size: 13px; "
                            + "-fx-border-color: #e94560; -fx-border-width: 1px; "
                            + "-fx-cursor: hand;");
                }
            });

            btn.setOnMouseExited(e -> {
                if (btn != activeBtn)
                    btn.setStyle(BTN_NORMAL);
            });

            grid.add(btn, col, row);
            col++;
            if (col == 4) {
                col = 0;
                row++;
            }
        }

        Button startBtn = new Button("Start Season →");
        startBtn.setPrefSize(250, 45);
        startBtn.setStyle("-fx-background-color: #e94560; -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-font-weight: bold; "
                + "-fx-cursor: hand;");

        startBtn.setOnMouseEntered(e -> startBtn.setStyle(
                "-fx-background-color: #ff6b6b; -fx-text-fill: white; "
                        + "-fx-font-size: 14px; -fx-font-weight: bold; "
                        + "-fx-cursor: hand;"));
        startBtn.setOnMouseExited(e -> startBtn.setStyle(
                "-fx-background-color: #e94560; -fx-text-fill: white; "
                        + "-fx-font-size: 14px; -fx-font-weight: bold; "
                        + "-fx-cursor: hand;"));

        startBtn.setOnAction(e -> {
            if (selectedTeam == null) {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.WARNING);
                alert.setTitle("No Team Selected");
                alert.setHeaderText("Please select a team");
                alert.setContentText(
                        "Choose a team to manage before starting.");
                alert.showAndWait();
                return;
            }

            facade.setPlayerTeam(selectedTeam);

            LeagueDashboardScreen dashboard = new LeagueDashboardScreen(facade);
            javafx.scene.Scene scene = new javafx.scene.Scene(
                    dashboard.getView(), 900, 650);
            MainApp.primaryStage.setScene(scene);
            MainApp.primaryStage.sizeToScene();
        });

        view.getChildren().addAll(title, subtitle, grid, startBtn);
    }

    public VBox getView() {
        return view;
    }
}