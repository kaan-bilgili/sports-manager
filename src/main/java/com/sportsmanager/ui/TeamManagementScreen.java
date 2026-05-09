package com.sportsmanager.ui;

import com.sportsmanager.app.MainApp;
import com.sportsmanager.domain.Player;
import com.sportsmanager.domain.Team;
import com.sportsmanager.engine.GameFacade;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class TeamManagementScreen {

    private BorderPane view;
    private GameFacade facade;
    private Team selectedTeam;
    private Button activeTeamBtn;

    public TeamManagementScreen(GameFacade facade) {
        this.facade = facade;
        this.selectedTeam = facade.getLeague().getTeams().get(0);
        buildUI();
    }

    private void buildUI() {
        view = new BorderPane();
        view.setPrefSize(900, 650);
        view.setStyle("-fx-background-color: " + AppStyle.BG_MAIN + ";");
        view.setTop(buildHeader());
        view.setLeft(buildTeamList());
        view.setCenter(buildPlayerTable());
    }

    private Label buildHeader() {
        Label lbl = new Label("Team Management — " + selectedTeam.getName());
        lbl.setMaxWidth(Double.MAX_VALUE);
        lbl.setStyle("-fx-background-color: " + AppStyle.BG_PANEL + "; "
                + "-fx-text-fill: " + AppStyle.ACCENT_BLUE + "; "
                + "-fx-font-size: 16px; -fx-font-weight: bold; "
                + "-fx-padding: 12px;");
        return lbl;
    }

    private VBox buildTeamList() {
        VBox box = new VBox(8);
        box.setPrefWidth(175);
        box.setStyle("-fx-background-color: " + AppStyle.BG_PANEL
                + "; -fx-padding: 12px;");

        Label title = new Label("Teams");
        title.setStyle("-fx-text-fill: " + AppStyle.ACCENT_BLUE
                + "; -fx-font-weight: bold; -fx-font-size: 14px;");
        box.getChildren().add(title);

        for (Team team : facade.getLeague().getTeams()) {
            Button btn = new Button(team.getName());
            btn.setPrefSize(155, 34);
            btn.setStyle(AppStyle.BTN_NORMAL);

            if (team == selectedTeam) {
                btn.setStyle(AppStyle.BTN_SELECTED);
                activeTeamBtn = btn;
            }

            btn.setOnAction(e -> {
                if (activeTeamBtn != null)
                    activeTeamBtn.setStyle(AppStyle.BTN_NORMAL);
                activeTeamBtn = btn;
                btn.setStyle(AppStyle.BTN_SELECTED);
                selectedTeam = team;
                view.setTop(buildHeader());
                view.setCenter(buildPlayerTable());
            });

            btn.setOnMouseEntered(e -> {
                if (btn != activeTeamBtn) btn.setStyle(AppStyle.BTN_HOVER);
            });
            btn.setOnMouseExited(e -> {
                if (btn != activeTeamBtn) btn.setStyle(AppStyle.BTN_NORMAL);
            });

            box.getChildren().add(btn);
        }

        Button backBtn = new Button("← Back");
        backBtn.setPrefSize(155, 34);
        backBtn.setStyle(AppStyle.BTN_BACK);
        backBtn.setOnAction(e -> {
            LeagueDashboardScreen dashboard = new LeagueDashboardScreen(facade);
            javafx.scene.Scene scene = new javafx.scene.Scene(
                    dashboard.getView(), 900, 650);
            MainApp.primaryStage.setScene(scene);
            MainApp.primaryStage.sizeToScene();
        });

        box.getChildren().add(backBtn);
        return box;
    }

    private VBox buildPlayerTable() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(15));

        boolean isFootball = selectedTeam instanceof
                com.sportsmanager.football.FootballTeam;
        boolean isBasketball = selectedTeam instanceof
                com.sportsmanager.basketball.BasketballTeam;

        int totalSize = selectedTeam.getPlayers().size();
        int availableSize = selectedTeam.getAvailablePlayers().size();
        int injuredSize = totalSize - availableSize;
        int matchdaySize = isFootball ? 18 : isBasketball ? 12 : totalSize;
        int starterSize = isFootball ? 11 : isBasketball ? 5 : totalSize;
        int benchSize = isFootball ? 7 : isBasketball ? 7 : 0;

        HBox info = new HBox(15);
        info.setAlignment(Pos.CENTER_LEFT);

        addInfoLabel(info, "Total: " + totalSize, "white");
        addInfoLabel(info, "Matchday: " + matchdaySize, AppStyle.ACCENT_BLUE);
        addInfoLabel(info, "Starting: " + starterSize, AppStyle.ACCENT_GREEN);
        addInfoLabel(info, "Bench: " + benchSize, "#FFE0B2");
        addInfoLabel(info, "Injured: " + injuredSize, AppStyle.ACCENT_CORAL);

        TableView<Player> table = new TableView<>();
        table.setStyle("-fx-background-color: " + AppStyle.BG_PANEL + ";");
        table.setPrefHeight(500);

        TableColumn<Player, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getName()));
        nameCol.setPrefWidth(140);

        TableColumn<Player, String> posCol = new TableColumn<>("Position");
        posCol.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getPosition()));
        posCol.setPrefWidth(130);

        TableColumn<Player, Integer> skillCol = new TableColumn<>("Skill");
        skillCol.setCellValueFactory(d ->
                new SimpleIntegerProperty(
                        d.getValue().getSkillLevel()).asObject());
        skillCol.setPrefWidth(60);

        TableColumn<Player, String> roleCol = new TableColumn<>("Role");
        roleCol.setPrefWidth(110);
        roleCol.setCellValueFactory(d -> {
            Player player = d.getValue();
            if (player.isInjured()) return new SimpleStringProperty("🤕 Injured");

            List<Player> starters;
            List<Player> bench;

            if (isBasketball) {
                com.sportsmanager.basketball.BasketballTeam bt =
                        (com.sportsmanager.basketball.BasketballTeam) selectedTeam;
                starters = bt.getStarters();
                bench = bt.getBench();
            } else if (isFootball) {
                com.sportsmanager.football.FootballTeam ft =
                        (com.sportsmanager.football.FootballTeam) selectedTeam;
                starters = ft.getStartingXI();
                bench = ft.getBench();
            } else {
                return new SimpleStringProperty("Unknown");
            }

            if (starters.contains(player))
                return new SimpleStringProperty("✅ Starting");
            else if (bench.contains(player))
                return new SimpleStringProperty("🔄 Bench");
            else
                return new SimpleStringProperty("❌ Excluded");
        });

        TableColumn<Player, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().isInjured()
                        ? "INJURED (" + d.getValue()
                                .getInjuryGamesRemaining() + ")"
                        : "Available"));
        statusCol.setPrefWidth(130);

        table.getColumns().addAll(nameCol, posCol, skillCol, roleCol, statusCol);
        table.getItems().addAll(selectedTeam.getPlayers());

        container.getChildren().addAll(info, table);
        return container;
    }

    private void addInfoLabel(HBox box, String text, String color) {
        Label lbl = new Label(text);
        lbl.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 12px;");
        box.getChildren().add(lbl);
    }

    public BorderPane getView() {
        return view;
    }
}