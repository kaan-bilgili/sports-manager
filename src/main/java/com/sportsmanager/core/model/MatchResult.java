package com.sportsmanager.core.model;

import java.util.Optional;

public class MatchResult {
    private final int homeScore;
    private final int awayScore;

    public MatchResult(int homeScore, int awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public int getHomeScore() { return homeScore; }
    public int getAwayScore() { return awayScore; }

    public boolean isHomeWin() { return homeScore > awayScore; }
    public boolean isAwayWin() { return awayScore > homeScore; }
    public boolean isDraw() { return homeScore == awayScore; }

    public Optional<AbstractTeam> getWinner(AbstractTeam home, AbstractTeam away) {
        if (isHomeWin()) return Optional.of(home);
        if (isAwayWin()) return Optional.of(away);
        return Optional.empty();
    }

    @Override
    public String toString() { return homeScore + " - " + awayScore; }
}
