package com.sportsmanager.integration;

import com.sportsmanager.domain.League;
import com.sportsmanager.domain.Season;
import com.sportsmanager.domain.StandingEntry;
import com.sportsmanager.domain.Team;
import com.sportsmanager.engine.GameFacade;
import com.sportsmanager.engine.FixtureGenerator;
import com.sportsmanager.football.FootballPlayer;
import com.sportsmanager.football.FootballSport;
import com.sportsmanager.football.FootballTeam;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FullSeasonTest {

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
    // T20: Tam sezon sonunda tum takimlar mac oynamis olmali
    void testAllTeamsPlayedAfterFullSeason() {
        FootballSport sport = new FootballSport();
        League league = new League("Full Season League");

        league.addTeam(createTeamWithPlayers("Team A", 80));
        league.addTeam(createTeamWithPlayers("Team B", 75));
        league.addTeam(createTeamWithPlayers("Team C", 70));
        league.addTeam(createTeamWithPlayers("Team D", 65));

        FixtureGenerator gen = new FixtureGenerator(sport);
        league.setFixture(gen.generate(league.getTeams()));

        Season season = new Season(1, league);
        while (!season.isFinished()) {
            season.advanceWeek();
        }

        // 4 takim, her biri 6 mac oynamali (3 ev + 3 deplasman)
        for (StandingEntry entry : league.getSortedStandings()) {
            assertEquals(6, entry.getMatchesPlayed());
        }
    }

    @Test
    // T21: Tam sezon sonunda lig lideri belirlenir
    void testLeagueLeaderDeterminedAfterSeason() {
        FootballSport sport = new FootballSport();
        League league = new League("Leader Test League");

        league.addTeam(createTeamWithPlayers("Team A", 80));
        league.addTeam(createTeamWithPlayers("Team B", 75));
        league.addTeam(createTeamWithPlayers("Team C", 70));
        league.addTeam(createTeamWithPlayers("Team D", 65));

        FixtureGenerator gen = new FixtureGenerator(sport);
        league.setFixture(gen.generate(league.getTeams()));

        Season season = new Season(1, league);
        while (!season.isFinished()) {
            season.advanceWeek();
        }

        Team leader = season.getLeader();
        assertNotNull(leader);

        // Lider en fazla puana sahip olmali
        List<StandingEntry> standings = league.getSortedStandings();
        assertEquals(leader.getName(), standings.get(0).getTeam().getName());
    }

    @Test
    // T22: GameFacade uzerinden tam sezon exception firlatmadan calisir
    void testGameFacadeFullSeasonNoException() {
        assertDoesNotThrow(() -> {
            GameFacade facade = new GameFacade(new FootballSport());
            facade.initGame(4);

            while (!facade.isSeasonFinished()) {
                facade.advanceWeek();
            }

            assertNotNull(facade.getLeader());
            assertTrue(facade.isSeasonFinished());
        });
    }
}