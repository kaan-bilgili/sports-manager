package com.sportsmanager.football;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FootballPlayerTest {

    @Test
    void testPlayerCreation() {
        FootballPlayer player = new FootballPlayer("John Doe", 25, FootballPosition.MIDFIELDER);
        assertEquals("John Doe", player.getName());
        assertEquals(25, player.getAge());
        assertEquals(FootballPosition.MIDFIELDER, player.getPosition());
    }

    @Test
    void testPlayerRating() {
        FootballPlayer player = new FootballPlayer("Test Player", 22, FootballPosition.FORWARD);
        double rating = player.getOverallRating();
        assertTrue(rating >= 0 && rating <= 100, "Rating should be between 0 and 100, was: " + rating);
    }

    @Test
    void testPlayerInjury() {
        FootballPlayer player = new FootballPlayer("Injured Player", 28, FootballPosition.DEFENDER);
        assertFalse(player.isInjured());

        player.setInjuredForGames(3);
        assertTrue(player.isInjured());
        assertEquals(3, player.getInjuredForGames());

        player.healOneGame();
        assertEquals(2, player.getInjuredForGames());
        assertTrue(player.isInjured());

        player.healOneGame();
        player.healOneGame();
        assertFalse(player.isInjured());
    }

    @Test
    void testPlayerAttributes() {
        FootballPlayer player = new FootballPlayer("Attr Player", 20, FootballPosition.GOALKEEPER);
        var attrs = player.getAttributes();
        assertFalse(attrs.isEmpty(), "Player should have attributes");
        assertEquals(6, attrs.size(), "Football player should have 6 attributes");

        boolean hasPace = attrs.stream().anyMatch(a -> a.getName().equals("Pace"));
        boolean hasShooting = attrs.stream().anyMatch(a -> a.getName().equals("Shooting"));
        boolean hasPassing = attrs.stream().anyMatch(a -> a.getName().equals("Passing"));
        boolean hasDefending = attrs.stream().anyMatch(a -> a.getName().equals("Defending"));
        boolean hasDribbling = attrs.stream().anyMatch(a -> a.getName().equals("Dribbling"));
        boolean hasPhysical = attrs.stream().anyMatch(a -> a.getName().equals("Physical"));

        assertTrue(hasPace && hasShooting && hasPassing && hasDefending && hasDribbling && hasPhysical);
    }
}
