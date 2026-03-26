package com.sportsmanager.ui;

import com.sportsmanager.football.FootballSport;
import com.sportsmanager.football.FootballTeam;
import com.sportsmanager.core.model.AbstractLeague;
import com.sportsmanager.core.interfaces.Sport;
import com.sportsmanager.util.TeamGenerator;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.List;

public class NewGameView {
    private final MainApp app;
    private VBox root;

    private ComboBox<String> sportCombo;
    private ComboBox<Integer> teamCountCombo;
    private TextField managerNameField;
    private ComboBox<String> teamCombo;
    private List<FootballTeam> generatedTeams;

    public NewGameView(MainApp app) {
        this.app = app;
        buildUI();
    }

    private void buildUI() {
        root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.getStyleClass().add("root");

        Text title = new Text("Sports Manager");
        title.getStyleClass().add("title-text");

        Text subtitle = new Text("Create New Game");
        subtitle.getStyleClass().add("subtitle-text");

        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(15);
        form.setAlignment(Pos.CENTER);
        form.setMaxWidth(400);

        Label sportLabel = new Label("Sport:");
        sportLabel.getStyleClass().add("form-label");
        sportCombo = new ComboBox<>(FXCollections.observableArrayList("Football"));
        sportCombo.setValue("Football");
        sportCombo.setMaxWidth(Double.MAX_VALUE);

        Label teamCountLabel = new Label("Number of Teams:");
        teamCountLabel.getStyleClass().add("form-label");
        teamCountCombo = new ComboBox<>(FXCollections.observableArrayList(8, 10, 12, 16, 20));
        teamCountCombo.setValue(16);
        teamCountCombo.setMaxWidth(Double.MAX_VALUE);
        teamCountCombo.setOnAction(e -> refreshTeams());

        Label managerLabel = new Label("Manager Name:");
        managerLabel.getStyleClass().add("form-label");
        managerNameField = new TextField("Manager");
        managerNameField.setPromptText("Enter your name");

        Label teamLabel = new Label("Choose Your Team:");
        teamLabel.getStyleClass().add("form-label");
        teamCombo = new ComboBox<>();
        teamCombo.setMaxWidth(Double.MAX_VALUE);

        refreshTeams();

        form.add(sportLabel, 0, 0);
        form.add(sportCombo, 1, 0);
        form.add(teamCountLabel, 0, 1);
        form.add(teamCountCombo, 1, 1);
        form.add(managerLabel, 0, 2);
        form.add(managerNameField, 1, 2);
        form.add(teamLabel, 0, 3);
        form.add(teamCombo, 1, 3);

        ColumnConstraints col1 = new ColumnConstraints(140);
        ColumnConstraints col2 = new ColumnConstraints(220);
        form.getColumnConstraints().addAll(col1, col2);

        Button startBtn = new Button("Start Game");
        startBtn.getStyleClass().add("primary-button");
        startBtn.setOnAction(e -> startGame());
        startBtn.setPrefWidth(200);

        root.getChildren().addAll(title, subtitle, form, startBtn);
    }

    private void refreshTeams() {
        int count = teamCountCombo.getValue() != null ? teamCountCombo.getValue() : 16;
        generatedTeams = TeamGenerator.generateTeams(count, new FootballSport());
        List<String> names = generatedTeams.stream().map(FootballTeam::getName).toList();
        teamCombo.setItems(FXCollections.observableArrayList(names));
        if (!names.isEmpty()) teamCombo.setValue(names.get(0));
    }

    private void startGame() {
        String selectedTeamName = teamCombo.getValue();
        if (selectedTeamName == null || generatedTeams == null) return;

        Sport sport = new FootballSport();
        AbstractLeague league = sport.createLeague("Premier League", generatedTeams);
        league.generateFixtures();

        FootballTeam managedTeam = generatedTeams.stream()
                .filter(t -> t.getName().equals(selectedTeamName))
                .findFirst().orElse(generatedTeams.get(0));

        DashboardView dashboard = new DashboardView(app, league, managedTeam);
        app.showDashboard(dashboard);
    }

    public VBox getRoot() { return root; }
}
