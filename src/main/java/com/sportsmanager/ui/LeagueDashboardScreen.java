package com.sportsmanager.ui;

import com.sportsmanager.app.MainApp;
import com.sportsmanager.domain.StandingEntry;
import com.sportsmanager.engine.GameFacade;
import com.sportsmanager.engine.GameSaveManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.layout.*;

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
        Button n = btn("Next Match");
        Button sv = btn("Save Game");
        Button m = btn("Main Menu");

        s.setOnAction(e -> view.setCenter(buildStandings()));
        f.setOnAction(e -> view.setCenter(buildFixture()));
        n.setOnAction(e -> {
            if (!facade.isSeasonFinished()) {
                facade.advanceWeek();
                view.setTop(buildHeader());
                view.setCenter(buildStandings());
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

        box.getChildren().addAll(s, f, n, sv, m);
        return box;
    }

    private TableView<StandingEntry> buildStandings() {
        TableView<StandingEntry> t = new TableView<>();
        t.setStyle("-fx-background-color: #1a1a2e;");

        addCol(t, "Team", 160, d -> new SimpleStringProperty(
                d.getValue().getTeam().getName()));
        addIntCol(t, "P", 45, d -> d.getValue().getMatchesPlayed());
        addIntCol(t, "W", 45, d -> d.getValue().getWins());
        addIntCol(t, "D", 45, d -> d.getValue().getDraws());
        addIntCol(t, "L", 45, d -> d.getValue().getLosses());
        addIntCol(t, "GD", 50, d -> d.getValue().getGoalDifference());
        addIntCol(t, "Pts", 50, d -> d.getValue().getPoints());

        t.getItems().addAll(facade.getLeague().getSortedStandings());
        return t;
    }

    private ScrollPane buildFixture() {
        VBox list = new VBox(6);
        list.setStyle("-fx-padding: 10px; -fx-background-color: #1a1a2e;");

        facade.getLeague().getFixture().getAllRounds().entrySet()
                .stream().sorted(java.util.Map.Entry.comparingByKey())
                .forEach(e -> {
                    Label w = new Label("Week " + e.getKey());
                    w.setStyle("-fx-text-fill: #e94560; -fx-font-weight: bold;");
                    list.getChildren().add(w);
                    e.getValue().forEach(m -> {
                        Label ml = new Label("  " + m);
                        ml.setStyle("-fx-text-fill: white;");
                        list.getChildren().add(ml);
                    });
                });

        ScrollPane sp = new ScrollPane(list);
        sp.setStyle("-fx-background-color: #1a1a2e;");
        return sp;
    }

    private void addCol(TableView<StandingEntry> t, String name, int w,
            javafx.util.Callback<TableColumn.CellDataFeatures<StandingEntry, String>,
            javafx.beans.value.ObservableValue<String>> factory) {
        TableColumn<StandingEntry, String> col = new TableColumn<>(name);
        col.setCellValueFactory(factory);
        col.setPrefWidth(w);
        t.getColumns().add(col);
    }

    private void addIntCol(TableView<StandingEntry> t, String name, int w,
            java.util.function.Function<TableColumn.CellDataFeatures<StandingEntry,
            Integer>, Integer> getter) {
        TableColumn<StandingEntry, Integer> col = new TableColumn<>(name);
        col.setCellValueFactory(d ->
                new SimpleIntegerProperty(getter.apply(d)).asObject());
        col.setPrefWidth(w);
        t.getColumns().add(col);
    }

    private Button btn(String text) {
        Button b = new Button(text);
        b.setPrefSize(140, 36);
        b.setStyle("-fx-background-color: #1a1a2e; -fx-text-fill: white; "
                + "-fx-border-color: #e94560; -fx-border-width: 1px; "
                + "-fx-cursor: hand;");
        return b;
    }

    public BorderPane getView() {
        return view;
    }
}