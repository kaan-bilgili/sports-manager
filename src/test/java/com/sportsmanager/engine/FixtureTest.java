package com.sportsmanager.engine;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.sportsmanager.domain.Fixture;
import com.sportsmanager.domain.Team;
import com.sportsmanager.football.FootballSport;
import com.sportsmanager.football.FootballTeam;

class FixtureTest {

    List<Team> createTeams(int count) {
        List<Team> teams = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            teams.add(new FootballTeam("Team" + i));
        }
        return teams;
    }

    @Test
    
    void testFixtureMatchCount() {
        FixtureGenerator generator = new FixtureGenerator(new FootballSport());
        List<Team> teams = createTeams(4);
        Fixture fixture = generator.generate(teams);

        assertEquals(12, fixture.getTotalMatchCount());
    }

    @Test
    
    void testFixtureTotalRounds() {
        FixtureGenerator generator = new FixtureGenerator(new FootballSport());
        List<Team> teams = createTeams(4);
        Fixture fixture = generator.generate(teams);

        assertEquals(6, fixture.getTotalRounds());
    }
}