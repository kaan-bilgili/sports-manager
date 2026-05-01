package com.sportsmanager.app;

import com.sportsmanager.ui.MainMenuScreen;
import com.sportsmanager.ui.SportSelectionScreen;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Sports Manager");

        showMainMenu();
        primaryStage.show();
    }

    public static void showMainMenu() {
        MainMenuScreen screen = new MainMenuScreen();
        Scene scene = new Scene(screen.getView(), 900, 650);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
    }

    public static void showSportSelection() {
        SportSelectionScreen screen = new SportSelectionScreen();
        Scene scene = new Scene(screen.getView(), 900, 650);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}