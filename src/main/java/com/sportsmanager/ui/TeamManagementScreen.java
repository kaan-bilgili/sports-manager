package com.sportsmanager.ui;

import java.util.List;

import com.sportsmanager.app.MainApp;
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
    private Button activeTeamBtn;

    private static final String TEAM_BTN_NORMAL =
            "-fx-background-color: #1a1a2e; -fx-text-fill: white; " +
            "-fx-border-color: #e94560; -fx-border-width: 1px; " +
            "-fx-cursor: hand; -fx-font-size: 12px;";

    private static final String TEAM_BTN_SELECTED =
            "-fx-background-color: #e94560; -fx-text-fill: white; " +
            "-fx-font-weight: bold; " +
            "-fx-border-color: #ff6b6b; -fx-border-width: 2px; " +
            "-fx-effect: dropshadow(gaussian, #e94560, 10, 0.5, 0, 0); " +
            "-fx-cursor: hand; -fx-font-size: 12px;";

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
            btn.setStyle(TEAM_BTN_NORMAL);

            // First team selected by default
            if (team == selectedTeam) {
                btn.setStyle(TEAM_BTN_SELECTED);
                activeTeamBtn = btn;
            }

            btn.setOnAction(e -> {
                // Update selected state
                if (activeTeamBtn != null) {
                    activeTeamBtn.setStyle(TEAM_BTN_NORMAL);
                }
                activeTeamBtn = btn;
                btn.setStyle(TEAM_BTN_SELECTED);

                selectedTeam = team;
                view.setTop(buildHeader());
                view.setCenter(buildPlayerTable());
            });

            btn.setOnMouseEntered(e -> {
                if (btn != activeTeamBtn) {
                    btn.setStyle("-fx-background-color: #2a2a4e; "
                            + "-fx-text-fill: white; "
                            + "-fx-border-color: #e94560; -fx-border-width: 1px; "
                            + "-fx-cursor: hand; -fx-font-size: 12px;");
                }
            });

            btn.setOnMouseExited(e -> {
                if (btn != activeTeamBtn) {
                    btn.setStyle(TEAM_BTN_NORMAL);
                }
            });

            box.getChildren().add(btn);
        }

        Button backBtn = new Button("← Back");
        backBtn.setPrefSize(155, 34);
        backBtn.setStyle("-fx-background-color: #e94560; -fx-text-fill: white; "
                + "-fx-border-color: #e94560; -fx-border-width: 1px; "
                + "-fx-cursor: hand; -fx-font-size: 12px;");
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

    // Info bar
    HBox info = new HBox(15);
    info.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

    Label totalLbl = new Label("Total: " + totalSize);
    totalLbl.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

    Label matchdayLbl = new Label("Matchday: " + matchdaySize);
    matchdayLbl.setStyle("-fx-text-fill: #4fc3f7; -fx-font-size: 12px;");

    Label starterLbl = new Label("Starting: " + starterSize);
    starterLbl.setStyle("-fx-text-fill: #4caf50; -fx-font-size: 12px;");

    Label benchLbl = new Label("Bench: " + benchSize);
    benchLbl.setStyle("-fx-text-fill: #ffeb3b; -fx-font-size: 12px;");

    Label injuredLbl = new Label("Injured: " + injuredSize);
    injuredLbl.setStyle("-fx-text-fill: #e94560; -fx-font-size: 12px;");

    info.getChildren().addAll(
            totalLbl, matchdayLbl, starterLbl, benchLbl, injuredLbl);

    // Player table
    TableView<Player> table = new TableView<>();
    table.setStyle("-fx-background-color: #16213e;");
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
    roleCol.setPrefWidth(100);
    roleCol.setCellValueFactory(d -> {
    Player player = d.getValue();

    if (player.isInjured()) {
        return new SimpleStringProperty("🤕 Injured");
    }

    List<Player> starters;
    List<Player> bench;

    if (selectedTeam instanceof com.sportsmanager.basketball.BasketballTeam) {
        com.sportsmanager.basketball.BasketballTeam bt =
                (com.sportsmanager.basketball.BasketballTeam) selectedTeam;
        starters = bt.getStarters();
        bench = bt.getBench();
    } else if (selectedTeam instanceof com.sportsmanager.football.FootballTeam) {
        com.sportsmanager.football.FootballTeam ft =
                (com.sportsmanager.football.FootballTeam) selectedTeam;
        starters = ft.getStartingXI();
        bench = ft.getBench();
    } else {
        return new SimpleStringProperty("Unknown");
    }

    if (starters.contains(player)) {
        return new SimpleStringProperty("✅ Starting");
    } else if (bench.contains(player)) {
        return new SimpleStringProperty("🔄 Bench");
    } else {
        return new SimpleStringProperty("❌ Excluded");
    }
});

    TableColumn<Player, String> statusCol = new TableColumn<>("Status");
    statusCol.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().isInjured()
                    ? "INJURED (" + d.getValue()
                            .getInjuryGamesRemaining() + ")"
                    : "Available"));
    statusCol.setPrefWidth(130);

    table.getColumns().addAll(
            nameCol, posCol, skillCol, roleCol, statusCol);
    table.getItems().addAll(selectedTeam.getPlayers());

    container.getChildren().addAll(info, table);
    return container;
}

    public BorderPane getView() {
        return view;
    }
}