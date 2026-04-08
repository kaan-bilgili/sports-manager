package com.sportsmanager.app;

import com.sportsmanager.engine.GameEngine;

public class Main {
    public static void main(String[] args) {
        System.out.println(" Sports Manager v1.0");

        GameEngine engine = new GameEngine();
        engine.startDemo();
    }
}