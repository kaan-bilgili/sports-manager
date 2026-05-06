package com.sportsmanager.ui;

import com.sportsmanager.app.MainApp;
import com.sportsmanager.domain.StandingEntry;
import com.sportsmanager.engine.GameFacade;
import com.sportsmanager.engine.GameSaveManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class LeagueDashboardScreen {

    private BorderPane view;
    private GameFacade facade;

    public LeagueDashboardScreen(GameFacade facade) {
        this.facade = facade;
        buildUI();
    }

    private void buildUI() {
        view = new BorderPane();
        view.setPrefSize(900, 650);
        view.setStyle("-fx-background-color: #1a1a2e;");
        view.setTop(buildHeader());
        view.setLeft(buildSidebar());
        view.setCenter(buildStandings());
    }

    private Label buildHeader() {
        Label lbl = new Label(facade.getLeague().getName()
                + " | Week " + facade.getSeason().getCurrentWeek());
        lbl.setMaxWidth(Double.MAX_VALUE);
        lbl.setStyle("-fx-background-color: #16213e; -fx-text-fill: white; "
                + "-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 12px;");
        return lbl;
    }

    private VBox buildSidebar() {
        VBox box = new VBox(8);
        box.setPrefWidth(155);
        box.setStyle("-fx-background-color: #16213e; -fx-padding: 12px;");

        Button s = btn("Standings");
        Button f = btn("Fixture");
        Button t = btn("Team");
        Button n = btn("Next Match");
        Button sv = btn("Save Game");
        Button m = btn("Main Menu");

        s.setOnAction(e -> view.setCenter(buildStandings()));
        f.setOnAction(e -> view.setCenter(buildFixture()));

        t.setOnAction(e -> {
            TeamManagementScreen team = new TeamManagementScreen(facade);
            javafx.scene.Scene scene = new javafx.scene.Scene(
                    team.getView(), 900, 650);
            MainApp.primaryStage.setScene(scene);
            MainApp.primaryStage.sizeToScene();
        });

        n.setOnAction(e -> {
            if (!facade.isSeasonFinished()) {
                MatchSimulationScreen sim = new MatchSimulationScreen(facade);
                javafx.scene.Scene scene = new javafx.scene.Scene(
                        sim.getView(), 900, 650);
                MainApp.primaryStage.setScene(scene);
                MainApp.primaryStage.sizeToScene();
            } else {
                new Alert(Alert.AlertType.INFORMATION,
                        "Champion: " + facade.getLeader().getName()).show();
            }
        });

        sv.setOnAction(e -> {
            try {
                new GameSaveManager().save(facade);
                new Alert(Alert.AlertType.INFORMATION, "Saved!").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
            }
        });

        m.setOnAction(e -> MainApp.showMainMenu());

        box.getChildren().addAll(s, f, t, n, sv, m);
        return box;
    }

    private TableView<StandingEntry> buildStandings() {
        boolean isBasketball = facade.getSport().getSportName()
                .equals("Basketball");

        TableView<StandingEntry> t = new TableView<>();
        t.setStyle("-fx-background-color: #1a1a2e;");

        TableColumn<StandingEntry, String> teamCol = new TableColumn<>("Team");
        teamCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTeam().getName()));
        teamCol.setPrefWidth(160);

        TableColumn<StandingEntry, Integer> pCol = new TableColumn<>("P");
        pCol.setCellValueFactory(d -> new SimpleIntegerProperty(
                d.getValue().getMatchesPlayed()).asObject());
        pCol.setPrefWidth(45);

        TableColumn<StandingEntry, Integer> wCol = new TableColumn<>("W");
        wCol.setCellValueFactory(d -> new SimpleIntegerProperty(
                d.getValue().getWins()).asObject());
        wCol.setPrefWidth(45);

        t.getColumns().addAll(teamCol, pCol, wCol);

        if (!isBasketball) {
            TableColumn<StandingEntry, Integer> dCol = new TableColumn<>("D");
            dCol.setCellValueFactory(d -> new SimpleIntegerProperty(
                    d.getValue().getDraws()).asObject());
            dCol.setPrefWidth(45);
            t.getColumns().add(dCol);
        }

        TableColumn<StandingEntry, Integer> lCol = new TableColumn<>("L");
        lCol.setCellValueFactory(d -> new SimpleIntegerProperty(
                d.getValue().getLosses()).asObject());
        lCol.setPrefWidth(45);

        TableColumn<StandingEntry, Integer> gdCol = new TableColumn<>(
                isBasketball ? "Diff" : "GD");
        gdCol.setCellValueFactory(d -> new SimpleIntegerProperty(
                d.getValue().getGoalDifference()).asObject());
        gdCol.setPrefWidth(60);

        TableColumn<StandingEntry, Integer> ptsCol = new TableColumn<>("Pts");
        ptsCol.setCellValueFactory(d -> new SimpleIntegerProperty(
                d.getValue().getPoints()).asObject());
        ptsCol.setPrefWidth(50);

        t.getColumns().addAll(lCol, gdCol, ptsCol);
        t.getItems().addAll(facade.getLeague().getSortedStandings());
        return t;
    }

    private ScrollPane buildFixture() {
        VBox list = new VBox(6);
        list.setStyle("-fx-padding: 10px; -fx-background-color: #1a1a2e;");

        facade.getLeague().getFixture().getAllRounds().entrySet()
                .stream()
                .sorted(java.util.Map.Entry.comparingByKey())
                .forEach(entry -> {
                    Label w = new Label("Week " + entry.getKey());
                    w.setStyle("-fx-text-fill: #e94560; -fx-font-weight: bold;");
                    list.getChildren().add(w);
                    entry.getValue().forEach(match -> {
                        Label ml = new Label("  " + match);
                        ml.setStyle("-fx-text-fill: white;");
                        list.getChildren().add(ml);
                    });
                });

        ScrollPane sp = new ScrollPane(list);
        sp.setStyle("-fx-background-color: #1a1a2e;");
        return sp;
    }

    private Button btn(String text) {
        Button b = new Button(text);
        b.setPrefSize(140, 36);
        b.setStyle("-fx-background-color: #1a1a2e; -fx-text-fill: white; "
                + "-fx-border-color: #e94560; -fx-border-width: 1px; "
                + "-fx-cursor: hand;");
        b.setOnMouseEntered(e -> b.setStyle(
                "-fx-background-color: #e94560; -fx-text-fill: white; "
                        + "-fx-border-color: #e94560; -fx-border-width: 1px; "
                        + "-fx-cursor: hand;"));
        b.setOnMouseExited(e -> b.setStyle(
                "-fx-background-color: #1a1a2e; -fx-text-fill: white; "
                        + "-fx-border-color: #e94560; -fx-border-width: 1px; "
                        + "-fx-cursor: hand;"));
        return b;
    }

    public BorderPane getView() {
        return view;
    }
}