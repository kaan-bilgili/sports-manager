package com.sportsmanager.domain;

import java.io.Serializable;

public abstract class Match implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Team homeTeam;
    protected Team awayTeam;
    protected int homeScore;
    protected int awayScore;
    protected boolean played;

    public Match(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
        this.played = false;
    }

    public abstract void simulate();

    public Team getHomeTeam() { return homeTeam; }
    public Team getAwayTeam() { return awayTeam; }
    public int getHomeScore() { return homeScore; }
    public int getAwayScore() { return awayScore; }
    public boolean isPlayed() { return played; }

    public Team getWinner() {
        if (!played) return null;
        if (homeScore > awayScore) return homeTeam;
        if (awayScore > homeScore) return awayTeam;
        return null;
    }

    public boolean isDraw() {
        return played && homeScore == awayScore;
    }

    @Override
    public String toString() {
        if (!played) {
            return homeTeam.getName() + " vs "
                    + awayTeam.getName() + " (not played)";
        }
        return homeTeam.getName() + " " + homeScore
                + " - " + awayScore + " " + awayTeam.getName();
    }
}