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
    // T8: Simulasyon sonrasi mac oynanmis sayilir
    void testMatchIsPlayedAfterSimulation() {
        FootballTeam home = createTeamWithPlayers("Home FC", 75);
        FootballTeam away = createTeamWithPlayers("Away FC", 70);
        FootballMatch match = new FootballMatch(home, away);

        assertFalse(match.isPlayed());
        match.simulate();
        assertTrue(match.isPlayed());
    }

    @Test
    // T9: Simulasyon sonrasi skorlar 0 veya daha buyuk
    void testMatchScoresAreNonNegative() {
        FootballTeam home = createTeamWithPlayers("Home FC", 75);
        FootballTeam away = createTeamWithPlayers("Away FC", 70);
        FootballMatch match = new FootballMatch(home, away);

        match.simulate();

        assertTrue(match.getHomeScore() >= 0);
        assertTrue(match.getAwayScore() >= 0);
    }

    @Test
    // T10: Ayni mac iki kez simulate edilemez
    void testMatchCannotBeSimulatedTwice() {
        FootballTeam home = createTeamWithPlayers("Home FC", 75);
        FootballTeam away = createTeamWithPlayers("Away FC", 70);
        FootballMatch match = new FootballMatch(home, away);

        match.simulate();
        int firstHomeScore = match.getHomeScore();
        int firstAwayScore = match.getAwayScore();

        match.simulate();

        assertEquals(firstHomeScore, match.getHomeScore());
        assertEquals(firstAwayScore, match.getAwayScore());
    }
}