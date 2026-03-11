package com.sportsmanager.football;

import com.sportsmanager.core.model.*;
import com.sportsmanager.util.TeamGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class FootballLeagueTest {

    private List<FootballTeam> teams;
    private FootballLeague league;

    @BeforeEach
    void setUp() {
        teams = TeamGenerator.generateTeams(8, new FootballSport());
        league = new FootballLeague("Test League", teams);
        league.generateFixtures();
    }

    @Test
    void testLeagueCreation() {
        assertEquals("Test League", league.getName());
        assertEquals(8, league.getTeams().size());
    }

    @Test
    void testGenerateFixtures() {
        assertEquals(14, league.getFixtures().size());
    }

    @Test
    void testAdvanceWeek() {
        assertEquals(1, league.getCurrentWeek());
        league.advanceWeek();
        assertEquals(2, league.getCurrentWeek());

        List<LeagueStanding> standings = league.getStandings();
        int totalPlayed = standings.stream().mapToInt(LeagueStanding::getPlayed).sum();
        assertTrue(totalPlayed > 0, "At least some matches should have been played");
    }

    @Test
    void testFullSeason() {
        while (!league.isFinished()) {
            league.advanceWeek();
        }
        assertTrue(league.isFinished());
        assertTrue(league.getChampion().isPresent());
    }

    @Test
    void testStandingsSorted() {
        for (int i = 0; i < 4; i++) {
            if (!league.isFinished()) league.advanceWeek();
        }
        List<LeagueStanding> standings = league.getStandings();
        for (int i = 0; i < standings.size() - 1; i++) {
            LeagueStanding a = standings.get(i);
            LeagueStanding b = standings.get(i + 1);
            assertTrue(a.getPoints() >= b.getPoints() ||
                    (a.getPoints() == b.getPoints() && a.getGoalDifference() >= b.getGoalDifference()),
                    "Standings should be sorted by points then goal difference");
        }
    }
}
