package com.sportsmanager.engine;

import com.sportsmanager.domain.League;
import com.sportsmanager.domain.Player;
import com.sportsmanager.domain.Season;
import com.sportsmanager.domain.StandingEntry;
import com.sportsmanager.domain.Team;
import com.sportsmanager.sport.Sport;

import java.util.List;
import java.util.Random;

public class GameFacade {

    private Sport sport;
    private League league;
    private Season season;
    private final Random random = new Random();

    private static final String[] TEAM_NAMES = {
        "Galatasaray", "Fenerbahce", "Besiktas", "Trabzonspor",
        "Basaksehir", "Sivasspor", "Alanyaspor", "Antalyaspor"
    };

    private static final String[] PLAYER_NAMES = {
        "Ali", "Veli", "Mehmet", "Ahmet", "Mustafa",
        "Hasan", "Huseyin", "Ibrahim", "Ismail", "Osman",
        "Yusuf", "Murat", "Emre", "Burak", "Arda"
    };

    public GameFacade(Sport sport) {
        this.sport = sport;
    }

    public void initGame(int teamCount) {
        league = new League(sport.getSportName() + " League");

        for (int i = 0; i < teamCount && i < TEAM_NAMES.length; i++) {
            Team team = sport.createTeam(TEAM_NAMES[i]);
            generatePlayers(team);
            league.addTeam(team);
        }

        FixtureGenerator generator = new FixtureGenerator(sport);
        league.setFixture(generator.generate(league.getTeams()));

        season = new Season(1, league);

        System.out.println("Game initialized: " + league.getName());
        System.out.println("Teams: " + league.getTeams().size());
        System.out.println("Total matches: " + league.getFixture().getTotalMatchCount());
    }

    private void generatePlayers(Team team) {
        int squadSize = sport.getSquadSize() + sport.getSubstituteCount();
        for (int i = 0; i < squadSize; i++) {
            String name = PLAYER_NAMES[random.nextInt(PLAYER_NAMES.length)]
                    + " " + (i + 1);
            int age = 18 + random.nextInt(20);
            int skill = 50 + random.nextInt(50);
            Player player = sport.createPlayer(name, age, skill);
            team.addPlayer(player);
        }
    }

    public void advanceWeek() {
        if (season == null) {
            System.out.println("Game not initialized!");
            return;
        }
        season.advanceWeek();
        System.out.println("Week advanced. Current week: " + season.getCurrentWeek());
    }

    public void printStandings() {
        if (league == null) return;
        System.out.println("\n=== " + league.getName() + " Standings ===");
        System.out.printf("%-20s %3s %3s %3s %3s %4s %4s%n",
                "Team", "P", "W", "D", "L", "GD", "Pts");
        System.out.println("-".repeat(50));
        for (StandingEntry entry : league.getSortedStandings()) {
            System.out.printf("%-20s %3d %3d %3d %3d %4d %4d%n",
                    entry.getTeam().getName(),
                    entry.getMatchesPlayed(),
                    entry.getWins(),
                    entry.getDraws(),
                    entry.getLosses(),
                    entry.getGoalDifference(),
                    entry.getPoints());
        }
    }

    public boolean isSeasonFinished() {
        return season != null && season.isFinished();
    }

    public Team getLeader() {
        return season != null ? season.getLeader() : null;
    }

    public Sport getSport() { return sport; }
    public League getLeague() { return league; }
    public Season getSeason() { return season; }
}