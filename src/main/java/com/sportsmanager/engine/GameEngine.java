package com.sportsmanager.engine;

import com.sportsmanager.domain.League;
import com.sportsmanager.domain.StandingEntry;

public class GameEngine {

    private League currentLeague;

    public void startDemo() {
        System.out.println("=== Sports Manager - Game Engine ===");
        System.out.println("Domain katmani yuklendi.");
        System.out.println("Fixture, League, Season hazir.");
    }

    public void setLeague(League league) {
        this.currentLeague = league;
    }

    public League getCurrentLeague() {
        return currentLeague;
    }

    public void printStandings() {
        if (currentLeague == null) {
            System.out.println("Lig bulunamadi.");
            return;
        }
        System.out.println("\n=== " + currentLeague.getName() + " Standings ===");
        for (StandingEntry entry : currentLeague.getSortedStandings()) {
            System.out.println(entry);
        }
    }
}