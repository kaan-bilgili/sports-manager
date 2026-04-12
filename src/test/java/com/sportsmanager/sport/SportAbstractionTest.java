package com.sportsmanager.sport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.sportsmanager.domain.Match;
import com.sportsmanager.domain.Player;
import com.sportsmanager.domain.Team;
import com.sportsmanager.football.FootballSport;

class SportAbstractionTest {

    @Test
    // T17: Sport interface uzerinden FootballSport calisir
    void testSportInterfacePolymorphism() {
        Sport sport = new FootballSport();

        assertEquals("Football", sport.getSportName());
        assertEquals(11, sport.getSquadSize());
        assertEquals(3, sport.getSubstituteCount());
    }

    @Test
    // T18: Sport interface uzerinden takim ve oyuncu olusturulur
    void testSportCreatesTeamAndPlayer() {
        Sport sport = new FootballSport();

        Team team = sport.createTeam("Test FC");
        assertNotNull(team);
        assertEquals("Test FC", team.getName());

        Player player = sport.createPlayer("KAAN", 25, 80);
        assertNotNull(player);
        assertEquals("KAAN", player.getName());
        assertEquals(80, player.getSkillLevel());
    }

    @Test
    // T19: Sport interface uzerinden mac olusturulur ve simulate edilir
    void testSportCreatesAndSimulatesMatch() {
        Sport sport = new FootballSport();

        Team home = sport.createTeam("Home FC");
        Team away = sport.createTeam("Away FC");

        for (int i = 0; i < 11; i++) {
            home.addPlayer(sport.createPlayer("HomePlayer" + i, 25, 75));
            away.addPlayer(sport.createPlayer("AwayPlayer" + i, 25, 70));
        }

        Match match = sport.createMatch(home, away);
        assertNotNull(match);
        assertFalse(match.isPlayed());

        match.simulate();
        assertTrue(match.isPlayed());
    }
}