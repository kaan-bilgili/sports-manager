package com.sportsmanager.basketball;

import java.util.List;
import java.util.stream.Collectors;

import com.sportsmanager.domain.Team;

public class BasketballTeam extends Team {

    private static final int SQUAD_SIZE = 5;
    private static final int SUBSTITUTE_COUNT = 7;

    public BasketballTeam(String name) {
        super(name);
    }

    public int getSquadSize() {
        return SQUAD_SIZE;
    }

    public int getSubstituteCount() {
        return SUBSTITUTE_COUNT;
    }

    public List<BasketballPlayer> getBasketballPlayers() {
        return players.stream()
                .filter(p -> p instanceof BasketballPlayer)
                .map(p -> (BasketballPlayer) p)
                .collect(Collectors.toList());
    }

    public List<BasketballPlayer> getPlayersByPosition(BasketballPlayer.Position position) {
        return getBasketballPlayers().stream()
                .filter(p -> p.getBasketballPosition() == position)
                .collect(Collectors.toList());
    }

}
