package com.sportsmanager.football;

public enum Tactic {
    F442("4-4-2", 1, 4, 4, 2),
    F433("4-3-3", 1, 4, 3, 3),
    F352("3-5-2", 1, 3, 5, 2),
    F532("5-3-2", 1, 5, 3, 2);

    private final String displayName;
    private final int goalkeepers;
    private final int defenders;
    private final int midfielders;
    private final int forwards;

    Tactic(String displayName, int gk, int def, int mid, int fwd) {
        this.displayName = displayName;
        this.goalkeepers = gk;
        this.defenders = def;
        this.midfielders = mid;
        this.forwards = fwd;
    }

    public String getDisplayName() { return displayName; }
    public int getGoalkeepers() { return goalkeepers; }
    public int getDefenders() { return defenders; }
    public int getMidfielders() { return midfielders; }
    public int getForwards() { return forwards; }

    @Override
    public String toString() { return displayName; }
}