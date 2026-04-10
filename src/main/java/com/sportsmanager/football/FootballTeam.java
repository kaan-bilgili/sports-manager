package com.sportsmanager.football;

import com.sportsmanager.domain.Player;
import com.sportsmanager.domain.Team;

import java.util.List;
import java.util.stream.Collectors;

public class FootballTeam extends Team {

    private static final int SQUAD_SIZE = 11;
    private static final int SUBSTITUTE_COUNT = 3;

    public FootballTeam(String name) {
        super(name);
    }

    public int getSquadSize() {
        return SQUAD_SIZE;
    }

    public int getSubstituteCount() {
        return SUBSTITUTE_COUNT;
    }

    public List<FootballPlayer> getFootballPlayers() {
        return players.stream()
                .filter(p -> p instanceof FootballPlayer)
                .map(p -> (FootballPlayer) p)
                .collect(Collectors.toList());
    }

    public List<FootballPlayer> getPlayersByPosition(FootballPlayer.Position position) {
        return getFootballPlayers().stream()
                .filter(p -> p.getFootballPosition() == position)
                .collect(Collectors.toList());
    }
}