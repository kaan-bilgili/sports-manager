package com.sportsmanager.ui;

import com.sportsmanager.core.model.*;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.List;

public class FixtureView {
    private final AbstractLeague league;
    private final AbstractTeam managedTeam;
    private VBox root;

    public FixtureView(AbstractLeague league, AbstractTeam managedTeam) {
        this.league = league;
        this.managedTeam = managedTeam;
        buildUI();
    }

    private void buildUI() {
        root = new VBox(10);
        root.setPadding(new Insets(30));

        Text heading = new Text("Fixtures");
        heading.getStyleClass().add("view-title");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("fixture-scroll");

        VBox fixtureList = new VBox(8);
        fixtureList.setPadding(new Insets(10));

        List<Fixture> fixtures = league.getFixtures();
        for (Fixture fixture : fixtures) {
            VBox weekBox = buildWeekBox(fixture);
            fixtureList.getChildren().add(weekBox);
        }

        scrollPane.setContent(fixtureList);
        root.getChildren().addAll(heading, scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
    }

    private VBox buildWeekBox(Fixture fixture) {
        VBox box = new VBox(4);
        box.setPadding(new Insets(10));
        box.getStyleClass().add(fixture.getWeekNumber() == league.getCurrentWeek() ? "current-week-box" : "week-box");

        Label weekLabel = new Label("Week " + fixture.getWeekNumber());
        weekLabel.getStyleClass().add("week-header");
        box.getChildren().add(weekLabel);

        for (AbstractMatch match : fixture.getMatches()) {
            HBox matchRow = buildMatchRow(match);
            box.getChildren().add(matchRow);
        }

        return box;
    }

    private HBox buildMatchRow(AbstractMatch match) {
        HBox row = new HBox(10);
        row.setPadding(new Insets(4, 8, 4, 8));
        row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        boolean involvesManaged = match.getHomeTeam().getId().equals(managedTeam.getId()) ||
                match.getAwayTeam().getId().equals(managedTeam.getId());

        if (involvesManaged) row.getStyleClass().add("managed-match-row");
        else row.getStyleClass().add("match-row");

        Label homeLabel = new Label(match.getHomeTeam().getName());
        homeLabel.setPrefWidth(160);
        homeLabel.getStyleClass().add("team-name");

        String score = match.isPlayed() ? match.getResult().toString() : "vs";
        Label scoreLabel = new Label(score);
        scoreLabel.setPrefWidth(60);
        scoreLabel.setAlignment(javafx.geometry.Pos.CENTER);
        scoreLabel.getStyleClass().add(match.isPlayed() ? "score-label" : "vs-label");

        Label awayLabel = new Label(match.getAwayTeam().getName());
        awayLabel.setPrefWidth(160);
        awayLabel.getStyleClass().add("team-name");

        row.getChildren().addAll(homeLabel, scoreLabel, awayLabel);
        return row;
    }

    public VBox getRoot() { return root; }
}
