package com.sportsmanager.ui;

import com.sportsmanager.core.model.AbstractLeague;
import com.sportsmanager.core.model.AbstractTeam;
import com.sportsmanager.core.model.LeagueStanding;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class DashboardView {
    private final MainApp app;
    private final AbstractLeague league;
    private final AbstractTeam managedTeam;
    private BorderPane root;
    private BorderPane contentArea;

    public DashboardView(MainApp app, AbstractLeague league, AbstractTeam managedTeam) {
        this.app = app;
        this.league = league;
        this.managedTeam = managedTeam;
        buildUI();
    }

    private void buildUI() {
        root = new BorderPane();
        root.getStyleClass().add("root");

        HBox topBar = buildTopBar();
        root.setTop(topBar);

        VBox sidebar = buildSidebar();
        root.setLeft(sidebar);

        contentArea = new BorderPane();
        contentArea.getStyleClass().add("content-area");
        root.setCenter(contentArea);

        showDashboardHome();
    }

    private HBox buildTopBar() {
        HBox bar = new HBox(20);
        bar.setPadding(new Insets(12, 20, 12, 20));
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.getStyleClass().add("top-bar");

        Text title = new Text("Sports Manager");
        title.getStyleClass().add("top-title");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label weekLabel = new Label("Week " + league.getCurrentWeek());
        weekLabel.getStyleClass().add("info-badge");

        Label teamLabel = new Label(managedTeam.getName());
        teamLabel.getStyleClass().add("team-badge");

        LeagueStanding myStanding = league.getStandingsMap().get(managedTeam.getId());
        Label ptsLabel = new Label(myStanding != null ? myStanding.getPoints() + " pts" : "0 pts");
        ptsLabel.getStyleClass().add("points-badge");

        bar.getChildren().addAll(title, spacer, weekLabel, teamLabel, ptsLabel);
        return bar;
    }

    private VBox buildSidebar() {
        VBox sidebar = new VBox(5);
        sidebar.setPadding(new Insets(20, 10, 20, 10));
        sidebar.setPrefWidth(180);
        sidebar.getStyleClass().add("sidebar");

        String[] navItems = {"Dashboard", "My Team", "Fixtures", "League Table", "Next Match"};
        for (String item : navItems) {
            Button btn = new Button(item);
            btn.getStyleClass().add("nav-button");
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setOnAction(e -> handleNav(item));
            sidebar.getChildren().add(btn);
        }

        return sidebar;
    }

    private void handleNav(String item) {
        if (item.contains("Dashboard")) showDashboardHome();
        else if (item.contains("My Team")) showTeamView();
        else if (item.contains("Fixtures")) showFixtures();
        else if (item.contains("League Table")) showLeagueTable();
        else if (item.contains("Next Match")) showNextMatch();
    }

    private void showDashboardHome() {
        VBox home = new VBox(20);
        home.setPadding(new Insets(30));

        Text heading = new Text("Dashboard");
        heading.getStyleClass().add("view-title");

        HBox cards = new HBox(15);

        LeagueStanding myStanding = league.getStandingsMap().get(managedTeam.getId());

        cards.getChildren().addAll(
            buildInfoCard("Current Week", String.valueOf(league.getCurrentWeek())),
            buildInfoCard("Points", myStanding != null ? String.valueOf(myStanding.getPoints()) : "0"),
            buildInfoCard("Goals", myStanding != null ? myStanding.getGoalsFor() + " / " + myStanding.getGoalsAgainst() : "0/0"),
            buildInfoCard("W/D/L", myStanding != null ? myStanding.getWon() + "/" + myStanding.getDrawn() + "/" + myStanding.getLost() : "0/0/0")
        );

        Button advanceBtn = new Button("Simulate Next Week");
        advanceBtn.getStyleClass().add("primary-button");
        advanceBtn.setDisable(league.isFinished());
        advanceBtn.setOnAction(e -> {
            league.advanceWeek();
            showDashboardHome();
            refreshTopBar();
        });

        if (league.isFinished()) {
            Text champion = new Text("Champion: " + league.getChampion().map(t -> t.getName()).orElse("Unknown"));
            champion.getStyleClass().add("champion-text");
            home.getChildren().addAll(heading, cards, advanceBtn, champion);
        } else {
            home.getChildren().addAll(heading, cards, advanceBtn);
        }

        contentArea.setCenter(home);
    }

    private VBox buildInfoCard(String title, String value) {
        VBox card = new VBox(5);
        card.setPadding(new Insets(15));
        card.setPrefWidth(160);
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("info-card");

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("card-title");

        Label valueLabel = new Label(value);
        valueLabel.getStyleClass().add("card-value");

        card.getChildren().addAll(titleLabel, valueLabel);
        return card;
    }

    private void refreshTopBar() {
        root.setTop(buildTopBar());
    }

    private void showTeamView() {
        TeamView view = new TeamView(managedTeam);
        contentArea.setCenter(view.getRoot());
    }

    private void showFixtures() {
        FixtureView view = new FixtureView(league, managedTeam);
        contentArea.setCenter(view.getRoot());
    }

    private void showLeagueTable() {
        LeagueTableView view = new LeagueTableView(league, managedTeam);
        contentArea.setCenter(view.getRoot());
    }

    private void showNextMatch() {
        MatchView view = new MatchView(league, managedTeam, this);
        contentArea.setCenter(view.getRoot());
    }

    public void refresh() {
        showDashboardHome();
        refreshTopBar();
    }

    public BorderPane getRoot() { return root; }
}
