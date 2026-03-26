package com.sportsmanager.core.model;

public abstract class PlayerAttribute {
    private String name;
    private int value;

    public PlayerAttribute(String name, int value) {
        this.name = name;
        this.value = Math.max(0, Math.min(100, value));
    }

    public String getName() { return name; }
    public int getValue() { return value; }
    public void setValue(int value) { this.value = Math.max(0, Math.min(100, value)); }

    public void train(int amount) {
        this.value = Math.min(100, this.value + amount);
    }
}
