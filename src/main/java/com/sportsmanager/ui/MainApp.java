package com.sportsmanager.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Sports Manager");
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(650);

        showNewGameView();
        primaryStage.show();
    }

    public void showNewGameView() {
        NewGameView view = new NewGameView(this);
        Scene scene = new Scene(view.getRoot(), 900, 650);
        applyStyles(scene);
        primaryStage.setScene(scene);
    }

    public void showDashboard(DashboardView dashboard) {
        Scene scene = new Scene(dashboard.getRoot(), 1100, 720);
        applyStyles(scene);
        primaryStage.setScene(scene);
    }

    private void applyStyles(Scene scene) {
        try {
            var css = getClass().getResource("/com/sportsmanager/main.css");
            if (css != null) scene.getStylesheets().add(css.toExternalForm());
        } catch (Exception ignored) {}
    }

    public Stage getPrimaryStage() { return primaryStage; }

    public static void main(String[] args) {
        launch(args);
    }
}
