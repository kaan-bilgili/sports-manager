package com.sportsmanager.core;

import com.sportsmanager.core.model.*;
import com.sportsmanager.football.*;
import com.sportsmanager.util.TeamGenerator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class FixtureTest {

    @Test
    void testFixtureCreation() {
        Fixture fixture = new Fixture(1);
        assertEquals(1, fixture.getWeekNumber());
        assertNotNull(fixture.getMatches());
        assertTrue(fixture.getMatches().isEmpty());
    }

    @Test
    void testEachTeamPlaysEveryOther() {
        List<FootballTeam> teams = TeamGenerator.generateTeams(8, new FootballSport());
        FootballLeague league = new FootballLeague("Test", teams);
        league.generateFixtures();

        Map<String, Integer> pairCount = new HashMap<>();
        for (Fixture fixture : league.getFixtures()) {
            for (AbstractMatch match : fixture.getMatches()) {
                String homeId = match.getHomeTeam().getId().toString();
                String awayId = match.getAwayTeam().getId().toString();
                String key = homeId.compareTo(awayId) < 0 ? homeId + "-" + awayId : awayId + "-" + homeId;
                pairCount.merge(key, 1, Integer::sum);
            }
        }

        for (Map.Entry<String, Integer> entry : pairCount.entrySet()) {
            assertEquals(2, entry.getValue(), "Each pair should play exactly twice");
        }
    }

    @Test
    void testNoTeamPlaysTwiceInWeek() {
        List<FootballTeam> teams = TeamGenerator.generateTeams(8, new FootballSport());
        FootballLeague league = new FootballLeague("Test", teams);
        league.generateFixtures();

        for (Fixture fixture : league.getFixtures()) {
            Set<UUID> teamsInWeek = new HashSet<>();
            for (AbstractMatch match : fixture.getMatches()) {
                UUID homeId = match.getHomeTeam().getId();
                UUID awayId = match.getAwayTeam().getId();
                assertFalse(teamsInWeek.contains(homeId), "Team appears twice in week " + fixture.getWeekNumber());
                assertFalse(teamsInWeek.contains(awayId), "Team appears twice in week " + fixture.getWeekNumber());
                teamsInWeek.add(homeId);
                teamsInWeek.add(awayId);
            }
        }
    }
}
