package com.sportsmanager.domain;

public class StandingEntry {

    private Team team;
    private int wins;
    private int draws;
    private int losses;
    private int goalsFor;
    private int goalsAgainst;

    public StandingEntry(Team team) {
        this.team = team;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
        this.goalsFor = 0;
        this.goalsAgainst = 0;
    }

    public void recordWin(int goalsFor, int goalsAgainst) {
        this.wins++;
        this.goalsFor += goalsFor;
        this.goalsAgainst += goalsAgainst;
    }

    public void recordDraw(int goalsFor, int goalsAgainst) {
        this.draws++;
        this.goalsFor += goalsFor;
        this.goalsAgainst += goalsAgainst;
    }

    public void recordLoss(int goalsFor, int goalsAgainst) {
        this.losses++;
        this.goalsFor += goalsFor;
        this.goalsAgainst += goalsAgainst;
    }

    public int getPoints() {
        return (wins * 3) + (draws * 1);
    }

    public int getGoalDifference() {
        return goalsFor - goalsAgainst;
    }

    public int getMatchesPlayed() {
        return wins + draws + losses;
    }

    public Team getTeam() { return team; }
    public int getWins() { return wins; }
    public int getDraws() { return draws; }
    public int getLosses() { return losses; }
    public int getGoalsFor() { return goalsFor; }
    public int getGoalsAgainst() { return goalsAgainst; }

    @Override
    public String toString() {
        return String.format("%-20s P:%d W:%d D:%d L:%d GD:%d Pts:%d",
                team.getName(), getMatchesPlayed(),
                wins, draws, losses, getGoalDifference(), getPoints());
    }
}