package com.sportsmanager.football;

import com.sportsmanager.core.model.AbstractLeague;
import com.sportsmanager.core.model.AbstractMatch;
import com.sportsmanager.core.model.AbstractTeam;

import java.util.List;

public class FootballLeague extends AbstractLeague {
    public FootballLeague(String name, List<? extends AbstractTeam> teams) {
        super(name, teams);
    }

    @Override
    public int getPointsForWin() { return 3; }

    @Override
    protected AbstractMatch createMatchInstance(AbstractTeam home, AbstractTeam away, int week) {
        return new FootballMatch(home, away, week);
    }
}
