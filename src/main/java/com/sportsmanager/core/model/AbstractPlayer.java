package com.sportsmanager.core.model;

import com.sportsmanager.core.interfaces.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AbstractPlayer {
    private final UUID id;
    private String name;
    private int age;
    private Position position;
    private List<PlayerAttribute> attributes;
    private int injuredForGames;
    private String nationality;

    public AbstractPlayer(String name, int age, Position position) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.age = age;
        this.position = position;
        this.attributes = new ArrayList<>();
        this.injuredForGames = 0;
        this.nationality = "Unknown";
        createAttributes();
    }

    protected abstract void createAttributes();

    protected void addAttribute(PlayerAttribute attribute) {
        attributes.add(attribute);
    }

    public boolean isInjured() { return injuredForGames > 0; }

    public void healOneGame() {
        if (injuredForGames > 0) injuredForGames--;
    }

    public double getOverallRating() {
        if (attributes.isEmpty()) return 0;
        return attributes.stream()
                .mapToInt(PlayerAttribute::getValue)
                .average()
                .orElse(0);
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }
    public List<PlayerAttribute> getAttributes() { return attributes; }
    public int getInjuredForGames() { return injuredForGames; }
    public void setInjuredForGames(int games) { this.injuredForGames = Math.max(0, games); }
    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
}
