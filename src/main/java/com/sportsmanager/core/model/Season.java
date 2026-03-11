package com.sportsmanager.core.model;

public class Season {
    private final int year;
    private String name;

    public Season(int year) {
        this.year = year;
        this.name = year + "/" + (year + 1);
    }

    public int getYear() { return year; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
