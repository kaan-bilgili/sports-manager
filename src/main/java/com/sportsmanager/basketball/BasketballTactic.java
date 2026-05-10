package com.sportsmanager.basketball;

public enum BasketballTactic {
    BALANCED("Balanced"),
    AGGRESSIVE("Aggressive"),
    DEFENSIVE("Defensive"),
    FAST_BREAK("Fast Break"),
    THREE_POINT_FOCUS("3PT Focus");

    private final String displayName;

    BasketballTactic(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}