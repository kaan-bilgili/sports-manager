package com.sportsmanager.football;

import com.sportsmanager.domain.Match;
import com.sportsmanager.domain.Team;

import java.util.Random;

public class FootballMatch extends Match {

    private static final Random random = new Random();

    public FootballMatch(Team homeTeam, Team awayTeam) {
        super(homeTeam, awayTeam);
    }

    @Override
    public void simulate() {
        if (played) return;

        double homeSkill = homeTeam.getAverageSkill();
        double awaySkill = awayTeam.getAverageSkill();

        // Home advantage bonus
        homeSkill *= 1.1;

        double total = homeSkill + awaySkill;
        double homeWinChance = homeSkill / total;

        double roll = random.nextDouble();

        if (roll < homeWinChance * 0.6) {
            // Home win
            homeScore = 1 + random.nextInt(4);
            awayScore = random.nextInt(homeScore);
        } else if (roll < homeWinChance * 0.6 + 0.25) {
            // Draw
            homeScore = random.nextInt(3);
            awayScore = homeScore;
        } else {
            // Away win
            awayScore = 1 + random.nextInt(4);
            homeScore = random.nextInt(awayScore);
        }

        played = true;

        // Injury chance: %10 per match
        applyInjuries();
    }

    private void applyInjuries() {
        applyInjuriesToTeam(homeTeam);
        applyInjuriesToTeam(awayTeam);
    }

    private void applyInjuriesToTeam(Team team) {
        team.getPlayers().forEach(player -> {
            if (!player.isInjured() && random.nextInt(100) < 10) {
                player.setInjuryGamesRemaining(1 + random.nextInt(3));
            }
        });
    }
}