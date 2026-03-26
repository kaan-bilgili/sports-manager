package com.sportsmanager.football;

import com.sportsmanager.core.interfaces.Tactic;

public enum FootballTactic implements Tactic {
    FOUR_FOUR_TWO("4-4-2", "Balanced 4-4-2"),
    FOUR_THREE_THREE("4-3-3", "Attacking 4-3-3"),
    THREE_FIVE_TWO("3-5-2", "Midfield 3-5-2"),
    FIVE_THREE_TWO("5-3-2", "Defensive 5-3-2");

    private final String name;
    private final String description;

    FootballTactic(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override public String getName() { return name; }
    @Override public String getDescription() { return description; }
}
