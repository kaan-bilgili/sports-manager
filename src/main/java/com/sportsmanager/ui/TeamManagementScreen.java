package com.sportsmanager.ui;

import com.sportsmanager.domain.Player;
import com.sportsmanager.domain.Team;
import com.sportsmanager.engine.GameFacade;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TeamManagementScreen {

    private BorderPane view;
    private GameFacade facade;
    private Team selectedTeam;

    public TeamManagementScreen(GameFacade facade) {
        this.facade = facade;
        this.selectedTeam = facade.getLeague().getTeams().get(0);
        buildUI();
    }

    private void buildUI() {
        view = new BorderPane();
        view.setPrefSize(900, 650);
        view.setStyle("-fx-background-color: #1a1a2e;");
        view.setTop(buildHeader());
        view.setLeft(buildTeamList());
        view.setCenter(buildPlayerTable());
    }

    private Label buildHeader() {
        Label lbl = new Label("Team Management — " + selectedTeam.getName());
        lbl.setMaxWidth(Double.MAX_VALUE);
        lbl.setStyle("-fx-background-color: #16213e; -fx-text-fill: white; "
                + "-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 12px;");
        return lbl;
    }

    private VBox buildTeamList() {
        VBox box = new VBox(8);
        box.setPrefWidth(175);
        box.setStyle("-fx-background-color: #16213e; -fx-padding: 12px;");

        Label title = new Label("Teams");
        title.setStyle("-fx-text-fill: #e94560; -fx-font-weight: bold; "
                + "-fx-font-size: 14px;");
        box.getChildren().add(title);

        for (Team team : facade.getLeague().getTeams()) {
            Button btn = new Button(team.getName());
            btn.setPrefSize(155, 34);
            btn.setStyle("-fx-background-color: #1a1a2e; -fx-text-fill: white; "
                    + "-fx-border-color: #e94560; -fx-border-width: 1px; "
                    + "-fx-cursor: hand; -fx-font-size: 12px;");
            btn.setOnAction(e -> {
                selectedTeam = team;
                view.setTop(buildHeader());
                view.setCenter(buildPlayerTable());
            });
            box.getChildren().add(btn);
        }
        return box;
    }

    private VBox buildPlayerTable() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(15));

        HBox info = new HBox(20);
        Label total = new Label("Total: " + selectedTeam.getPlayers().size());
        total.setStyle("-fx-text-fill: white;");
        Label available = new Label("Available: "
                + selectedTeam.getAvailablePlayers().size());
        available.setStyle("-fx-text-fill: #4caf50;");
        Label injured = new Label("Injured: "
                + (selectedTeam.getPlayers().size()
                - selectedTeam.getAvailablePlayers().size()));
        injured.setStyle("-fx-text-fill: #e94560;");
        info.getChildren().addAll(total, available, injured);

        TableView<Player> table = new TableView<>();
        table.setStyle("-fx-background-color: #16213e;");
        table.setPrefHeight(530);

        TableColumn<Player, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getName()));
        nameCol.setPrefWidth(160);

        TableColumn<Player, String> posCol = new TableColumn<>("Position");
        posCol.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getPosition()));
        posCol.setPrefWidth(130);

        TableColumn<Player, Integer> skillCol = new TableColumn<>("Skill");
        skillCol.setCellValueFactory(d ->
                new SimpleIntegerProperty(d.getValue().getSkillLevel()).asObject());
        skillCol.setPrefWidth(80);

        TableColumn<Player, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().isInjured()
                        ? "INJURED (" + d.getValue().getInjuryGamesRemaining() + ")"
                        : "Available"));
        statusCol.setPrefWidth(150);

        table.getColumns().addAll(nameCol, posCol, skillCol, statusCol);
        table.getItems().addAll(selectedTeam.getPlayers());

        container.getChildren().addAll(info, table);
        return container;
    }

    public BorderPane getView() {
        return view;
    }
}