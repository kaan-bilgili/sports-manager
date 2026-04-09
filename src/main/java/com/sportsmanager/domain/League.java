package com.sportsmanager.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class League {

    private String name;
    private List<Team> teams;
    private Fixture fixture;
    private Map<String, StandingEntry> standings;

    public League(String name) {
        this.name = name;
        this.teams = new ArrayList<>();
        this.standings = new HashMap<>();
        this.fixture = new Fixture();
    }

    public void addTeam(Team team) {
        teams.add(team);
        standings.put(team.getName(), new StandingEntry(team));
    }

    public void setFixture(Fixture fixture) {
        this.fixture = fixture;
    }

    public void updateStandings(Match match) {
        if (!match.isPlayed()) return;

        StandingEntry homeEntry = standings.get(match.getHomeTeam().getName());
        StandingEntry awayEntry = standings.get(match.getAwayTeam().getName());

        if (homeEntry == null || awayEntry == null) return;

        int homeScore = match.getHomeScore();
        int awayScore = match.getAwayScore();

        if (match.isDraw()) {
            homeEntry.recordDraw(homeScore, awayScore);
            awayEntry.recordDraw(awayScore, homeScore);
        } else if (match.getWinner() == match.getHomeTeam()) {
            homeEntry.recordWin(homeScore, awayScore);
            awayEntry.recordLoss(awayScore, homeScore);
        } else {
            awayEntry.recordWin(awayScore, homeScore);
            homeEntry.recordLoss(homeScore, awayScore);
        }
    }
    public List<StandingEntry> getSortedStandings() {
        List<StandingEntry> list = new ArrayList<>(standings.values());

        list.sort((a, b) -> {
            if (b.getPoints() != a.getPoints()) {
                return b.getPoints() - a.getPoints();
            } else if (b.getGoalDifference() != a.getGoalDifference()) {
                return b.getGoalDifference() - a.getGoalDifference();
            } else {
                return b.getGoalsFor() - a.getGoalsFor();
            }
        });

        return list;
    }

    public String getName() { return name; }
    public List<Team> getTeams() { return teams; }
    public Fixture getFixture() { return fixture; }
    public Map<String, StandingEntry> getStandings() { return standings; }

    public StandingEntry getStandingForTeam(String teamName) {
        return standings.get(teamName);
    }
}