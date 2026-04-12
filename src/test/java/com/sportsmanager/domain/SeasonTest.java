package com.sportsmanager.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.sportsmanager.engine.FixtureGenerator;
import com.sportsmanager.football.FootballPlayer;
import com.sportsmanager.football.FootballSport;
import com.sportsmanager.football.FootballTeam;

class SeasonTest {

    static FootballTeam createTeamWithPlayers(String name, int skill) {
        FootballTeam team = new FootballTeam(name);
        for (int i = 0; i < 11; i++) {
            team.addPlayer(new FootballPlayer(
                    "Player" + i, 25, skill,
                    FootballPlayer.Position.MIDFIELDER));
        }
        return team;
    }

    @Test
    // T15: Sezon basinda currentWeek 1 olmali
    void testSeasonStartsAtWeekOne() {
        FootballSport sport = new FootballSport();
        League league = new League("Test League");
        league.addTeam(createTeamWithPlayers("Team A", 75));
        league.addTeam(createTeamWithPlayers("Team B", 70));

        FixtureGenerator gen = new FixtureGenerator(sport);
        league.setFixture(gen.generate(league.getTeams()));

        Season season = new Season(1, league);
        assertEquals(1, season.getCurrentWeek());
        assertFalse(season.isFinished());
    }

    @Test
    // T16: Tum haftalar oynandiktan sonra sezon biter
    void testSeasonFinishesAfterAllWeeks() {
        FootballSport sport = new FootballSport();
        League league = new League("Test League");
        league.addTeam(createTeamWithPlayers("Team A", 75));
        league.addTeam(createTeamWithPlayers("Team B", 70));
        league.addTeam(createTeamWithPlayers("Team C", 65));
        league.addTeam(createTeamWithPlayers("Team D", 60));

        FixtureGenerator gen = new FixtureGenerator(sport);
        league.setFixture(gen.generate(league.getTeams()));

        Season season = new Season(1, league);

        while (!season.isFinished()) {
            season.advanceWeek();
        }

        assertTrue(season.isFinished());
        assertNotNull(season.getLeader());
    }
}