package com.sportsmanager.football;

import com.sportsmanager.core.model.AbstractTeam;

public class FootballTeam extends AbstractTeam {
    public FootballTeam(String name) {
        super(name);
        setTactic(FootballTactic.FOUR_FOUR_TWO);
    }
}
