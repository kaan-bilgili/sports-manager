package com.sportsmanager.football;

import com.sportsmanager.core.model.PlayerAttribute;

public class FootballPlayerAttribute extends PlayerAttribute {
    public FootballPlayerAttribute(String name, int value) {
        super(name, value);
    }

    public static FootballPlayerAttribute pace(int value) { return new FootballPlayerAttribute("Pace", value); }
    public static FootballPlayerAttribute shooting(int value) { return new FootballPlayerAttribute("Shooting", value); }
    public static FootballPlayerAttribute passing(int value) { return new FootballPlayerAttribute("Passing", value); }
    public static FootballPlayerAttribute defending(int value) { return new FootballPlayerAttribute("Defending", value); }
    public static FootballPlayerAttribute dribbling(int value) { return new FootballPlayerAttribute("Dribbling", value); }
    public static FootballPlayerAttribute physical(int value) { return new FootballPlayerAttribute("Physical", value); }
}
