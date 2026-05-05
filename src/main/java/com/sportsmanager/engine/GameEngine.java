package com.sportsmanager.engine;

import com.sportsmanager.domain.League;

public class GameEngine {

    private GameFacade facade;

    public void startDemo() {
        System.out.println("=== Sports Manager v2.0 ===");
        System.out.println("Starting JavaFX GUI...");
        com.sportsmanager.app.MainApp.main(new String[]{});
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