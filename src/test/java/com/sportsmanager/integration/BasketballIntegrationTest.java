package com.sportsmanager.integration;

import com.sportsmanager.basketball.BasketballSport;
import com.sportsmanager.domain.StandingEntry;
import com.sportsmanager.domain.Team;
import com.sportsmanager.engine.GameFacade;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BasketballIntegrationTest {

    @Test
    void testBasketballFullSeasonNoException() {
        assertDoesNotThrow(() -> {
            GameFacade facade = new GameFacade(new BasketballSport());
            facade.initGame(4);
            while (!facade.isSeasonFinished()) {
                facade.advanceWeek();
            }
            assertNotNull(facade.getLeader());
            assertTrue(facade.isSeasonFinished());
        });
    }

    @Test
    void testBasketballLeaderDeterminedAfterSeason() {
        GameFacade facade = new GameFacade(new BasketballSport());
        facade.initGame(4);
        while (!facade.isSeasonFinished()) {
            facade.advanceWeek();
        }
        Team leader = facade.getLeader();
        assertNotNull(leader);
        List<StandingEntry> standings = facade.getLeague().getSortedStandings();
        assertEquals(leader.getName(), standings.get(0).getTeam().getName());
    }

    @Test
    void testBasketballNoDrawsInSeason() {
        GameFacade facade = new GameFacade(new BasketballSport());
        facade.initGame(4);
        while (!facade.isSeasonFinished()) {
            facade.advanceWeek();
        }
        for (StandingEntry entry : facade.getLeague().getSortedStandings()) {
            assertEquals(0, entry.getDraws());
        }
    }
}