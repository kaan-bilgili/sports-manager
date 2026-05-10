package com.sportsmanager.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MatchSimulationScreen {

    private BorderPane view;
    private GameFacade facade;

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

    private HBox buildContent() {
        HBox content = new HBox(10);
        content.setPadding(new Insets(15));

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

        boolean isBasketball = facade.getSport().getSportName()
                .equals("Basketball");

        // Generate events
        List<String[]> events = generateEvents(nextMatch, isBasketball);

        // Left panel — Home events
        VBox leftPanel = buildEventPanel(
                nextMatch.getHomeTeam().getName(),
                events, true, "#e94560");

        // Center panel — Score
        VBox centerPanel = buildScorePanel(nextMatch);

        // Right panel — Away events
        VBox rightPanel = buildEventPanel(
                nextMatch.getAwayTeam().getName(),
                events, false, "#4fc3f7");

        HBox.setHgrow(leftPanel, Priority.ALWAYS);
        HBox.setHgrow(centerPanel, Priority.NEVER);
        HBox.setHgrow(rightPanel, Priority.ALWAYS);

        content.getChildren().addAll(leftPanel, centerPanel, rightPanel);
        return content;
    }

    private VBox buildScorePanel(Match match) {
        VBox panel = new VBox(10);
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setPrefWidth(200);
        panel.setStyle("-fx-background-color: #16213e; "
                + "-fx-padding: 15px; -fx-background-radius: 8px;");

        // Score
        int fontSize = (match.getHomeScore() >= 100 || match.getAwayScore() >= 100)
                ? 28
                : 42;

        Label homeScore = new Label(String.valueOf(match.getHomeScore()));
        homeScore.setStyle("-fx-text-fill: #e94560; -fx-font-size: "
                + fontSize + "px; -fx-font-weight: bold;");

        Label dash = new Label("—");
        dash.setStyle("-fx-text-fill: white; -fx-font-size: "
                + (fontSize - 10) + "px;");

        Label awayScore = new Label(String.valueOf(match.getAwayScore()));
        awayScore.setStyle("-fx-text-fill: #4fc3f7; -fx-font-size: "
                + fontSize + "px; -fx-font-weight: bold;");

        HBox scoreBox = new HBox(8, homeScore, dash, awayScore);
        scoreBox.setAlignment(Pos.CENTER);
        scoreBox.setMinWidth(180);

        Label resultLabel;
        if (match.isDraw()) {
            resultLabel = new Label("DRAW");
            resultLabel.setStyle("-fx-text-fill: #ffeb3b; "
                    + "-fx-font-size: 14px; -fx-font-weight: bold;");
        } else {
            resultLabel = new Label(match.getWinner().getName() + "\nWINS!");
            resultLabel.setStyle("-fx-text-fill: #4caf50; "
                    + "-fx-font-size: 14px; -fx-font-weight: bold; "
                    + "-fx-alignment: center;");
        }

        // Separator
        Label sep = new Label("─────────");
        sep.setStyle("-fx-text-fill: #444466;");

        // Stats title
        Label statsTitle = new Label("MATCH STATS");
        statsTitle.setStyle("-fx-text-fill: #a0a0b0; -fx-font-size: 11px; "
                + "-fx-font-weight: bold;");

        boolean isBasketball = facade.getSport().getSportName()
                .equals("Basketball");

        VBox stats = buildStats(match, isBasketball);

        panel.getChildren().addAll(
                scoreBox, resultLabel, sep, statsTitle, stats);
        return panel;
    }

    private VBox buildStats(Match match, boolean isBasketball) { // Bu functionu sonradan ekledim.
        VBox stats = new VBox(6);
        stats.setAlignment(Pos.CENTER);

        Random random = new Random();

        if (isBasketball) {
            int homeScore = match.getHomeScore();
            int awayScore = match.getAwayScore();

            // Assists — skorun yaklaşık yarısı
            int homeAssists = homeScore / 4 + random.nextInt(8);
            int awayAssists = awayScore / 4 + random.nextInt(8);

            // Rebounds — rastgele ama makul
            int homeRebounds = 20 + random.nextInt(20);
            int awayRebounds = 20 + random.nextInt(20);

            // Steals, Blocks, Turnovers
            int homeSteals = random.nextInt(10);
            int awaySteals = random.nextInt(10);
            int homeBlocks = random.nextInt(8);
            int awayBlocks = random.nextInt(8);
            int homeTurnovers = random.nextInt(15);
            int awayTurnovers = random.nextInt(15);

            addStat(stats, "Rebounds", homeRebounds, awayRebounds);
            addStat(stats, "Assists", homeAssists, awayAssists);
            addStat(stats, "Steals", homeSteals, awaySteals);
            addStat(stats, "Blocks", homeBlocks, awayBlocks);
            addStat(stats, "Turnovers", homeTurnovers, awayTurnovers);

        } else {
            int homeGoals = match.getHomeScore();
            int awayGoals = match.getAwayScore();

            // Shots — gol sayısına bağlı (her gol için ~3-5 şut)
            int homeShots = homeGoals * 3 + 2 + random.nextInt(8);
            int awayShots = awayGoals * 3 + 2 + random.nextInt(8);

            int homeOnTarget = homeGoals + random.nextInt(4);
            int awayOnTarget = awayGoals + random.nextInt(4);

            // Possession — toplam 100
            int homePossession = 35 + random.nextInt(30);
            int awayPossession = 100 - homePossession;

            // Fouls, Corners
            int homeFouls = 2 + random.nextInt(12);
            int awayFouls = 2 + random.nextInt(12);
            int homeCorners = random.nextInt(10);
            int awayCorners = random.nextInt(10);

            addStat(stats, "Shots", homeShots, awayShots);
            addStat(stats, "On Target", homeOnTarget, awayOnTarget);
            addStat(stats, "Possession %", homePossession, awayPossession);
            addStat(stats, "Fouls", homeFouls, awayFouls);
            addStat(stats, "Corners", homeCorners, awayCorners);
        }

        return stats;
    }

    private void addStat(VBox stats, String label, int home, int away) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER);
        row.setSpacing(5);

        Label homeVal = new Label(String.valueOf(home));
        homeVal.setStyle("-fx-text-fill: #e94560; -fx-font-size: 12px; "
                + "-fx-font-weight: bold;");
        homeVal.setPrefWidth(35);
        homeVal.setAlignment(Pos.CENTER_RIGHT);

        Label lbl = new Label(label);
        lbl.setStyle("-fx-text-fill: #a0a0b0; -fx-font-size: 11px;");
        lbl.setPrefWidth(80);
        lbl.setAlignment(Pos.CENTER);

        Label awayVal = new Label(String.valueOf(away));
        awayVal.setStyle("-fx-text-fill: #4fc3f7; -fx-font-size: 12px; "
                + "-fx-font-weight: bold;");
        awayVal.setPrefWidth(35);
        awayVal.setAlignment(Pos.CENTER_LEFT);

        row.getChildren().addAll(homeVal, lbl, awayVal);
        stats.getChildren().add(row);
    }

    private VBox buildEventPanel(String teamName,
            List<String[]> events, boolean isHome, String color) {
        VBox panel = new VBox(8);
        panel.setStyle("-fx-background-color: #16213e; "
                + "-fx-padding: 10px; -fx-background-radius: 8px;");

        Label title = new Label(teamName);
        title.setStyle("-fx-text-fill: " + color + "; "
                + "-fx-font-weight: bold; -fx-font-size: 13px;");
        title.setMaxWidth(Double.MAX_VALUE);
        title.setAlignment(isHome ? Pos.CENTER_LEFT : Pos.CENTER_RIGHT);

        VBox eventList = new VBox(4);
        eventList.setStyle("-fx-background-color: #16213e; -fx-padding: 5px;");

        for (String[] event : events) {
            boolean eventIsHome = Boolean.parseBoolean(event[2]);
            if (eventIsHome == isHome) {
                Label lbl = new Label(event[0] + " " + event[1]);
                lbl.setStyle("-fx-text-fill: white; -fx-font-size: 12px; "
                        + "-fx-padding: 3px 5px;");
                lbl.setWrapText(true);
                lbl.setMaxWidth(Double.MAX_VALUE);
                lbl.setAlignment(isHome ? Pos.CENTER_LEFT : Pos.CENTER_RIGHT);
                eventList.getChildren().add(lbl);
            }
        }

        if (eventList.getChildren().isEmpty()) {
            Label empty = new Label("No events");
            empty.setStyle("-fx-text-fill: #555577; -fx-font-size: 11px;");
            eventList.getChildren().add(empty);
        }

        ScrollPane scroll = new ScrollPane(eventList);
        scroll.setStyle("-fx-background-color: #16213e; "
                + "-fx-background: #16213e;");
        scroll.setPrefHeight(450);
        scroll.setFitToWidth(true);

        // Bu kritik — ScrollPane içini dark yapar
        scroll.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            scroll.lookup(".viewport").setStyle(
                    "-fx-background-color: #16213e;");
        });

        panel.getChildren().addAll(title, scroll);
        return panel;
    }

    private List<String[]> generateEvents(Match match, boolean isBasketball) {
        List<String[]> events = new ArrayList<>();
        Random random = new Random();

        if (isBasketball) {
            String[] shotTypes = { "2PT", "3PT", "FT" };
            int totalEvents = (match.getHomeScore() + match.getAwayScore()) / 3;

            // Home events
            int homeEvents = totalEvents / 2;
            for (int i = 0; i < homeEvents; i++) {
                int minute = 1 + random.nextInt(48);
                String shot = shotTypes[random.nextInt(shotTypes.length)];
                String player = getRandomPlayer(match.getHomeTeam(), random);
                events.add(new String[] {
                        minute + "'",
                        "🏀 " + shot + " — " + player,
                        "true"
                });
            }

            // Away events
            int awayEvents = totalEvents - homeEvents;
            for (int i = 0; i < awayEvents; i++) {
                int minute = 1 + random.nextInt(48);
                String shot = shotTypes[random.nextInt(shotTypes.length)];
                String player = getRandomPlayer(match.getAwayTeam(), random);
                events.add(new String[] {
                        minute + "'",
                        "🏀 " + shot + " — " + player,
                        "false"
                });
            }
        } else {
            // Home goals
            for (int i = 0; i < match.getHomeScore(); i++) {
                int minute = 1 + random.nextInt(90);
                String player = getRandomPlayer(match.getHomeTeam(), random);
                events.add(new String[] {
                        minute + "'",
                        "⚽ GOAL! " + player,
                        "true"
                });
            }

            // Away goals
            for (int i = 0; i < match.getAwayScore(); i++) {
                int minute = 1 + random.nextInt(90);
                String player = getRandomPlayer(match.getAwayTeam(), random);
                events.add(new String[] {
                        minute + "'",
                        "⚽ GOAL! " + player,
                        "false"
                });
            }

            // Cards
            if (random.nextBoolean()) {
                int minute = 1 + random.nextInt(90);
                boolean isHome = random.nextBoolean();
                Team team = isHome ? match.getHomeTeam() : match.getAwayTeam();
                events.add(new String[] {
                        minute + "'",
                        "🟡 Yellow Card — " + getRandomPlayer(team, random),
                        String.valueOf(isHome)
                });
            }

            if (random.nextInt(5) == 0) {
                int minute = 1 + random.nextInt(90);
                boolean isHome = random.nextBoolean();
                Team team = isHome ? match.getHomeTeam() : match.getAwayTeam();
                events.add(new String[] {
                        minute + "'",
                        "🔴 Red Card — " + getRandomPlayer(team, random),
                        String.valueOf(isHome)
                });
            }
        }

        // Kronolojik sırala
        events.sort((a, b) -> {
            try {
                int minA = Integer.parseInt(a[0].replace("'", "").trim());
                int minB = Integer.parseInt(b[0].replace("'", "").trim());
                return Integer.compare(minA, minB);
            } catch (NumberFormatException e) {
                return 0;
            }
        });

        return events;
    }

    private String getRandomPlayer(Team team, Random random) {
        if (team.getAvailablePlayers().isEmpty())
            return "Unknown";
        return team.getAvailablePlayers()
                .get(random.nextInt(team.getAvailablePlayers().size()))
                .getName();
    }

    private Match getNextMatch() {
        for (int week = 1; week <= facade.getLeague()
                .getFixture().getTotalRounds(); week++) {
            List<Match> matches = facade.getLeague()
                    .getFixture().getMatchesForRound(week);
            for (Match m : matches) {
                if (!m.isPlayed())
                    return m;
            }
        }
        return null;
    }

    private HBox buildButtons() {
        HBox box = new HBox(15);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(15));
        box.setStyle("-fx-background-color: #16213e;");

        Button continueBtn = new Button("Continue →");
        continueBtn.setPrefSize(200, 40);
        continueBtn.setStyle("-fx-background-color: #e94560; "
                + "-fx-text-fill: white; -fx-font-size: 14px; "
                + "-fx-cursor: hand; -fx-font-weight: bold;");
        continueBtn.setOnMouseEntered(e -> continueBtn.setStyle(
                "-fx-background-color: #ff6b6b; "
                        + "-fx-text-fill: white; -fx-font-size: 14px; "
                        + "-fx-cursor: hand; -fx-font-weight: bold;"));
        continueBtn.setOnMouseExited(e -> continueBtn.setStyle(
                "-fx-background-color: #e94560; "
                        + "-fx-text-fill: white; -fx-font-size: 14px; "
                        + "-fx-cursor: hand; -fx-font-weight: bold;"));
        continueBtn.setOnAction(e -> {
            facade.resetTrainings();
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