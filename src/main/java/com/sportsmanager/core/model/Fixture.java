package com.sportsmanager.core.model;

import java.util.ArrayList;
import java.util.List;

public class Fixture {
    private final int weekNumber;
    private final List<AbstractMatch> matches;

    public Fixture(int weekNumber) {
        this.weekNumber = weekNumber;
        this.matches = new ArrayList<>();
    }

    public void addMatch(AbstractMatch match) { matches.add(match); }

    public int getWeekNumber() { return weekNumber; }
    public List<AbstractMatch> getMatches() { return matches; }
}
