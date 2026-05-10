package com.sportsmanager.engine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.sportsmanager.domain.League;
import com.sportsmanager.domain.Match;
import com.sportsmanager.domain.Player;
import com.sportsmanager.domain.Season;
import com.sportsmanager.domain.StandingEntry;
import com.sportsmanager.domain.Team;
import com.sportsmanager.sport.Sport;

public class GameFacade implements Serializable {

    private static final long serialVersionUID = 1L;

    private Sport sport;
    private League league;
    private Season season;
    private Team playerTeam;
    private transient Random random = new Random();

    private static final String[] FOOTBALL_TEAM_NAMES = {
            "🦁 Galatasaray",
            "🐤 Fenerbahce",
            "🦅 Besiktas",
            "⚡ Trabzonspor",
            "🌊 Basaksehir",
            "🔥 Sivasspor",
            "🌙 Alanyaspor",
            "🐯 Antalyaspor"
    };

    private static final String[] BASKETBALL_TEAM_NAMES = {
            "🏆 Lakers",
            "🍀 Celtics",
            "🐂 Bulls",
            "⚔️ Warriors",
            "🗽 Knicks",
            "🌡️ Heat",
            "🤠 Spurs",
            "🕸️ Nets"
    };

    private static final String[] PLAYER_NAMES = {

            "Ali", "Mehmet", "Ahmet", "Mustafa", "Hasan",
            "Huseyin", "Ibrahim", "Murat", "Emre", "Burak",
            "Can", "Ozan", "Kerem", "Tuna", "Ege",
            "Kaan", "Berk", "Onur", "Serkan", "Tolga",
            "Umut", "Yigit", "Doruk", "Batuhan", "Arda",
            "Mert", "Baris", "Cenk", "Furkan", "Deniz",
            "Mateo", "Lorenzo", "Marco", "Adrian", "Victor",
            "Nico", "Ruben", "Thiago", "Andre", "Felix",
            "Dario", "Sandro", "Enzo", "Raul", "Bruno",
            "Milan", "Tomas", "Leon", "Oscar", "Diego",
            "Hugo", "Javier", "Pablo", "Alvaro", "Sergi",
            "Ricardo", "Daniel", "Matias", "Gabriel", "Samuel",
            "Ivan", "Damian", "Alex", "Martin", "Julian",
            "Noah", "Elias", "Lucas", "Jonas", "Anton"
    };

    public GameFacade(Sport sport) {
        this.sport = sport;
    }

    public void initGame(int teamCount) {
        league = new League(sport.getSportName() + " League",
                sport.getSportName());

        boolean isBasketball = sport.getSportName().equals("Basketball");
        String[] teamNames = isBasketball
                ? BASKETBALL_TEAM_NAMES
                : FOOTBALL_TEAM_NAMES;

        for (int i = 0; i < teamCount && i < teamNames.length; i++) {
            Team team = sport.createTeam(teamNames[i]);
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

    public void setPlayerTeam(Team team) {
        this.playerTeam = team;
    }

    public Team getPlayerTeam() {
        return playerTeam;
    }

    public boolean isPlayerMatch(Match match) {
        return playerTeam != null &&
                (match.getHomeTeam().getName().equals(playerTeam.getName()) ||
                        match.getAwayTeam().getName().equals(playerTeam.getName()));
    }

    public Match getNextPlayerMatch() {
        for (int week = 1; week <= league.getFixture().getTotalRounds(); week++) {
            List<Match> matches = league.getFixture().getMatchesForRound(week);
            for (Match m : matches) {
                if (!m.isPlayed() && isPlayerMatch(m))
                    return m;
            }
        }
        return null;
    }

    public void simulateOtherMatches() {
        int week = season.getCurrentWeek();
        List<Match> matches = league.getFixture().getMatchesForRound(week);
        for (Match m : matches) {
            if (!m.isPlayed() && !isPlayerMatch(m)) {
                m.simulate();
                league.updateStandings(m);
            }
        }
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

        // 22 oyuncu: 2 GK, 7 DEF, 7 MID, 6 FWD
        String[] balanced = {
                "GOALKEEPER", "GOALKEEPER",
                "DEFENDER", "DEFENDER", "DEFENDER", "DEFENDER",
                "DEFENDER", "DEFENDER", "DEFENDER",
                "MIDFIELDER", "MIDFIELDER", "MIDFIELDER", "MIDFIELDER",
                "MIDFIELDER", "MIDFIELDER", "MIDFIELDER",
                "FORWARD", "FORWARD", "FORWARD",
                "FORWARD", "FORWARD", "FORWARD"
        };

        for (int i = 0; i < 22; i++) {
            String name = PLAYER_NAMES[random.nextInt(PLAYER_NAMES.length)]
                    + " " + (i + 1);
            int age = 18 + random.nextInt(20);
            int skill = 50 + random.nextInt(50);

            com.sportsmanager.football.FootballPlayer.Position pos = com.sportsmanager.football.FootballPlayer.Position
                    .valueOf(balanced[i]);
            team.addPlayer(new com.sportsmanager.football.FootballPlayer(
                    name, age, skill, pos));
        }
    }

    private void generateBasketballPlayers(Team team) {
        if (random == null)
            random = new Random();

        // 15 oyuncu: 3 PG, 3 SG, 3 SF, 3 PF, 3 C
        String[] balanced = {
                "POINT_GUARD", "POINT_GUARD", "POINT_GUARD",
                "SHOOTING_GUARD", "SHOOTING_GUARD", "SHOOTING_GUARD",
                "SMALL_FORWARD", "SMALL_FORWARD", "SMALL_FORWARD",
                "POWER_FORWARD", "POWER_FORWARD", "POWER_FORWARD",
                "CENTER", "CENTER", "CENTER"
        };

        for (int i = 0; i < 15; i++) {
            String name = PLAYER_NAMES[random.nextInt(PLAYER_NAMES.length)]
                    + " " + (i + 1);
            int age = 18 + random.nextInt(20);
            int skill = 50 + random.nextInt(50);

            com.sportsmanager.basketball.BasketballPlayer.Position pos = com.sportsmanager.basketball.BasketballPlayer.Position
                    .valueOf(balanced[i]);
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
        System.out.println("Week advanced. Current week: "
                + season.getCurrentWeek());
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

    private int trainingsLeft = 3;

    public int getTrainingsLeft() { return trainingsLeft; }

    public boolean canTrain() { return trainingsLeft > 0; }

    public void useTraining() {
        if (trainingsLeft > 0) trainingsLeft--;
    }

    public void resetTrainings() { trainingsLeft = 3; }

    private com.sportsmanager.football.Tactic currentTactic =
        com.sportsmanager.football.Tactic.F442;

    private com.sportsmanager.basketball.BasketballTactic currentBasketballTactic =
        com.sportsmanager.basketball.BasketballTactic.BALANCED;

public com.sportsmanager.football.Tactic getCurrentTactic() {
    return currentTactic;
}

public void setTactic(com.sportsmanager.football.Tactic tactic) {
    this.currentTactic = tactic;
}

public com.sportsmanager.basketball.BasketballTactic
getCurrentBasketballTactic() {
    return currentBasketballTactic;
}

public void setBasketballTactic(
        com.sportsmanager.basketball.BasketballTactic tactic) {
    this.currentBasketballTactic = tactic;
}

public List<Player> getStartingXI(Team team) {
    if (sport.getSportName().equals("Football")) {
        return com.sportsmanager.football.TacticService
                .getStartingXI(team, currentTactic);
    }
    if (team instanceof com.sportsmanager.basketball.BasketballTeam) {
    return com.sportsmanager.basketball.BasketballTacticService
            .getStarters(team, currentBasketballTactic);
}
    return new ArrayList<>();
}

public List<Player> getBench(Team team) {
    if (sport.getSportName().equals("Football")) {
        return com.sportsmanager.football.TacticService
                .getBench(team, currentTactic);
    }
    if (team instanceof com.sportsmanager.basketball.BasketballTeam) {
    return com.sportsmanager.basketball.BasketballTacticService
            .getBench(team, currentBasketballTactic);
}
    return new ArrayList<>();
}

    }