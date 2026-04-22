package com.sportsmanager.basketball;

import java.util.Random;

import com.sportsmanager.domain.Match;
import com.sportsmanager.domain.Player;
import com.sportsmanager.domain.Team;
import com.sportsmanager.sport.Sport;

public class BasketballSport implements Sport {

    private static final String[] POSITIONS = { "POINT_GUARD", "SHOOTING_GUARD",
            "SMALL_FORWARD", "POWER_FORWARD", "CENTER" };
    private static final Random random = new Random();

    @Override
    public String getSportName() {
        return "Basketball";
    }

    @Override
    public Team createTeam(String name) {
        return new BasketballTeam(name);
    }

    @Override
    public Player createPlayer(String name, int age, int skillLevel) {
        BasketballPlayer.Position[] positions = BasketballPlayer.Position.values();
        BasketballPlayer.Position position = positions[random.nextInt(positions.length)];
        return new BasketballPlayer(name, age, skillLevel, position);
    }

    @Override
    public Match createMatch(Team homeTeam, Team awayTeam) {
        return new BasketballMatch(homeTeam, awayTeam);
    }

    @Override
    public int getSquadSize() {
        return 5;
    }

    @Override
    public int getSubstituteCount() {
        return 7;
    }

}
