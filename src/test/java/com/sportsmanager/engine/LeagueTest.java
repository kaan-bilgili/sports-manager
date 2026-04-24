package com.sportsmanager.engine;

import com.sportsmanager.domain.League;
import com.sportsmanager.domain.StandingEntry;
import com.sportsmanager.football.FootballMatch;
import com.sportsmanager.football.FootballPlayer;
import com.sportsmanager.football.FootballTeam;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeagueTest {

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
    // T13: Mac sonrasi her iki takim toplam puan aliyo
    void testStandingsUpdatedAfterMatch() {
        League league = new League("Test League");
        FootballTeam teamA = createTeamWithPlayers("Team A", 80);
        FootballTeam teamB = createTeamWithPlayers("Team B", 60);
        league.addTeam(teamA);
        league.addTeam(teamB);

        // Manuel galibiyet olustur — rastgelelige bagli olmamali
        FootballMatch match = new FootballMatch(teamA, teamB) {
            @Override
            public void simulate() {
                homeScore = 2;
                awayScore = 0;
                played = true;
            }
        };

        match.simulate();
        league.updateStandings(match);

        StandingEntry entryA = league.getStandingForTeam("Team A");
        StandingEntry entryB = league.getStandingForTeam("Team B");

        assertEquals(1, entryA.getMatchesPlayed());
        assertEquals(1, entryB.getMatchesPlayed());
        assertEquals(3, entryA.getPoints()); // galibiyet = 3 puan
        assertEquals(0, entryB.getPoints()); // maglubiyet = 0 puan
        assertEquals(3, entryA.getPoints() + entryB.getPoints()); // toplam = 3
    }

    @Test
    // T14: Beraberlikte her iki takim 1'er puan alir
    void testDrawGivesOnePointEach() {
        League league = new League("Test League");
        FootballTeam teamA = createTeamWithPlayers("Team A", 75);
        FootballTeam teamB = createTeamWithPlayers("Team B", 75);
        league.addTeam(teamA);
        league.addTeam(teamB);

        FootballMatch match = new FootballMatch(teamA, teamB) {
            @Override
            public void simulate() {
                homeScore = 1;
                awayScore = 1;
                played = true;
            }
        };

        match.simulate();
        league.updateStandings(match);

        StandingEntry entryA = league.getStandingForTeam("Team A");
        StandingEntry entryB = league.getStandingForTeam("Team B");

        assertEquals(1, entryA.getPoints());
        assertEquals(1, entryB.getPoints());
    }
}