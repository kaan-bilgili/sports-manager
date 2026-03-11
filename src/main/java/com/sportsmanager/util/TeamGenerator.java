package com.sportsmanager.util;

import com.sportsmanager.core.interfaces.Sport;
import com.sportsmanager.core.model.AbstractTeam;
import com.sportsmanager.football.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TeamGenerator {
    private static final Random random = new Random();

    private static final List<String> TEAM_NAMES = Arrays.asList(
        "Galatasaray", "Fenerbahce", "Besiktas", "Trabzonspor", "Basaksehir",
        "Adana Demirspor", "Sivasspor", "Konyaspor", "Antalyaspor", "Gaziantep FK",
        "Kasimpasa", "Alanyaspor", "Kayserispor", "Rizespor", "Hatayspor",
        "Giresunspor", "Altay", "Goztepe", "Umraniyespor", "Ankaragugu",
        "Bursaspor", "Eskisehirspor", "MKE Ankaragugu", "Boluspor", "Samsunspor"
    );

    public static List<FootballTeam> generateTeams(int count, Sport sport) {
        List<FootballTeam> teams = new ArrayList<>();
        List<String> availableNames = new ArrayList<>(TEAM_NAMES);

        for (int i = 0; i < count && !availableNames.isEmpty(); i++) {
            int nameIdx = random.nextInt(availableNames.size());
            String teamName = availableNames.remove(nameIdx);
            FootballTeam team = new FootballTeam(teamName);

            team.addPlayer(createPlayer(FootballPosition.GOALKEEPER));
            for (int j = 0; j < 4; j++) team.addPlayer(createPlayer(FootballPosition.DEFENDER));
            for (int j = 0; j < 4; j++) team.addPlayer(createPlayer(FootballPosition.MIDFIELDER));
            for (int j = 0; j < 2; j++) team.addPlayer(createPlayer(FootballPosition.FORWARD));
            FootballPosition[] subPositions = {FootballPosition.GOALKEEPER, FootballPosition.DEFENDER, FootballPosition.MIDFIELDER, FootballPosition.MIDFIELDER, FootballPosition.FORWARD};
            for (FootballPosition pos : subPositions) {
                team.addPlayer(createPlayer(pos));
            }

            FootballCoach coach = new FootballCoach(
                NameGenerator.generateCoachName(),
                40 + random.nextInt(20),
                "Head Coach",
                5 + random.nextInt(20)
            );
            team.getCoaches().add(coach);

            FootballTactic[] tactics = FootballTactic.values();
            team.setTactic(tactics[random.nextInt(tactics.length)]);

            teams.add(team);
        }
        return teams;
    }

    private static FootballPlayer createPlayer(FootballPosition position) {
        return new FootballPlayer(
            NameGenerator.generateName(),
            17 + random.nextInt(19),
            position
        );
    }
}
