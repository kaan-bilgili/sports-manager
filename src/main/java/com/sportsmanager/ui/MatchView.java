package com.sportsmanager.ui;

import com.sportsmanager.core.model.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.Optional;

public class MatchView {
    private final AbstractLeague league;
    private final AbstractTeam managedTeam;
    private final DashboardView dashboard;
    private VBox root;

    public MatchView(AbstractLeague league, AbstractTeam managedTeam, DashboardView dashboard) {
        this.league = league;
        this.managedTeam = managedTeam;
        this.dashboard = dashboard;
        buildUI();
    }

    private void buildUI() {
        root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);

        if (league.isFinished()) {
            Text finished = new Text("Season Finished!");
            finished.getStyleClass().add("view-title");
            Optional<AbstractTeam> champion = league.getChampion();
            Text champText = new Text("Champion: " + champion.map(AbstractTeam::getName).orElse("Unknown"));
            champText.getStyleClass().add("champion-text");
            root.getChildren().addAll(finished, champText);
            return;
        }

        AbstractMatch nextMatch = findNextManagedMatch();

        Text heading = new Text("Next Match - Week " + league.getCurrentWeek());
        heading.getStyleClass().add("view-title");

        if (nextMatch == null) {
            Label noMatch = new Label("No upcoming match for your team this week.");
            noMatch.getStyleClass().add("info-text");

            Button advanceBtn = new Button("Advance Week");
            advanceBtn.getStyleClass().add("primary-button");
            advanceBtn.setOnAction(e -> { league.advanceWeek(); dashboard.refresh(); });

            root.getChildren().addAll(heading, noMatch, advanceBtn);
            return;
        }

        VBox matchCard = buildMatchCard(nextMatch);
        root.getChildren().addAll(heading, matchCard);
    }

    private VBox buildMatchCard(AbstractMatch match) {
        VBox card = new VBox(20);
        card.setPadding(new Insets(30));
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("match-card");
        card.setMaxWidth(500);

        HBox teamsRow = new HBox(30);
        teamsRow.setAlignment(Pos.CENTER);

        VBox homeBox = buildTeamBox(match.getHomeTeam(), true);
        Text vsText = new Text("VS");
        vsText.getStyleClass().add("vs-text");
        VBox awayBox = buildTeamBox(match.getAwayTeam(), false);

        teamsRow.getChildren().addAll(homeBox, vsText, awayBox);

        Label tacticLabel = new Label("Your Tactic:");
        tacticLabel.getStyleClass().add("form-label");

        ComboBox<String> tacticCombo = new ComboBox<>();
        tacticCombo.getItems().addAll("4-4-2", "4-3-3", "3-5-2", "5-3-2");
        tacticCombo.setValue(managedTeam.getCurrentTactic() != null ? managedTeam.getCurrentTactic().getName() : "4-4-2");

        Button startBtn = new Button("Play Match");
        startBtn.getStyleClass().add("primary-button");
        startBtn.setOnAction(e -> playMatch(match, card));

        card.getChildren().addAll(teamsRow, tacticLabel, tacticCombo, startBtn);
        return card;
    }

    private VBox buildTeamBox(AbstractTeam team, boolean isHome) {
        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10));

        Label isHomeLabel = new Label(isHome ? "(Home)" : "(Away)");
        isHomeLabel.getStyleClass().add("match-side-label");

        Label nameLabel = new Label(team.getName());
        nameLabel.getStyleClass().add("match-team-name");
        if (team.getId().equals(managedTeam.getId())) {
            nameLabel.getStyleClass().add("managed-team-highlight");
        }

        box.getChildren().addAll(isHomeLabel, nameLabel);
        return box;
    }

    private void playMatch(AbstractMatch match, VBox card) {
        MatchResult result = match.simulate();
        league.getStandingsMap().get(match.getHomeTeam().getId()).update(result, true);
        league.getStandingsMap().get(match.getAwayTeam().getId()).update(result, false);

        Fixture currentFixture = league.getFixtures().get(league.getCurrentWeek() - 1);
        for (AbstractMatch m : currentFixture.getMatches()) {
            if (!m.isPlayed()) {
                MatchResult r = m.simulate();
                league.getStandingsMap().get(m.getHomeTeam().getId()).update(r, true);
                league.getStandingsMap().get(m.getAwayTeam().getId()).update(r, false);
            }
        }

        league.forceAdvanceWeek();

        card.getChildren().clear();
        Text resultTitle = new Text("Match Result");
        resultTitle.getStyleClass().add("view-title");

        String homeStr = match.getHomeTeam().getName();
        String awayStr = match.getAwayTeam().getName();
        Text scoreText = new Text(result.getHomeScore() + "  -  " + result.getAwayScore());
        scoreText.getStyleClass().add("big-score");

        Text teamsText = new Text(homeStr + "   vs   " + awayStr);
        teamsText.getStyleClass().add("match-teams-text");

        String outcome;
        if (match.getHomeTeam().getId().equals(managedTeam.getId())) {
            outcome = result.isHomeWin() ? "Victory!" : result.isDraw() ? "Draw" : "Defeat";
        } else {
            outcome = result.isAwayWin() ? "Victory!" : result.isDraw() ? "Draw" : "Defeat";
        }
        Text outcomeText = new Text(outcome);
        outcomeText.getStyleClass().add("outcome-text");

        Button continueBtn = new Button("Continue");
        continueBtn.getStyleClass().add("primary-button");
        continueBtn.setOnAction(ev -> dashboard.refresh());

        card.setAlignment(Pos.CENTER);
        card.getChildren().addAll(resultTitle, teamsText, scoreText, outcomeText, continueBtn);
    }

    private AbstractMatch findNextManagedMatch() {
        if (league.getCurrentWeek() > league.getFixtures().size()) return null;
        Fixture fixture = league.getFixtures().get(league.getCurrentWeek() - 1);
        for (AbstractMatch match : fixture.getMatches()) {
            if (!match.isPlayed() &&
                (match.getHomeTeam().getId().equals(managedTeam.getId()) ||
                 match.getAwayTeam().getId().equals(managedTeam.getId()))) {
                return match;
            }
        }
        return null;
    }

    public VBox getRoot() { return root; }
}
