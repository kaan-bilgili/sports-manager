package com.sportsmanager.football;

import com.sportsmanager.core.interfaces.Position;

public enum FootballPosition implements Position {
    GOALKEEPER("Goalkeeper", "GK"),
    DEFENDER("Defender", "DEF"),
    MIDFIELDER("Midfielder", "MID"),
    FORWARD("Forward", "FWD");

    private final String name;
    private final String abbreviation;

    FootballPosition(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

    @Override public String getName() { return name; }
    @Override public String getAbbreviation() { return abbreviation; }
}
