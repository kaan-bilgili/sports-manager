package com.sportsmanager.ui;

import com.sportsmanager.core.model.AbstractLeague;
import com.sportsmanager.core.model.AbstractTeam;
import com.sportsmanager.core.model.LeagueStanding;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class LeagueTableView {
    private final AbstractLeague league;
    private final AbstractTeam managedTeam;
    private VBox root;

    public LeagueTableView(AbstractLeague league, AbstractTeam managedTeam) {
        this.league = league;
        this.managedTeam = managedTeam;
        buildUI();
    }

    @SuppressWarnings("unchecked")
    private void buildUI() {
        root = new VBox(15);
        root.setPadding(new Insets(30));

        Text heading = new Text("League Table");
        heading.getStyleClass().add("view-title");

        TableView<StandingRow> table = new TableView<>();
        table.getStyleClass().add("league-table");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<StandingRow, String> posCol = new TableColumn<>("#");
        posCol.setCellValueFactory(new PropertyValueFactory<>("pos"));
        posCol.setPrefWidth(40);

        TableColumn<StandingRow, String> teamCol = new TableColumn<>("Team");
        teamCol.setCellValueFactory(new PropertyValueFactory<>("team"));
        teamCol.setPrefWidth(180);

        TableColumn<StandingRow, Integer> pCol = new TableColumn<>("P");
        pCol.setCellValueFactory(new PropertyValueFactory<>("played"));

        TableColumn<StandingRow, Integer> wCol = new TableColumn<>("W");
        wCol.setCellValueFactory(new PropertyValueFactory<>("won"));

        TableColumn<StandingRow, Integer> dCol = new TableColumn<>("D");
        dCol.setCellValueFactory(new PropertyValueFactory<>("drawn"));

        TableColumn<StandingRow, Integer> lCol = new TableColumn<>("L");
        lCol.setCellValueFactory(new PropertyValueFactory<>("lost"));

        TableColumn<StandingRow, Integer> gfCol = new TableColumn<>("GF");
        gfCol.setCellValueFactory(new PropertyValueFactory<>("goalsFor"));

        TableColumn<StandingRow, Integer> gaCol = new TableColumn<>("GA");
        gaCol.setCellValueFactory(new PropertyValueFactory<>("goalsAgainst"));

        TableColumn<StandingRow, Integer> gdCol = new TableColumn<>("GD");
        gdCol.setCellValueFactory(new PropertyValueFactory<>("goalDiff"));

        TableColumn<StandingRow, Integer> ptsCol = new TableColumn<>("Pts");
        ptsCol.setCellValueFactory(new PropertyValueFactory<>("points"));

        table.getColumns().addAll(posCol, teamCol, pCol, wCol, dCol, lCol, gfCol, gaCol, gdCol, ptsCol);

        List<LeagueStanding> standings = league.getStandings();
        var rows = FXCollections.<StandingRow>observableArrayList();
        for (int i = 0; i < standings.size(); i++) {
            LeagueStanding s = standings.get(i);
            rows.add(new StandingRow(i + 1, s));
        }
        table.setItems(rows);

        table.setRowFactory(tv -> new TableRow<StandingRow>() {
            @Override
            protected void updateItem(StandingRow item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && item.getTeamName().equals(managedTeam.getName())) {
                    getStyleClass().add("managed-team-row");
                } else {
                    getStyleClass().remove("managed-team-row");
                }
            }
        });

        root.getChildren().addAll(heading, table);
    }

    public VBox getRoot() { return root; }

    public static class StandingRow {
        private final int posNum;
        private final LeagueStanding standing;

        public StandingRow(int pos, LeagueStanding standing) {
            this.posNum = pos;
            this.standing = standing;
        }

        public String getPos() { return String.valueOf(posNum); }
        public String getTeam() { return standing.getTeam().getName(); }
        public String getTeamName() { return standing.getTeam().getName(); }
        public int getPlayed() { return standing.getPlayed(); }
        public int getWon() { return standing.getWon(); }
        public int getDrawn() { return standing.getDrawn(); }
        public int getLost() { return standing.getLost(); }
        public int getGoalsFor() { return standing.getGoalsFor(); }
        public int getGoalsAgainst() { return standing.getGoalsAgainst(); }
        public int getGoalDiff() { return standing.getGoalDifference(); }
        public int getPoints() { return standing.getPoints(); }
    }
}
