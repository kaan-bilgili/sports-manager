package com.sportsmanager.ui;

import com.sportsmanager.app.MainApp;
import com.sportsmanager.domain.Match;
import com.sportsmanager.domain.Team;
import com.sportsmanager.engine.GameFacade;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MatchSimulationScreen {

    private BorderPane view;
    private GameFacade facade;
    private VBox eventLog;

    public MatchSimulationScreen(GameFacade facade) {
        this.facade = facade;
        buildUI();
    }

    private void buildUI() {
        view = new BorderPane();
        view.setPrefSize(900, 650);
        view.setStyle("-fx-background-color: #1a1a2e;");
        view.setTop(buildHeader());
        view.setCenter(buildContent());
        view.setBottom(buildButtons());
    }

    private Label buildHeader() {
        Label lbl = new Label("Match Simulation");
        lbl.setMaxWidth(Double.MAX_VALUE);
        lbl.setStyle("-fx-background-color: #16213e; -fx-text-fill: white; "
                + "-fx-font-size: 18px; -fx-font-weight: bold; "
                + "-fx-padding: 12px; -fx-alignment: center;");
        return lbl;
    }

    private VBox buildContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_CENTER);

        Match nextMatch = getNextMatch();

        if (nextMatch == null) {
            Label noMatch = new Label("No matches to simulate!");
            noMatch.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
            content.getChildren().add(noMatch);
            return content;
        }

        nextMatch.simulate();
        facade.getLeague().updateStandings(nextMatch);

        boolean weekComplete = facade.getLeague()
                .getFixture()
                .getMatchesForRound(facade.getSeason().getCurrentWeek())
                .stream().allMatch(Match::isPlayed);

        if (weekComplete) {
            facade.getSeason().nextWeek();
        }

        // Score display
        HBox scoreBox = new HBox(20);
        scoreBox.setAlignment(Pos.CENTER);
        scoreBox.setStyle("-fx-background-color: #16213e; -fx-padding: 20px; "
                + "-fx-background-radius: 8px;");

        Label homeLabel = new Label(nextMatch.getHomeTeam().getName());
        homeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; "
                + "-fx-font-weight: bold;");

        Label scoreLabel = new Label(nextMatch.getHomeScore()
                + " — " + nextMatch.getAwayScore());
        scoreLabel.setStyle("-fx-text-fill: #e94560; -fx-font-size: 36px; "
                + "-fx-font-weight: bold;");

        Label awayLabel = new Label(nextMatch.getAwayTeam().getName());
        awayLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; "
                + "-fx-font-weight: bold;");

        scoreBox.getChildren().addAll(homeLabel, scoreLabel, awayLabel);

        Label resultLabel;
        if (nextMatch.isDraw()) {
            resultLabel = new Label("DRAW");
        } else {
            resultLabel = new Label(nextMatch.getWinner().getName() + " WINS!");
        }
        resultLabel.setStyle("-fx-text-fill: #4caf50; -fx-font-size: 16px; "
                + "-fx-font-weight: bold;");

        eventLog = new VBox(6);
        eventLog.setPadding(new Insets(10));
        eventLog.setStyle("-fx-background-color: #16213e;");

        Label logTitle = new Label("Match Events");
        logTitle.setStyle("-fx-text-fill: #e94560; -fx-font-weight: bold; "
                + "-fx-font-size: 14px;");
        eventLog.getChildren().add(logTitle);

        generateEvents(nextMatch);

        ScrollPane scroll = new ScrollPane(eventLog);
        scroll.setPrefHeight(300);
        scroll.setStyle("-fx-background-color: #16213e;");

        content.getChildren().addAll(scoreBox, resultLabel, scroll);
        return content;
    }

    private void generateEvents(Match match) {
        boolean isBasketball = facade.getSport().getSportName()
                .equals("Basketball");
        List<String> events = new ArrayList<>();
        Random random = new Random();

        if (isBasketball) {
            generateBasketballEvents(match, events, random);
        } else {
            generateFootballEvents(match, events, random);
        }

        // Kronolojik sırala
        events.sort((a, b) -> {
            try {
                int minA = Integer.parseInt(a.split("'")[0].trim());
                int minB = Integer.parseInt(b.split("'")[0].trim());
                return Integer.compare(minA, minB);
            } catch (NumberFormatException e) {
                return 0;
            }
        });

        for (String event : events) {
            addEvent(event);
        }
    }

    private void generateFootballEvents(Match match,
            List<String> events, Random random) {
        int totalGoals = match.getHomeScore() + match.getAwayScore();

        for (int i = 0; i < totalGoals; i++) {
            int minute = 1 + random.nextInt(90);
            boolean isHome = i < match.getHomeScore();
            Team scorer = isHome ? match.getHomeTeam() : match.getAwayTeam();
            String playerName = getRandomPlayer(scorer, random);
            events.add(minute + "' ⚽ GOAL! " + playerName
                    + " (" + scorer.getName() + ")");
        }

        if (random.nextBoolean()) {
            int minute = 1 + random.nextInt(90);
            Team team = random.nextBoolean()
                    ? match.getHomeTeam() : match.getAwayTeam();
            events.add(minute + "' 🟡 Yellow Card — "
                    + getRandomPlayer(team, random)
                    + " (" + team.getName() + ")");
        }

        if (random.nextInt(5) == 0) {
            int minute = 1 + random.nextInt(90);
            Team team = random.nextBoolean()
                    ? match.getHomeTeam() : match.getAwayTeam();
            events.add(minute + "' 🔴 Red Card — "
                    + getRandomPlayer(team, random)
                    + " (" + team.getName() + ")");
        }
    }

    private void generateBasketballEvents(Match match,
            List<String> events, Random random) {
        String[] shotTypes = {"2PT", "3PT", "FT"};
        int totalEvents = (match.getHomeScore() + match.getAwayScore()) / 3;

        for (int i = 0; i < totalEvents; i++) {
            int minute = 1 + random.nextInt(48);
            boolean isHome = random.nextBoolean();
            Team team = isHome ? match.getHomeTeam() : match.getAwayTeam();
            String shot = shotTypes[random.nextInt(shotTypes.length)];
            String playerName = getRandomPlayer(team, random);
            events.add(minute + "' 🏀 " + shot + " — "
                    + playerName + " (" + team.getName() + ")");
        }
    }

    private String getRandomPlayer(Team team, Random random) {
        if (team.getAvailablePlayers().isEmpty()) return "Unknown";
        return team.getAvailablePlayers()
                .get(random.nextInt(team.getAvailablePlayers().size()))
                .getName();
    }

    private void addEvent(String text) {
        Label lbl = new Label(text);
        lbl.setStyle("-fx-text-fill: white; -fx-font-size: 13px; "
                + "-fx-padding: 3px;");
        eventLog.getChildren().add(lbl);
    }

    private Match getNextMatch() {
        for (int week = 1; week <= facade.getLeague()
                .getFixture().getTotalRounds(); week++) {
            List<Match> matches = facade.getLeague()
                    .getFixture().getMatchesForRound(week);
            for (Match m : matches) {
                if (!m.isPlayed()) return m;
            }
        }
        return null;
    }

    private HBox buildButtons() {
        HBox box = new HBox(15);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(15));
        box.setStyle("-fx-background-color: #16213e;");

        Button continueBtn = new Button("Continue");
        continueBtn.setPrefSize(200, 40);
        continueBtn.setStyle("-fx-background-color: #e94560; "
                + "-fx-text-fill: white; -fx-font-size: 14px; "
                + "-fx-cursor: hand;");
        continueBtn.setOnAction(e -> {
            LeagueDashboardScreen dashboard = new LeagueDashboardScreen(facade);
            javafx.scene.Scene scene = new javafx.scene.Scene(
                    dashboard.getView(), 900, 650);
            MainApp.primaryStage.setScene(scene);
            MainApp.primaryStage.sizeToScene();
        });

        box.getChildren().add(continueBtn);
        return box;
    }

    public BorderPane getView() {
        return view;
    }
}