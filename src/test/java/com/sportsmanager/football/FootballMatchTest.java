package com.sportsmanager.football;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FootballMatchTest {
    static FootballTeam createTeamWithPlayers(String name, int skillLevel) {
        FootballTeam team = new FootballTeam(name);
        for (int i = 0; i < 11; i++) {
            team.addPlayer(new FootballPlayer(
                    "Player" + i, 25, skillLevel,
                    FootballPlayer.Position.MIDFIELDER));
        }
        return team;
    }

    @Test
    void testMatchIsPlayedAfterSimulation() {
        FootballTeam home = createTeamWithPlayers("Home FC", 75);
        FootballTeam away = createTeamWithPlayers("Away FC", 70);
        FootballMatch match = new FootballMatch(home, away);
        assertFalse(match.isPlayed());
        match.simulate();
        assertTrue(match.isPlayed());
    }
}