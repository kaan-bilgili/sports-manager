package com.sportsmanager.ui;

import java.util.List;

import com.sportsmanager.app.MainApp;
import com.sportsmanager.domain.Player;
import com.sportsmanager.domain.Team;
import com.sportsmanager.engine.GameFacade;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TeamManagementScreen {

    private BorderPane view;
    private GameFacade facade;
    private Team selectedTeam;
    private Button activeTeamBtn;

    public TeamManagementScreen(GameFacade facade) {
        this.facade = facade;
        // our teams come by default
        if (facade.getPlayerTeam() != null) {
            this.selectedTeam = facade.getPlayerTeam();
        } else {
            this.selectedTeam = facade.getLeague().getTeams().get(0);
        }
        buildUI();
    }

    private void buildUI() {
        view = new BorderPane();
        view.setPrefSize(900, 650);
        view.setStyle("-fx-background-color: " + AppStyle.BG_MAIN + ";");
        view.setTop(buildHeader());
        view.setLeft(buildTeamList());
        view.setCenter(buildPlayerTable());
    }

    private Label buildHeader() {
        Label lbl = new Label("Team Management — " + selectedTeam.getName());
        lbl.setMaxWidth(Double.MAX_VALUE);
        lbl.setStyle("-fx-background-color: " + AppStyle.BG_PANEL + "; "
                + "-fx-text-fill: " + AppStyle.ACCENT_BLUE + "; "
                + "-fx-font-size: 16px; -fx-font-weight: bold; "
                + "-fx-padding: 12px;");
        return lbl;
    }

    private VBox buildTeamList() {
        VBox box = new VBox(8);
        box.setPrefWidth(175);
        box.setStyle("-fx-background-color: " + AppStyle.BG_PANEL
                + "; -fx-padding: 12px;");

        Label title = new Label("Teams");
        title.setStyle("-fx-text-fill: " + AppStyle.ACCENT_BLUE
                + "; -fx-font-weight: bold; -fx-font-size: 14px;");
        box.getChildren().add(title);

        for (Team team : facade.getLeague().getTeams()) {
            Button btn = new Button(team.getName());
            btn.setPrefSize(155, 34);
            btn.setStyle(AppStyle.BTN_NORMAL);

            if (team == selectedTeam) {
                btn.setStyle(AppStyle.BTN_SELECTED);
                activeTeamBtn = btn;
            }

            btn.setOnAction(e -> {
                if (activeTeamBtn != null)
                    activeTeamBtn.setStyle(AppStyle.BTN_NORMAL);
                activeTeamBtn = btn;
                btn.setStyle(AppStyle.BTN_SELECTED);
                selectedTeam = team;
                view.setTop(buildHeader());
                view.setCenter(buildPlayerTable());
            });

            btn.setOnMouseEntered(e -> {
                if (btn != activeTeamBtn)
                    btn.setStyle(AppStyle.BTN_HOVER);
            });
            btn.setOnMouseExited(e -> {
                if (btn != activeTeamBtn)
                    btn.setStyle(AppStyle.BTN_NORMAL);
            });

            box.getChildren().add(btn);
        }

        Button backBtn = new Button("← Back");
        backBtn.setPrefSize(155, 34);
        backBtn.setStyle(AppStyle.BTN_BACK);
        backBtn.setOnAction(e -> {
            LeagueDashboardScreen dashboard = new LeagueDashboardScreen(facade);
            javafx.scene.Scene scene = new javafx.scene.Scene(
                    dashboard.getView(), 900, 650);
            MainApp.primaryStage.setScene(scene);
            MainApp.primaryStage.sizeToScene();
        });

        box.getChildren().add(backBtn);
        return box;
    }

    private VBox buildPlayerTable() {
    VBox container = new VBox(10);
    container.setPadding(new Insets(15));

    boolean isFootball = selectedTeam instanceof
            com.sportsmanager.football.FootballTeam;
    boolean isBasketball = selectedTeam instanceof
            com.sportsmanager.basketball.BasketballTeam;
    boolean isPlayerTeam = facade.getPlayerTeam() != null &&
            selectedTeam.getName().equals(
                    facade.getPlayerTeam().getName());

    int totalSize = selectedTeam.getPlayers().size();
    int availableSize = selectedTeam.getAvailablePlayers().size();
    int injuredSize = totalSize - availableSize;
    int matchdaySize = isFootball ? 18 : isBasketball ? 12 : totalSize;
    int starterSize = isFootball ? 11 : isBasketball ? 5 : totalSize;
    int benchSize = isFootball ? 7 : isBasketball ? 7 : 0;

    HBox info = new HBox(15);
    info.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

    addInfoLabel(info, "Total: " + totalSize, "white");
    addInfoLabel(info, "Matchday: " + matchdaySize, AppStyle.ACCENT_BLUE);
    addInfoLabel(info, "Starting: " + starterSize, AppStyle.ACCENT_GREEN);
    addInfoLabel(info, "Bench: " + benchSize, "#FFE0B2");
    addInfoLabel(info, "Injured: " + injuredSize, AppStyle.ACCENT_CORAL);

    // Training hakkı — sadece kendi takımında olyuyor
    if (isPlayerTeam) {
        Label trainingLbl = new Label(
                "🏋️ " + facade.getTrainingsLeft() + " trainings left");
        trainingLbl.setStyle("-fx-text-fill: " + AppStyle.ACCENT_ORANGE
                + "; -fx-font-size: 13px; -fx-font-weight: bold;");
        info.getChildren().add(trainingLbl);
    }

    TableView<Player> table = new TableView<>();
    table.setStyle("-fx-background-color: " + AppStyle.BG_PANEL + ";");
    table.setPrefHeight(470);

    TableColumn<Player, String> nameCol = new TableColumn<>("Name");
    nameCol.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getName()));
    nameCol.setPrefWidth(140);

    TableColumn<Player, String> posCol = new TableColumn<>("Position");
    posCol.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getPosition()));
    posCol.setPrefWidth(120);

    TableColumn<Player, Integer> skillCol = new TableColumn<>("Skill");
    skillCol.setCellValueFactory(d ->
            new SimpleIntegerProperty(
                    d.getValue().getSkillLevel()).asObject());
    skillCol.setPrefWidth(55);

    TableColumn<Player, String> roleCol = new TableColumn<>("Role");
    roleCol.setPrefWidth(100);
    roleCol.setCellValueFactory(d -> {
    Player player = d.getValue();
    if (player.isInjured()) return new SimpleStringProperty("🤕 Injured");

    List<Player> starters = facade.getStartingXI(selectedTeam);
    List<Player> bench = facade.getBench(selectedTeam);

    if (starters.contains(player))
        return new SimpleStringProperty("✅ Starting");
    else if (bench.contains(player))
        return new SimpleStringProperty("🔄 Bench");
    else
        return new SimpleStringProperty("❌ Excluded");
});

    TableColumn<Player, String> statusCol = new TableColumn<>("Status");
    statusCol.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().isInjured()
                    ? "INJURED (" + d.getValue()
                            .getInjuryGamesRemaining() + ")"
                    : "Available"));
    statusCol.setPrefWidth(110);

    table.getColumns().addAll(nameCol, posCol, skillCol, roleCol, statusCol);

    // Train butonu — sadece kendi takımında
    if (isPlayerTeam) {
        TableColumn<Player, Void> trainCol = new TableColumn<>("Train");
        trainCol.setPrefWidth(80);
        trainCol.setCellFactory(col ->
                new javafx.scene.control.TableCell<>() {
                    private final Button trainBtn = new Button("Train");
                    {
                        trainBtn.setStyle(
                                "-fx-background-color: " + AppStyle.ACCENT_GREEN
                                + "; -fx-text-fill: #0F172A; "
                                + "-fx-font-size: 11px; "
                                + "-fx-cursor: hand;");
                        trainBtn.setOnAction(e -> {
                            Player player = getTableView()
                                    .getItems().get(getIndex());
                            if (!facade.canTrain()) {
                                new Alert(Alert.AlertType.WARNING,
                                        "No trainings left this week!")
                                        .show();
                                return;
                            }
                            if (player.isInjured()) {
                                new Alert(Alert.AlertType.WARNING,
                                        "Injured players cannot train!")
                                        .show();
                                return;
                            }
                            int oldSkill = player.getSkillLevel();
                            player.train();
                            facade.useTraining();
                            int gain = player.getSkillLevel() - oldSkill;

                            // Refresh
                            view.setCenter(buildPlayerTable());

                            new Alert(Alert.AlertType.INFORMATION,
                                    player.getName() + " trained! +"
                                    + gain + " skill → "
                                    + player.getSkillLevel()).show();
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Player player = getTableView()
                                    .getItems().get(getIndex());
                            trainBtn.setDisable(player.isInjured()
                                    || !facade.canTrain());
                            setGraphic(trainBtn);
                        }
                    }
                });
        table.getColumns().add(trainCol);
    }
    // Tactic ComboBox — sadece football + kendi takımı
if (isPlayerTeam && isFootball) {
    javafx.scene.control.ComboBox<com.sportsmanager.football.Tactic> tacticBox =
            new javafx.scene.control.ComboBox<>();
    tacticBox.getItems().addAll(
            com.sportsmanager.football.Tactic.values());
    tacticBox.setValue(facade.getCurrentTactic());
    tacticBox.setStyle(
        "-fx-background-color: " + "white" + "; "
        + "-fx-border-color: " + AppStyle.ACCENT_BLUE + "; "
        + "-fx-border-width: 1px; "
        + "-fx-mark-color: white;");

    Label tacticLabel = new Label("Tactic:");
    tacticLabel.setStyle("-fx-text-fill: " + AppStyle.ACCENT_BLUE
            + "; -fx-font-size: 13px; -fx-font-weight: bold;");

    tacticBox.setOnAction(e -> {
        facade.setTactic(tacticBox.getValue());
        view.setCenter(buildPlayerTable());
    });

    HBox tacticRow = new HBox(10, tacticLabel, tacticBox);
    tacticRow.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
    container.getChildren().add(tacticRow);
}
if (isPlayerTeam && isBasketball) {
    javafx.scene.control.ComboBox<
            com.sportsmanager.basketball.BasketballTactic> tacticBox =
            new javafx.scene.control.ComboBox<>();

    tacticBox.getItems().addAll(
            com.sportsmanager.basketball.BasketballTactic.values());

    tacticBox.setValue(
            facade.getCurrentBasketballTactic());

    tacticBox.setStyle(
            "-fx-background-color: " + AppStyle.BG_PANEL + "; "
            + "-fx-border-color: " + AppStyle.ACCENT_BLUE + "; "
            + "-fx-border-width: 1px;");

    tacticBox.setButtonCell(
            new javafx.scene.control.ListCell<>() {
        @Override
        protected void updateItem(
                com.sportsmanager.basketball.BasketballTactic item,
                boolean empty) {
            super.updateItem(item, empty);
            setText(empty || item == null
                    ? null
                    : item.toString());
            setStyle("-fx-text-fill: white;");
        }
    });

    Label tacticLabel = new Label("Play Style:");
    tacticLabel.setStyle(
            "-fx-text-fill: " + AppStyle.ACCENT_BLUE
                    + "; -fx-font-size: 13px;"
                    + " -fx-font-weight: bold;");

    tacticBox.setOnAction(e -> {
        facade.setBasketballTactic(
                tacticBox.getValue());
        view.setCenter(buildPlayerTable());
    });

    HBox tacticRow = new HBox(10, tacticLabel, tacticBox);
    tacticRow.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

    container.getChildren().add(tacticRow);
}



    table.getItems().addAll(selectedTeam.getPlayers());
    container.getChildren().addAll(info, table);
    return container;
}

    private void addInfoLabel(HBox box, String text, String color) {
        Label lbl = new Label(text);
        lbl.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 12px;");
        box.getChildren().add(lbl);
    }

    public BorderPane getView() {
        return view;
    }
}