package com.sportsmanager.basketball;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BasketballPlayerTest {

    @Test
    // T23: BasketballPlayer pozisyon dogru atanir
    void testBasketballPlayerPositionAssigned() {
        BasketballPlayer player = new BasketballPlayer(
                "LeBron", 30, 95, BasketballPlayer.Position.SMALL_FORWARD);

        assertEquals("SMALL_FORWARD", player.getPosition());
        assertEquals(BasketballPlayer.Position.SMALL_FORWARD, player.getBasketballPosition());
    }

    @Test
    // T24: BasketballPlayer skill level dogru set edilir
    void testBasketballPlayerSkillLevel() {
        BasketballPlayer player = new BasketballPlayer(
                "Curry", 28, 99, BasketballPlayer.Position.POINT_GUARD);

        assertEquals(99, player.getSkillLevel());
        assertFalse(player.isInjured());
    }

    @Test
    // T25: BasketballPlayer tum pozisyonlar tanimli
    void testAllPositionsExist() {
        BasketballPlayer.Position[] positions = BasketballPlayer.Position.values();
        assertEquals(5, positions.length);
    }
}
