package com.sportsmanager.domain;

import java.util.List;

public class Season {

    private int seasonNumber;
    private int weekIndex;
    private League leagueRef;
    private boolean completed;

    public Season(int seasonNumber, League leagueRef) {
        this.seasonNumber = seasonNumber;
        this.leagueRef = leagueRef;
        this.weekIndex = 1;
        this.completed = false;
    }

    public void nextWeek() {

        if (completed) return;

        List<Match> roundMatches = leagueRef.getFixture().getMatchesForRound(weekIndex);

        if (roundMatches != null) {
            for (Match matchObj : roundMatches) {

                matchObj.simulate();

                if (matchObj.isPlayed()) {
                    leagueRef.updateStandings(matchObj);
                }
            }
        }

        int maxWeeks = leagueRef.getFixture().getTotalRounds();

        if (weekIndex >= maxWeeks) {
            completed = true;
        } else {
            weekIndex++;
        }
    }

    public boolean isFinished() {
        return completed;
    }

    public int getCurrentWeek() {
        return weekIndex;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public League getLeague() {
        return leagueRef;
    }

    public Team getLeader() {

        List<StandingEntry> list = leagueRef.getSortedStandings();

        if (list == null || list.isEmpty()) {
            return null;
        }

        return list.get(0).getTeam();
    }
}