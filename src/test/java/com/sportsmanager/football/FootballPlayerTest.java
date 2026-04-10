package com.sportsmanager.football;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

class FootballPlayerTest {

    @Test
    // T6: FootballPlayer pozisyon dogru atanir
    void testFootballPlayerPositionAssigned() {
        FootballPlayer player = new FootballPlayer(
                "Ronaldo", 30, 95, FootballPlayer.Position.FORWARD);

        assertEquals("FORWARD", player.getPosition());
        assertEquals(FootballPlayer.Position.FORWARD, player.getFootballPosition());
    }

    @Test
    // T7: FootballPlayer skill level dogru set edilir
    void testFootballPlayerSkillLevel() {
        FootballPlayer player = new FootballPlayer(
                "Messi", 28, 99, FootballPlayer.Position.MIDFIELDER);

        assertEquals(99, player.getSkillLevel());
        assertFalse(player.isInjured());
    }
}