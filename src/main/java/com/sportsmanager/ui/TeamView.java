package com.sportsmanager.ui;

import com.sportsmanager.core.model.AbstractPlayer;
import com.sportsmanager.core.model.AbstractTeam;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class TeamView {
    private final AbstractTeam team;
    private VBox root;

    public TeamView(AbstractTeam team) {
        this.team = team;
        buildUI();
    }

    @SuppressWarnings("unchecked")
    private void buildUI() {
        root = new VBox(15);
        root.setPadding(new Insets(30));

        Text heading = new Text(team.getName());
        heading.getStyleClass().add("view-title");

        Text tacticsText = new Text("Tactic: " + (team.getCurrentTactic() != null ? team.getCurrentTactic().getName() : "None"));
        tacticsText.getStyleClass().add("subtitle-text");

        TableView<PlayerRow> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getStyleClass().add("player-table");

        TableColumn<PlayerRow, String> numCol = new TableColumn<>("#");
        numCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        numCol.setPrefWidth(40);

        TableColumn<PlayerRow, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(200);

        TableColumn<PlayerRow, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<PlayerRow, String> posCol = new TableColumn<>("Position");
        posCol.setCellValueFactory(new PropertyValueFactory<>("position"));

        TableColumn<PlayerRow, String> ratingCol = new TableColumn<>("Rating");
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));

        TableColumn<PlayerRow, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.getColumns().addAll(numCol, nameCol, ageCol, posCol, ratingCol, statusCol);

        var rows = FXCollections.<PlayerRow>observableArrayList();
        List<AbstractPlayer> players = team.getPlayers();
        for (int i = 0; i < players.size(); i++) {
            rows.add(new PlayerRow(i + 1, players.get(i)));
        }
        table.setItems(rows);

        root.getChildren().addAll(heading, tacticsText, table);
        VBox.setVgrow(table, Priority.ALWAYS);
    }

    public VBox getRoot() { return root; }

    public static class PlayerRow {
        private final int number;
        private final AbstractPlayer player;

        public PlayerRow(int number, AbstractPlayer player) {
            this.number = number;
            this.player = player;
        }

        public String getNumber() { return String.valueOf(number); }
        public String getName() { return player.getName(); }
        public int getAge() { return player.getAge(); }
        public String getPosition() { return player.getPosition().getAbbreviation(); }
        public String getRating() { return String.format("%.0f", player.getOverallRating()); }
        public String getStatus() { return player.isInjured() ? "Injured (" + player.getInjuredForGames() + ")" : "Fit"; }
    }
}
