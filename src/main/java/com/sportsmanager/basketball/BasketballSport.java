package com.sportsmanager.basketball;

import java.util.Random;

import com.sportsmanager.domain.Match;
import com.sportsmanager.domain.Player;
import com.sportsmanager.domain.Team;
import com.sportsmanager.football.FootballMatch;
import com.sportsmanager.football.FootballPlayer;
import com.sportsmanager.football.FootballTeam;

public class BasketballSport {

     private static final String[] POSITIONS = {"GOALKEEPER", "DEFENDER",
            "MIDFIELDER", "FORWARD"};
    private static final Random random = new Random();

    @Override
    public String getSportName() {
        return "Football";
    }

    @Override
    public Team createTeam(String name) {
        return new FootballTeam(name);
    }

    @Override
    public Player createPlayer(String name, int age, int skillLevel) {
        FootballPlayer.Position[] positions = FootballPlayer.Position.values();
        FootballPlayer.Position position = positions[random.nextInt(positions.length)];
        return new FootballPlayer(name, age, skillLevel, position);
    }

    @Override
    public Match createMatch(Team homeTeam, Team awayTeam) {
        return new FootballMatch(homeTeam, awayTeam);
    }

    @Override
    public int getSquadSize() {
        return 11;
    }

    @Override
    public int getSubstituteCount() {
        return 3;
    }

}
