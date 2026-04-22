package com.sportsmanager.basketball;

public class BasketballPlayer {
     public enum Position {
        GOALKEEPER, DEFENDER, MIDFIELDER, FORWARD
    }

    private Position position;

    public FootballPlayer(String name, int age, int skillLevel, Position position) {
        super(name, age, skillLevel);
        this.position = position;
    }

    public Position getFootballPosition() {
        return position;
    }

    @Override
    public String getPosition() {
        return position.name();
    }

}
