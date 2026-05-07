package com.sportsmanager.engine;

import com.sportsmanager.domain.League;
import com.sportsmanager.domain.Player;
import com.sportsmanager.domain.Season;
import com.sportsmanager.domain.StandingEntry;
import com.sportsmanager.domain.Team;
import com.sportsmanager.sport.Sport;

import java.io.Serializable;
import java.util.Random;

public class GameFacade implements Serializable {

    private static final long serialVersionUID = 1L;

    private Sport sport;
    private League league;
    private Season season;
    private transient Random random = new Random();

    private static final String[] TEAM_NAMES = {
            "Galatasaray", "Fenerbahce", "Besiktas", "Trabzonspor",
            "Basaksehir", "Sivasspor", "Alanyaspor", "Antalyaspor",
            "Lakers", "Celtics", "Bulls", "Warriors",
            "Knicks", "Heat", "Spurs", "Nets"
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
    
    league = new League(sport.getSportName() + " League",
            sport.getSportName());

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
    System.out.println("Total matches: "
            + league.getFixture().getTotalMatchCount());
}

    private void generatePlayers(Team team) {
        if (random == null)
            random = new Random();

        boolean isBasketball = sport.getSportName().equals("Basketball");

        if (isBasketball) {
            generateBasketballPlayers(team);
        } else {
            generateFootballPlayers(team);
        }
    }

    private void generateFootballPlayers(Team team) {
        if (random == null)
            random = new Random();

        String[][] positions = {
                { "GOALKEEPER", "GOALKEEPER" },
                { "DEFENDER", "DEFENDER", "DEFENDER", "DEFENDER", "DEFENDER" },
                { "MIDFIELDER", "MIDFIELDER", "MIDFIELDER", "MIDFIELDER" },
                { "FORWARD", "FORWARD", "FORWARD" }
        };

        String[] allPositions = { "GOALKEEPER", "DEFENDER",
                "MIDFIELDER", "FORWARD" };

        // 2 GK, 5 DEF, 4 MID, 3 FWD = 14 oyuncu
        String[] balanced = {
                "GOALKEEPER", "GOALKEEPER",
                "DEFENDER", "DEFENDER", "DEFENDER", "DEFENDER", "DEFENDER",
                "MIDFIELDER", "MIDFIELDER", "MIDFIELDER", "MIDFIELDER",
                "FORWARD", "FORWARD", "FORWARD"
        };

        int squadSize = sport.getSquadSize() + sport.getSubstituteCount();
        for (int i = 0; i < squadSize; i++) {
            String name = PLAYER_NAMES[random.nextInt(PLAYER_NAMES.length)]
                    + " " + (i + 1);
            int age = 18 + random.nextInt(20);
            int skill = 50 + random.nextInt(50);

            String position = i < balanced.length
                    ? balanced[i]
                    : allPositions[random.nextInt(allPositions.length)];

            com.sportsmanager.football.FootballPlayer.Position pos = com.sportsmanager.football.FootballPlayer.Position
                    .valueOf(position);
            team.addPlayer(new com.sportsmanager.football.FootballPlayer(
                    name, age, skill, pos));
        }
    }

    private void generateBasketballPlayers(Team team) {
        if (random == null)
            random = new Random();

        // PG, SG, SF, PF, C, + yedekler
        String[] balanced = {
                "POINT_GUARD", "SHOOTING_GUARD", "SMALL_FORWARD",
                "POWER_FORWARD", "CENTER",
                "POINT_GUARD", "SHOOTING_GUARD", "SMALL_FORWARD",
                "POWER_FORWARD", "CENTER",
                "POINT_GUARD", "SHOOTING_GUARD"
        };

        com.sportsmanager.basketball.BasketballPlayer.Position[] positions = com.sportsmanager.basketball.BasketballPlayer.Position
                .values();

        int squadSize = sport.getSquadSize() + sport.getSubstituteCount();
        for (int i = 0; i < squadSize; i++) {
            String name = PLAYER_NAMES[random.nextInt(PLAYER_NAMES.length)]
                    + " " + (i + 1);
            int age = 18 + random.nextInt(20);
            int skill = 50 + random.nextInt(50);

            com.sportsmanager.basketball.BasketballPlayer.Position pos = i < balanced.length
                    ? com.sportsmanager.basketball.BasketballPlayer.Position
                            .valueOf(balanced[i])
                    : positions[random.nextInt(positions.length)];

            team.addPlayer(new com.sportsmanager.basketball.BasketballPlayer(
                    name, age, skill, pos));
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
        if (league == null)
            return;
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

    public Sport getSport() {
        return sport;
    }

    public League getLeague() {
        return league;
    }

    public Season getSeason() {
        return season;
    }
}