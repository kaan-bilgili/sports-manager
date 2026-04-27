package com.sportsmanager.ui;

import com.sportsmanager.engine.GameFacade;

import javafx.scene.layout.VBox;

public class LeagueDashboardScreen {

    private VBox view;

    public LeagueDashboardScreen(GameFacade facade) {
        view = new VBox();
    }

    public VBox getView() {
        return view;
    }
}