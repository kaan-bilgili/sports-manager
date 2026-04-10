package com.sportsmanager.football;

import com.sportsmanager.domain.Match;
import com.sportsmanager.domain.Player;
import com.sportsmanager.domain.Team;
import com.sportsmanager.sport.Sport;

import java.util.Random;

public class FootballSport implements Sport {

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
        return new FootballPlayer(name, age, skillLevel, FootballPlayer.Position.FORWARD);
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