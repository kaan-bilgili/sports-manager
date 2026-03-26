package com.sportsmanager.core;

import com.sportsmanager.core.model.LeagueStanding;
import com.sportsmanager.core.model.MatchResult;
import com.sportsmanager.football.FootballTeam;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LeagueStandingTest {

    @Test
    void testInitialStanding() {
        FootballTeam team = new FootballTeam("Test FC");
        LeagueStanding standing = new LeagueStanding(team);
        assertEquals(0, standing.getPlayed());
        assertEquals(0, standing.getWon());
        assertEquals(0, standing.getDrawn());
        assertEquals(0, standing.getLost());
        assertEquals(0, standing.getGoalsFor());
        assertEquals(0, standing.getGoalsAgainst());
        assertEquals(0, standing.getPoints());
    }

    @Test
    void testUpdateWin() {
        FootballTeam team = new FootballTeam("Test FC");
        LeagueStanding standing = new LeagueStanding(team);
        standing.update(new MatchResult(3, 1), true);

        assertEquals(1, standing.getPlayed());
        assertEquals(1, standing.getWon());
        assertEquals(0, standing.getDrawn());
        assertEquals(0, standing.getLost());
        assertEquals(3, standing.getGoalsFor());
        assertEquals(1, standing.getGoalsAgainst());
        assertEquals(3, standing.getPoints());
    }

    @Test
    void testUpdateDraw() {
        FootballTeam team = new FootballTeam("Test FC");
        LeagueStanding standing = new LeagueStanding(team);
        standing.update(new MatchResult(1, 1), true);

        assertEquals(1, standing.getPlayed());
        assertEquals(0, standing.getWon());
        assertEquals(1, standing.getDrawn());
        assertEquals(0, standing.getLost());
        assertEquals(1, standing.getPoints());
    }

    @Test
    void testUpdateLoss() {
        FootballTeam team = new FootballTeam("Test FC");
        LeagueStanding standing = new LeagueStanding(team);
        standing.update(new MatchResult(0, 2), true);

        assertEquals(1, standing.getPlayed());
        assertEquals(0, standing.getWon());
        assertEquals(0, standing.getDrawn());
        assertEquals(1, standing.getLost());
        assertEquals(0, standing.getPoints());
    }

    @Test
    void testGetPoints() {
        FootballTeam team = new FootballTeam("Test FC");
        LeagueStanding standing = new LeagueStanding(team);
        standing.update(new MatchResult(2, 0), true);
        standing.update(new MatchResult(1, 1), true);
        standing.update(new MatchResult(0, 1), true);

        assertEquals(4, standing.getPoints());
        assertEquals(3, standing.getPlayed());
    }
}
