package com.sportsmanager.basketball;

import java.util.Random;

import com.sportsmanager.domain.Match;
import com.sportsmanager.domain.Team;

public class BasketballMatch extends Match {

    private static final Random random = new Random();

    public BasketballMatch(Team homeTeam, Team awayTeam) {
        super(homeTeam, awayTeam);
    }

    @Override
    public void simulate() {
        if (played)
            return;

        double homeSkill = homeTeam.getAverageSkill();
        double awaySkill = awayTeam.getAverageSkill();

        // Home advantage bonus
        homeSkill *= 1.1;
        double total = homeSkill + awaySkill;
        double homeWinChance = homeSkill / total;

        int homeBase = 70 + random.nextInt(40);
        int awayBase = 70 + random.nextInt(40);

        double roll = random.nextDouble();
        if (roll < homeWinChance) {
            homeScore = homeBase + 5 + random.nextInt(10);
            awayScore = awayBase - random.nextInt(10);
        } else {
            awayScore = awayBase + 5 + random.nextInt(10);
            homeScore = homeBase - random.nextInt(10);
        }

        homeScore = Math.max(homeScore, 60);
        awayScore = Math.max(awayScore, 60);

        // Beraberlik yok
        if (homeScore == awayScore) {
            homeScore += 2;
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
