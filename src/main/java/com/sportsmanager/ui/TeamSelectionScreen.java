package com.sportsmanager.ui;

import com.sportsmanager.app.MainApp;
import com.sportsmanager.domain.Team;
import com.sportsmanager.engine.GameFacade;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class TeamSelectionScreen {

    private VBox view;
    private GameFacade facade;
    private Team selectedTeam;
    private Button activeBtn;

    public TeamSelectionScreen(GameFacade facade) {
        this.facade = facade;
        buildUI();
    }

    private void buildUI() {
        view = new VBox(25);
        view.setAlignment(Pos.CENTER);
        view.setPadding(new Insets(40));
        view.setStyle("-fx-background-color: " + AppStyle.BG_MAIN + ";");
        view.setPrefSize(900, 650);

        Label title = new Label("Select Your Team");
        title.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; "
                + "-fx-text-fill: " + AppStyle.ACCENT_BLUE + ";");

        Label subtitle = new Label(
                "Choose the team you want to manage this season");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: "
                + AppStyle.TEXT_SECONDARY + ";");

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);
        grid.setAlignment(Pos.CENTER);

        int col = 0, row = 0;
        for (Team team : facade.getLeague().getTeams()) {
            Button btn = new Button(team.getName());
            btn.setPrefSize(180, 50);
            btn.setStyle(AppStyle.BTN_NORMAL);

            btn.setOnAction(e -> {
                if (activeBtn != null) activeBtn.setStyle(AppStyle.BTN_NORMAL);
                activeBtn = btn;
                btn.setStyle(AppStyle.BTN_SELECTED);
                selectedTeam = team;
            });

            btn.setOnMouseEntered(e -> {
                if (btn != activeBtn) btn.setStyle(AppStyle.BTN_HOVER);
            });
            btn.setOnMouseExited(e -> {
                if (btn != activeBtn) btn.setStyle(AppStyle.BTN_NORMAL);
            });

            grid.add(btn, col, row);
            col++;
            if (col == 4) { col = 0; row++; }
        }

        Button startBtn = new Button("Start Season →");
        startBtn.setPrefSize(250, 45);
        startBtn.setStyle(AppStyle.BTN_PRIMARY);
        startBtn.setOnMouseEntered(e ->
                startBtn.setStyle(AppStyle.BTN_PRIMARY_HOVER));
        startBtn.setOnMouseExited(e ->
                startBtn.setStyle(AppStyle.BTN_PRIMARY));

        startBtn.setOnAction(e -> {
            if (selectedTeam == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Team Selected");
                alert.setHeaderText("Please select a team");
                alert.setContentText(
                        "Choose a team to manage before starting.");
                alert.showAndWait();
                return;
            }
            facade.setPlayerTeam(selectedTeam);
            LeagueDashboardScreen dashboard =
                    new LeagueDashboardScreen(facade);
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