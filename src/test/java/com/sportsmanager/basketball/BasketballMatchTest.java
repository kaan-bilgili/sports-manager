package com.sportsmanager.basketball;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class BasketballMatchTest {

    static BasketballTeam createTeamWithPlayers(String name, int skillLevel) {
        BasketballTeam team = new BasketballTeam(name);
        for (int i = 0; i < 12; i++) {
            team.addPlayer(new BasketballPlayer(
                    "Player" + i, 25, skillLevel,
                    BasketballPlayer.Position.POINT_GUARD));
        }
        return team;
    }

    @Test

    void testMatchIsPlayedAfterSimulation() {
        BasketballTeam home = createTeamWithPlayers("Lakers", 75);
        BasketballTeam away = createTeamWithPlayers("Celtics", 70);
        BasketballMatch match = new BasketballMatch(home, away);

        assertFalse(match.isPlayed());
        match.simulate();
        assertTrue(match.isPlayed());
    }

    @Test
    // T27: Basketbol skorlari 60 veya daha buyuk
    void testBasketballScoresAboveMinimum() {
        BasketballTeam home = createTeamWithPlayers("Lakers", 75);
        BasketballTeam away = createTeamWithPlayers("Celtics", 70);
        BasketballMatch match = new BasketballMatch(home, away);

        match.simulate();

        assertTrue(match.getHomeScore() >= 60);
        assertTrue(match.getAwayScore() >= 60);
    }

    @Test
    // T28: Basketbolda beraberlik olmaz
    void testNoDrawInBasketball() {
        BasketballTeam home = createTeamWithPlayers("Lakers", 75);
        BasketballTeam away = createTeamWithPlayers("Celtics", 70);
        BasketballMatch match = new BasketballMatch(home, away);

        match.simulate();

        assertFalse(match.isDraw());
        assertNotNull(match.getWinner());
    }

    @Test
    // T29: Ayni mac iki kez simulate edilemez
    void testMatchCannotBeSimulatedTwice() {
        BasketballTeam home = createTeamWithPlayers("Lakers", 75);
        BasketballTeam away = createTeamWithPlayers("Celtics", 70);
        BasketballMatch match = new BasketballMatch(home, away);

        match.simulate();
        int firstHomeScore = match.getHomeScore();
        int firstAwayScore = match.getAwayScore();

        match.simulate();

        assertEquals(firstHomeScore, match.getHomeScore());
        assertEquals(firstAwayScore, match.getAwayScore());
    }
}
