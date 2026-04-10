package com.sportsmanager.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StandingEntryTest {

    static class TestTeam extends Team {
        public TestTeam(String name) {
            super(name);
        }
    }

    @Test
    // Testtt
    void testPointCalculation() {
        TestTeam team = new TestTeam("Team A");
        StandingEntry entry = new StandingEntry(team);

        entry.recordWin(2, 0);
        entry.recordDraw(1, 1);
        entry.recordLoss(0, 3);

        assertEquals(4, entry.getPoints());
        assertEquals(3, entry.getMatchesPlayed());
        assertEquals(-1, entry.getGoalDifference());
    }
}