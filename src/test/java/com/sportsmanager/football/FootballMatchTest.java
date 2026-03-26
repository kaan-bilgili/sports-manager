package com.sportsmanager.football;

import com.sportsmanager.core.model.MatchResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FootballMatchTest {

    private FootballTeam createTeamWithPlayers(String name) {
        FootballTeam team = new FootballTeam(name);
        team.addPlayer(new FootballPlayer("GK", 25, FootballPosition.GOALKEEPER));
        for (int i = 0; i < 4; i++) team.addPlayer(new FootballPlayer("DEF" + i, 25, FootballPosition.DEFENDER));
        for (int i = 0; i < 4; i++) team.addPlayer(new FootballPlayer("MID" + i, 25, FootballPosition.MIDFIELDER));
        for (int i = 0; i < 2; i++) team.addPlayer(new FootballPlayer("FWD" + i, 25, FootballPosition.FORWARD));
        return team;
    }

    @Test
    void testMatchCreation() {
        FootballTeam home = new FootballTeam("Home FC");
        FootballTeam away = new FootballTeam("Away FC");
        FootballMatch match = new FootballMatch(home, away, 1);

        assertEquals(home, match.getHomeTeam());
        assertEquals(away, match.getAwayTeam());
        assertFalse(match.isPlayed());
        assertNull(match.getResult());
    }

    @Test
    void testSimulate() {
        FootballTeam home = createTeamWithPlayers("Home FC");
        FootballTeam away = createTeamWithPlayers("Away FC");
        FootballMatch match = new FootballMatch(home, away, 1);

        MatchResult result = match.simulate();
        assertTrue(match.isPlayed());
        assertNotNull(result);
    }

    @Test
    void testSimulateResult() {
        FootballTeam home = createTeamWithPlayers("Home FC");
        FootballTeam away = createTeamWithPlayers("Away FC");
        FootballMatch match = new FootballMatch(home, away, 1);

        MatchResult result = match.simulate();
        assertTrue(result.getHomeScore() >= 0, "Home score should be non-negative");
        assertTrue(result.getAwayScore() >= 0, "Away score should be non-negative");
        assertTrue(result.getHomeScore() <= 10, "Home score should be reasonable");
        assertTrue(result.getAwayScore() <= 10, "Away score should be reasonable");
    }
}
