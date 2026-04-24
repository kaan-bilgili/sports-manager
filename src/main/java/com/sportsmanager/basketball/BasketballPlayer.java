package com.sportsmanager.basketball;
import com.sportsmanager.domain.Player;

public class BasketballPlayer extends Player {
     public enum Position {
        POINT_GUARD, SHOOTING_GUARD, SMALL_FORWARD, POWER_FORWARD, CENTER
    }

    private Position position;

    public BasketballPlayer(String name, int age, int skillLevel, Position position) {
        super(name, age, skillLevel);
        this.position = position;
    }

    public Position getBasketballPosition() {
        return position;
    }

    @Override
    public String getPosition() {
        return position.name();
    }

}
