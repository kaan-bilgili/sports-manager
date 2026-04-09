package com.sportsmanager.domain;

import java.util.List;

public class Season {

    private int seasonNumber;
    private int currentWeek;
    private League league;
    private boolean finished;

    public Season(int seasonNumber, League league) {
        this.seasonNumber = seasonNumber;
        this.currentWeek = 1;
        this.league = league;
        this.finished = false;
    }

    public void advanceWeek() {
        if (finished) return;

        List<Match> matches = league.getFixture().getMatchesForRound(currentWeek);
        for (Match match : matches) {
            match.simulate();
            league.updateStandings(match);
        }

        if (currentWeek >= league.getFixture().getTotalRounds()) {
            finished = true;
        } else {
            currentWeek++;
        }
    }

    public boolean isFinished() { return finished; }
    public int getCurrentWeek() { return currentWeek; }
    public int getSeasonNumber() { return seasonNumber; }
    public League getLeague() { return league; }

    public Team getLeader() {
        List<StandingEntry> standings = league.getSortedStandings();
        if (standings.isEmpty()) return null;
        return standings.get(0).getTeam();
    }
}