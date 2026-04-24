package com.sportsmanager.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Team implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String name;
    protected List<Player> players;

    public Team(String name) {
        this.name = name;
        this.players = new ArrayList<>();
    }

    public String getName() { return name; }
    public List<Player> getPlayers() { return players; }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public List<Player> getAvailablePlayers() {
        List<Player> available = new ArrayList<>();
        for (Player p : players) {
            if (!p.isInjured()) {
                available.add(p);
            }
        }
        return available;
    }

    public double getAverageSkill() {
        if (players.isEmpty()) return 0;
        return players.stream()
                .mapToInt(Player::getSkillLevel)
                .average()
                .orElse(0);
    }

    @Override
    public String toString() {
        return name + " (" + players.size() + " players)";
    }
}