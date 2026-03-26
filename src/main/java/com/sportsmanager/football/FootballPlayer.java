package com.sportsmanager.football;

import com.sportsmanager.core.interfaces.Position;
import com.sportsmanager.core.model.AbstractPlayer;
import com.sportsmanager.core.model.PlayerAttribute;

import java.util.Random;

public class FootballPlayer extends AbstractPlayer {
    private static final Random random = new Random();

    public FootballPlayer(String name, int age, FootballPosition position) {
        super(name, age, position);
    }

    @Override
    protected void createAttributes() {
        addAttribute(FootballPlayerAttribute.pace(40 + random.nextInt(51)));
        addAttribute(FootballPlayerAttribute.shooting(40 + random.nextInt(51)));
        addAttribute(FootballPlayerAttribute.passing(40 + random.nextInt(51)));
        addAttribute(FootballPlayerAttribute.defending(40 + random.nextInt(51)));
        addAttribute(FootballPlayerAttribute.dribbling(40 + random.nextInt(51)));
        addAttribute(FootballPlayerAttribute.physical(40 + random.nextInt(51)));
    }

    @Override
    public double getOverallRating() {
        FootballPosition pos = (FootballPosition) getPosition();
        var attrs = getAttributes();
        if (attrs.isEmpty()) return 0;

        double pace = getAttrValue("Pace");
        double shooting = getAttrValue("Shooting");
        double passing = getAttrValue("Passing");
        double defending = getAttrValue("Defending");
        double dribbling = getAttrValue("Dribbling");
        double physical = getAttrValue("Physical");

        return switch (pos) {
            case GOALKEEPER -> (defending * 0.4 + physical * 0.2 + pace * 0.15 + passing * 0.15 + dribbling * 0.05 + shooting * 0.05);
            case DEFENDER -> (defending * 0.35 + physical * 0.25 + pace * 0.15 + passing * 0.15 + dribbling * 0.05 + shooting * 0.05);
            case MIDFIELDER -> (passing * 0.3 + dribbling * 0.2 + pace * 0.15 + defending * 0.15 + shooting * 0.1 + physical * 0.1);
            case FORWARD -> (shooting * 0.35 + pace * 0.25 + dribbling * 0.2 + passing * 0.1 + physical * 0.05 + defending * 0.05);
        };
    }

    private double getAttrValue(String name) {
        return getAttributes().stream()
                .filter(a -> a.getName().equals(name))
                .mapToInt(PlayerAttribute::getValue)
                .findFirst()
                .orElse(50);
    }

    public double getAttackRating() {
        return getAttrValue("Shooting") * 0.4 + getAttrValue("Dribbling") * 0.3 + getAttrValue("Pace") * 0.3;
    }

    public double getDefenseRating() {
        return getAttrValue("Defending") * 0.5 + getAttrValue("Physical") * 0.3 + getAttrValue("Pace") * 0.2;
    }
}
