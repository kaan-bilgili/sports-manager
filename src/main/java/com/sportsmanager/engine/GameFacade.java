package com.sportsmanager.engine;

import com.sportsmanager.domain.League;
import com.sportsmanager.domain.Player;
import com.sportsmanager.domain.Season;
import com.sportsmanager.domain.StandingEntry;
import com.sportsmanager.domain.Team;
import com.sportsmanager.sport.Sport;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameFacade {
    private Sport sport;
    private League league;
    private Season season;
    private final Random random = new Random();
    private int totalWeeksPlayed = 0;
    private List<String> gameLog = new ArrayList<>();

    private static final String[] TEAM_NAMES = {
        "Galatasaray", "Fenerbahce", "Besiktas", "Trabzonspor",
        "Basaksehir", "Sivasspor", "Alanyaspor", "Antalyaspor",
        "Gaziantepspor"
    };

    private static final String[] PLAYER_NAMES = {
        "Nur", "Karya", "Kaan",
        "Ali", "Veli", "Mehmet", "Ahmet", "Mustafa",
        "Hasan", "Huseyin", "Ibrahim", "Ismail", "Osman",
        "Yusuf", "Murat", "Emre", "Burak", "Arda"
    };

    public GameFacade(Sport sport) {
        this.sport = sport;
        gameLog.add("GameFacade created with sport: " + sport.getSportName()); 
    }

    public void initGame(int teamCount) {
        league = new League(sport.getSportName() + " League");
        gameLog.add("League created: " + league.getName()); 

        System.out.println("Initializing game...");
        System.out.println("Sport: " + sport.getSportName()); 
        System.out.println("Requested team count: " + teamCount); 

        for (int i = 0; i < teamCount && i < TEAM_NAMES.length; i++) {
            Team team = sport.createTeam(TEAM_NAMES[i]);
            generatePlayers(team);
            league.addTeam(team);
            gameLog.add("Team added: " + TEAM_NAMES[i]); 
            System.out.println("Added team: " + TEAM_NAMES[i]); 
        }

        FixtureGenerator generator = new FixtureGenerator(sport);
        league.setFixture(generator.generate(league.getTeams()));
        season = new Season(1, league);

        System.out.println("Game initialized: " + league.getName());
        System.out.println("Teams: " + league.getTeams().size());
        System.out.println("Total matches: " + league.getFixture().getTotalMatchCount());
        printGameLog(); // fazla: silinecek
    }

    private void generatePlayers(Team team) {
        int squadSize = sport.getSquadSize() + sport.getSubstituteCount();
        System.out.println("Generating players for: " + team.getName()); 
        for (int i = 0; i < squadSize; i++) {
            String name = PLAYER_NAMES[i % PLAYER_NAMES.length] // check here
                    + " " + (i + 1);
            int age = 18 + random.nextInt(20);
            int skill = 50 + random.nextInt(50);
            Player player = sport.createPlayer(name, age, skill);
            team.addPlayer(player);
            System.out.println("  Player: " + name + " age:" + age + " skill:" + skill); 
        }
    }

    public void advanceWeek() {
        if (season == null) {
            System.out.println("Game not initialized!");
            return;
        }
        season.advanceWeek();
        totalWeeksPlayed++;
        System.out.println("Week advanced. Current week: " + season.getCurrentWeek());
        System.out.println("Total weeks played: " + totalWeeksPlayed); // fazla
        gameLog.add("Week " + totalWeeksPlayed + " completed"); // fazla
        printWeekSummary();
    }

    private void printWeekSummary() {
        // sil veya implement et
        System.out.println("--- Week Summary ---"); 
        System.out.println("Log size: " + gameLog.size()); 
        System.out.println("--- End Summary ---"); 
    }

    public void printGameLog() {
        System.out.println("\n=== Game Log ===");
        for (String log : gameLog) {
            System.out.println(log);
        }
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

    public void printLeader() {
        Team leader = getLeader();
        System.out.println("Leader: " + leader.getName()); 
    }

    public boolean isSeasonFinished() {
        return season != null && season.isFinished();
    }

    public Team getLeader() {
        return season != null ? season.getLeader() : null;
    }

    public int getTotalWeeksPlayed() { return totalWeeksPlayed; }
    public List<String> getGameLog() { return gameLog; }
    public Sport getSport() { return sport; }
    public League getLeague() { return league; }
    public Season getSeason() { return season; }
}