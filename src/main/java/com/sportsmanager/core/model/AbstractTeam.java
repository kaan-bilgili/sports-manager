package com.sportsmanager.core.model;

import com.sportsmanager.core.interfaces.Tactic;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class AbstractTeam {
    private final UUID id;
    private String name;
    private String logoPath;
    private List<AbstractPlayer> players;
    private List<AbstractCoach> coaches;
    private Tactic currentTactic;

    public AbstractTeam(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.players = new ArrayList<>();
        this.coaches = new ArrayList<>();
    }

    public void addPlayer(AbstractPlayer player) { players.add(player); }
    public void removePlayer(AbstractPlayer player) { players.remove(player); }

    public List<AbstractPlayer> getAvailablePlayers() {
        return players.stream()
                .filter(p -> !p.isInjured())
                .collect(Collectors.toList());
    }

    public void setTactic(Tactic tactic) { this.currentTactic = tactic; }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLogoPath() { return logoPath; }
    public void setLogoPath(String logoPath) { this.logoPath = logoPath; }
    public List<AbstractPlayer> getPlayers() { return players; }
    public List<AbstractCoach> getCoaches() { return coaches; }
    public Tactic getCurrentTactic() { return currentTactic; }
}
