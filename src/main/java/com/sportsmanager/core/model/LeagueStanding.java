package com.sportsmanager.core.model;

public class LeagueStanding {
    private AbstractTeam team;
    private int played;
    private int won;
    private int drawn;
    private int lost;
    private int goalsFor;
    private int goalsAgainst;

    public LeagueStanding(AbstractTeam team) {
        this.team = team;
    }

    public int getPoints() { return won * 3 + drawn; }
    public int getGoalDifference() { return goalsFor - goalsAgainst; }

    public void update(MatchResult result, boolean isHome) {
        played++;
        int myGoals = isHome ? result.getHomeScore() : result.getAwayScore();
        int theirGoals = isHome ? result.getAwayScore() : result.getHomeScore();
        goalsFor += myGoals;
        goalsAgainst += theirGoals;
        if (myGoals > theirGoals) won++;
        else if (myGoals == theirGoals) drawn++;
        else lost++;
    }

    public AbstractTeam getTeam() { return team; }
    public int getPlayed() { return played; }
    public int getWon() { return won; }
    public int getDrawn() { return drawn; }
    public int getLost() { return lost; }
    public int getGoalsFor() { return goalsFor; }
    public int getGoalsAgainst() { return goalsAgainst; }
}
