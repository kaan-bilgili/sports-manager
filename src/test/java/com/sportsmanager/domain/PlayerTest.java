package com.sportsmanager.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    static class TestPlayer extends Player {
        public TestPlayer(String name, int age, int skillLevel) {
            super(name, age, skillLevel);
        }
        @Override
        public String getPosition() { return "TEST"; }
    }

    @Test
    // T1: Oyuncu yaralanma durumu dogru set edilir
    void testPlayerInjurySetCorrectly() {
        TestPlayer player = new TestPlayer("Ali", 25, 80);
        assertFalse(player.isInjured());

        player.setInjuryGamesRemaining(3);
        assertTrue(player.isInjured());
        assertEquals(3, player.getInjuryGamesRemaining());
    }

    @Test
    // T2: Yaralanma sayaci azaldikca injured false olur
    void testPlayerInjuryDecrement() {
        TestPlayer player = new TestPlayer("Veli", 22, 70);
        player.setInjuryGamesRemaining(1);
        assertTrue(player.isInjured());

        player.decrementInjury();
        assertFalse(player.isInjured());
        assertEquals(0, player.getInjuryGamesRemaining());
    }
}