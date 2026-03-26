package com.sportsmanager.football;

import com.sportsmanager.core.interfaces.Position;
import com.sportsmanager.core.interfaces.Sport;
import com.sportsmanager.core.model.*;

import java.util.Arrays;
import java.util.List;

public class FootballSport implements Sport {
    @Override
    public String getName() { return "Football"; }

    @Override
    public int getTeamSize() { return 11; }

    @Override
    public int getSubstituteCount() { return 5; }

    @Override
    public List<? extends Position> getPositions() {
        return Arrays.asList(FootballPosition.values());
    }

    @Override
    public AbstractLeague createLeague(String name, List<? extends AbstractTeam> teams) {
        return new FootballLeague(name, teams);
    }

    @Override
    public AbstractTeam createTeam(String name) {
        return new FootballTeam(name);
    }

    @Override
    public AbstractPlayer createPlayer(String name, int age, Position position) {
        return new FootballPlayer(name, age, (FootballPosition) position);
    }

    @Override
    public AbstractMatch createMatch(AbstractTeam home, AbstractTeam away) {
        return new FootballMatch(home, away, 0);
    }
}
