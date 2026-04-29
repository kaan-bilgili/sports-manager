package com.sportsmanager.ui;

import com.sportsmanager.app.MainApp;
import com.sportsmanager.domain.StandingEntry;
import com.sportsmanager.engine.GameFacade;
import com.sportsmanager.engine.GameSaveManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class LeagueDashboardScreen {

    private BorderPane view;
    private GameFacade facade;

    public LeagueDashboardScreen(GameFacade facade) {
        this.facade = facade;
        buildUI();
    }

    private void buildUI() {
        view = new BorderPane();
        view.setStyle("-fx-background-color: #1a1a2e;");
        view.setPrefHeight(650);
        view.setPrefWidth(900);

        view.setLeft(buildSidebar());
        view.setCenter(buildStandingsTable());
        view.setTop(buildHeader());
    }

    private VBox buildHeader() {
        VBox header = new VBox(5);
        header.setPadding(new Insets(20, 20, 10, 20));
        header.setStyle("-fx-background-color: #16213e;");

        Label title = new Label(facade.getLeague().getName());
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; "
                + "-fx-text-fill: #e94560;");

        Label season = new Label("Season " + facade.getSeason().getSeasonNumber()
                + "  |  Week " + facade.getSeason().getCurrentWeek());
        season.setStyle("-fx-font-size: 13px; -fx-text-fill: #a0a0b0;");

        header.getChildren().addAll(title, season);
        return header;
    }

    private VBox buildSidebar() {
        VBox sidebar = new VBox(10);
        sidebar.setPrefWidth(180);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #16213e;");

        Button standingsBtn = createSidebarButton("Standings");
        Button fixtureBtn = createSidebarButton("Fixture");
        Button nextMatchBtn = createSidebarButton("Next Match");
        Button saveBtn = createSidebarButton("Save Game");
        Button menuBtn = createSidebarButton("Main Menu");

        standingsBtn.setOnAction(e -> view.setCenter(buildStandingsTable()));
        fixtureBtn.setOnAction(e -> view.setCenter(buildFixturePanel()));

        nextMatchBtn.setOnAction(e -> {
            if (!facade.isSeasonFinished()) {
                facade.advanceWeek();
                view.setCenter(buildStandingsTable());
                view.setTop(buildHeader());
            } else {
                showSeasonWinner();
            }
        });

        saveBtn.setOnAction(e -> {
            try {
                new GameSaveManager().save(facade);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Save Game");
                alert.setHeaderText("Game Saved");
                alert.setContentText("Your game has been saved successfully.");
                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Save Error");
                alert.setHeaderText("Could not save game");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        menuBtn.setOnAction(e -> MainApp.showMainMenu());

        sidebar.getChildren().addAll(
                standingsBtn, fixtureBtn, nextMatchBtn, saveBtn, menuBtn);
        return sidebar;
    }

    private VBox buildStandingsTable() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));

        Label title = new Label("League Standings");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; "
                + "-fx-text-fill: white;");

        TableView<StandingEntry> table = new TableView<>();
        table.setStyle("-fx-background-color: #16213e; -fx-text-fill: white;");
        table.setPrefHeight(500);

        TableColumn<StandingEntry, String> teamCol = new TableColumn<>("Team");
        teamCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getTeam().getName()));
        teamCol.setPrefWidth(150);

        TableColumn<StandingEntry, Integer> pCol = new TableColumn<>("P");
        pCol.setCellValueFactory(data ->
                new SimpleIntegerProperty(
                        data.getValue().getMatchesPlayed()).asObject());
        pCol.setPrefWidth(50);

        TableColumn<StandingEntry, Integer> wCol = new TableColumn<>("W");
        wCol.setCellValueFactory(data ->
                new SimpleIntegerProperty(
                        data.getValue().getWins()).asObject());
        wCol.setPrefWidth(50);

        TableColumn<StandingEntry, Integer> dCol = new TableColumn<>("D");
        dCol.setCellValueFactory(data ->
                new SimpleIntegerProperty(
                        data.getValue().getDraws()).asObject());
        dCol.setPrefWidth(50);

        TableColumn<StandingEntry, Integer> lCol = new TableColumn<>("L");
        lCol.setCellValueFactory(data ->
                new SimpleIntegerProperty(
                        data.getValue().getLosses()).asObject());
        lCol.setPrefWidth(50);

        TableColumn<StandingEntry, Integer> gdCol = new TableColumn<>("GD");
        gdCol.setCellValueFactory(data ->
                new SimpleIntegerProperty(
                        data.getValue().getGoalDifference()).asObject());
        gdCol.setPrefWidth(60);

        TableColumn<StandingEntry, Integer> ptsCol = new TableColumn<>("Pts");
        ptsCol.setCellValueFactory(data ->
                new SimpleIntegerProperty(
                        data.getValue().getPoints()).asObject());
        ptsCol.setPrefWidth(60);

        table.getColumns().addAll(teamCol, pCol, wCol, dCol, lCol, gdCol, ptsCol);
        table.getItems().addAll(facade.getLeague().getSortedStandings());

        container.getChildren().addAll(title, table);
        return container;
    }

    private VBox buildFixturePanel() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));

        Label title = new Label("Fixture");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; "
                + "-fx-text-fill: white;");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background-color: #1a1a2e;");
        scrollPane.setPrefHeight(500);

        VBox fixtureList = new VBox(10);
        fixtureList.setPadding(new Insets(10));

        facade.getLeague().getFixture().getAllRounds().entrySet()
                .stream()
                .sorted(java.util.Map.Entry.comparingByKey())
                .forEach(entry -> {
                    Label weekLabel = new Label("Week " + entry.getKey());
                    weekLabel.setStyle("-fx-font-size: 14px; "
                            + "-fx-font-weight: bold; -fx-text-fill: #e94560;");
                    fixtureList.getChildren().add(weekLabel);

                    entry.getValue().forEach(match -> {
                        Label matchLabel = new Label("  " + match.toString());
                        matchLabel.setStyle("-fx-text-fill: white; "
                                + "-fx-font-size: 13px;");
                        fixtureList.getChildren().add(matchLabel);
                    });
                });

        scrollPane.setContent(fixtureList);
        container.getChildren().addAll(title, scrollPane);
        return container;
    }

    private void showSeasonWinner() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Season Finished");
        alert.setHeaderText("Season Complete!");
        alert.setContentText("Champion: " + facade.getLeader().getName());
        alert.showAndWait();
    }

    private Button createSidebarButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(160);
        btn.setPrefHeight(40);
        btn.setStyle("-fx-background-color: #1a1a2e; "
                + "-fx-text-fill: white; "
                + "-fx-font-size: 13px; "
                + "-fx-border-color: #e94560; "
                + "-fx-border-width: 1px; "
                + "-fx-cursor: hand;");
        return btn;
    }

    public BorderPane getView() {
        return view;
    }
}