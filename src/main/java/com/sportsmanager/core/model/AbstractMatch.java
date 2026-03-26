package com.sportsmanager.core.model;

import com.sportsmanager.core.interfaces.Simulatable;
import java.util.UUID;

public abstract class AbstractMatch implements Simulatable {
    private final UUID id;
    private final AbstractTeam homeTeam;
    private final AbstractTeam awayTeam;
    private boolean played;
    private MatchResult result;
    private int weekNumber;

    public AbstractMatch(AbstractTeam homeTeam, AbstractTeam awayTeam, int weekNumber) {
        this.id = UUID.randomUUID();
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.played = false;
        this.weekNumber = weekNumber;
    }

    @Override
    public MatchResult simulate() {
        if (!played) {
            result = doSimulate();
            played = true;
        }
        return result;
    }

    protected abstract MatchResult doSimulate();

    public UUID getId() { return id; }
    public AbstractTeam getHomeTeam() { return homeTeam; }
    public AbstractTeam getAwayTeam() { return awayTeam; }
    public boolean isPlayed() { return played; }
    public MatchResult getResult() { return result; }
    public int getWeekNumber() { return weekNumber; }
    public void setWeekNumber(int weekNumber) { this.weekNumber = weekNumber; }
}
