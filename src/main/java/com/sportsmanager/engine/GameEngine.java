package com.sportsmanager.engine;

import com.sportsmanager.domain.League;
import com.sportsmanager.domain.StandingEntry;
import com.sportsmanager.football.FootballSport;

public class GameEngine {

    private GameFacade facade;

    public void startDemo() {
        System.out.println("=== Sports Manager v1.0 ===");

        facade = new GameFacade(new FootballSport());
        facade.initGame(6);

        System.out.println("\nSimulating full season...");
        while (!facade.isSeasonFinished()) {
            facade.advanceWeek();
        }

        facade.printStandings();

        System.out.println("\n=== Season Champion ===");
        System.out.println(facade.getLeader().getName());
    }

    public void setLeague(League league) {
        // kept for compatibility
    }

    public void printStandings() {
        if (facade != null) {
            facade.printStandings();
        }
    }
}