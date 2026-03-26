package com.sportsmanager.football;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FootballTeamTest {

    @Test
    void testTeamCreation() {
        FootballTeam team = new FootballTeam("Test FC");
        assertEquals("Test FC", team.getName());
        assertNotNull(team.getPlayers());
        assertTrue(team.getPlayers().isEmpty());
    }

    @Test
    void testAddRemovePlayer() {
        FootballTeam team = new FootballTeam("Test FC");
        FootballPlayer player = new FootballPlayer("Player A", 22, FootballPosition.FORWARD);

        team.addPlayer(player);
        assertEquals(1, team.getPlayers().size());

        team.removePlayer(player);
        assertEquals(0, team.getPlayers().size());
    }

    @Test
    void testGetAvailablePlayers() {
        FootballTeam team = new FootballTeam("Test FC");
        FootballPlayer healthy = new FootballPlayer("Healthy", 25, FootballPosition.MIDFIELDER);
        FootballPlayer injured = new FootballPlayer("Injured", 25, FootballPosition.DEFENDER);

        team.addPlayer(healthy);
        team.addPlayer(injured);
        injured.setInjuredForGames(2);

        var available = team.getAvailablePlayers();
        assertEquals(1, available.size());
        assertTrue(available.contains(healthy));
        assertFalse(available.contains(injured));
    }

    @Test
    void testSetTactic() {
        FootballTeam team = new FootballTeam("Test FC");
        team.setTactic(FootballTactic.FOUR_THREE_THREE);
        assertEquals(FootballTactic.FOUR_THREE_THREE, team.getCurrentTactic());
    }
}
