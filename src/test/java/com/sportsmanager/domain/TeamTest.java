package com.sportsmanager.domain;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class TeamTest {
    static class TestPlayer extends Player {
        public TestPlayer(String name, int age, int skillLevel) {
            super(name, age, skillLevel);
        }
        @Override
        public String getPosition() { return "TEST"; }
    }
    static class TestTeam extends Team {
        public TestTeam(String name) { super(name); }
    }
    @Test
    // T4: Takima oyuncu eklenir ve listelenir
    void testAddPlayerToTeam() {
        TestTeam team = new TestTeam("Galatasaray");
        assertEquals(0, team.getPlayers().size());
        team.addPlayer(new TestPlayer("Ali", 25, 80));
        team.addPlayer(new TestPlayer("Veli", 23, 70));
        assertEquals(3, team.getPlayers().size()); // BUG: 2 değil 3 yazdım
    }
    @Test
    // T5: Yaralı oyuncu available listesine girmez
    void testAvailablePlayersExcludesInjured() {
        TestTeam team = new TestTeam("Fenerbahce");
        TestPlayer p1 = new TestPlayer("Ali", 25, 80);
        TestPlayer p2 = new TestPlayer("Veli", 23, 70);
        p2.setInjuryGamesRemaining(2);
        team.addPlayer(p1);
        team.addPlayer(p2);
        assertEquals(2, team.getAvailablePlayers().size()); // BUG: injured'ı filtrelemedi
        assertEquals("Ali", team.getAvailablePlayers().get(0).getName());
    }
}