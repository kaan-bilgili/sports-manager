package com.sportsmanager.domain;

import java.io.Serializable;

public abstract class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    // geri kalan kod aynı kalır
    protected String name;
    protected int age;
    protected int skillLevel;
    protected boolean injured;
    protected int injuryGamesRemaining;

    public Player(String name, int age, int skillLevel) {
        this.name = name;
        this.age = age;
        this.skillLevel = skillLevel;
        this.injured = false;
        this.injuryGamesRemaining = 0;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public int getSkillLevel() { return skillLevel; }
    public boolean isInjured() { return injured; }
    public int getInjuryGamesRemaining() { return injuryGamesRemaining; }

    public void setInjured(boolean injured) {
        this.injured = injured;
    }

    public void setInjuryGamesRemaining(int games) {
        this.injuryGamesRemaining = games;
        this.injured = games > 0;
    }

    public void train() {
    if (injured) return;
    
    int gain;
    if (skillLevel < 60) {
        gain = 2 + new java.util.Random().nextInt(3); // 2-4
    } else if (skillLevel < 80) {
        gain = 1 + new java.util.Random().nextInt(3); // 1-3
    } else {
        gain = new java.util.Random().nextInt(2); // 0-1
    }
    
    skillLevel = Math.min(99, skillLevel + gain);
}

    public void decrementInjury() {
        if (injuryGamesRemaining > 0) {
            injuryGamesRemaining--;
            if (injuryGamesRemaining == 0) {
                injured = false;
            }
        }
    }

    public abstract String getPosition();

    @Override
    public String toString() {
        return name + " [" + getPosition() + "] Skill:" + skillLevel
                + (injured ? " INJURED(" + injuryGamesRemaining + ")" : "");
    }
}