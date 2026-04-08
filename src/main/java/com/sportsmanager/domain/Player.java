package com.sportsmanager.domain;

public abstract class Player {

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