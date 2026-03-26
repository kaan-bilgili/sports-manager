package com.sportsmanager.football;

import com.sportsmanager.core.model.AbstractMatch;
import com.sportsmanager.core.model.AbstractPlayer;
import com.sportsmanager.core.model.AbstractTeam;
import com.sportsmanager.core.model.MatchResult;

import java.util.List;
import java.util.Random;

public class FootballMatch extends AbstractMatch {
    private static final Random random = new Random();
    private static final double INJURY_CHANCE = 0.02;

    public FootballMatch(AbstractTeam homeTeam, AbstractTeam awayTeam, int weekNumber) {
        super(homeTeam, awayTeam, weekNumber);
    }

    @Override
    protected MatchResult doSimulate() {
        // Heal previously injured players by one game BEFORE the match
        for (AbstractPlayer p : getHomeTeam().getPlayers()) p.healOneGame();
        for (AbstractPlayer p : getAwayTeam().getPlayers()) p.healOneGame();

        // Get available players (after healing)
        List<AbstractPlayer> homePlayers = getHomeTeam().getAvailablePlayers();
        List<AbstractPlayer> awayPlayers = getAwayTeam().getAvailablePlayers();

        double homeAttack = calculateAttackRating(homePlayers);
        double homeDefense = calculateDefenseRating(homePlayers);
        double awayAttack = calculateAttackRating(awayPlayers);
        double awayDefense = calculateDefenseRating(awayPlayers);

        int homeGoals = simulateGoals(homeAttack, awayDefense, true);
        int awayGoals = simulateGoals(awayAttack, homeDefense, false);

        // Process new injuries AFTER the match
        processInjuries(homePlayers);
        processInjuries(awayPlayers);

        return new MatchResult(homeGoals, awayGoals);
    }

    private double calculateAttackRating(List<AbstractPlayer> players) {
        if (players.isEmpty()) return 50;
        return players.stream()
                .filter(p -> p instanceof FootballPlayer)
                .map(p -> (FootballPlayer) p)
                .filter(p -> p.getPosition() == FootballPosition.FORWARD || p.getPosition() == FootballPosition.MIDFIELDER)
                .mapToDouble(FootballPlayer::getAttackRating)
                .average()
                .orElse(50);
    }

    private double calculateDefenseRating(List<AbstractPlayer> players) {
        if (players.isEmpty()) return 50;
        return players.stream()
                .filter(p -> p instanceof FootballPlayer)
                .map(p -> (FootballPlayer) p)
                .filter(p -> p.getPosition() == FootballPosition.DEFENDER || p.getPosition() == FootballPosition.GOALKEEPER)
                .mapToDouble(FootballPlayer::getDefenseRating)
                .average()
                .orElse(50);
    }

    private int simulateGoals(double attackRating, double defenseRating, boolean isHome) {
        double baseChance = 0.08;
        if (isHome) baseChance += 0.01;
        double goalChance = baseChance * (1 + (attackRating - defenseRating) / 100.0);
        goalChance = Math.max(0.02, Math.min(0.25, goalChance));

        int goals = 0;
        for (int minute = 0; minute < 90; minute++) {
            if (random.nextDouble() < goalChance) {
                goals++;
            }
        }
        return Math.min(goals, 8);
    }

    private void processInjuries(List<AbstractPlayer> players) {
        for (AbstractPlayer player : players) {
            if (!player.isInjured() && random.nextDouble() < INJURY_CHANCE) {
                player.setInjuredForGames(1 + random.nextInt(3));
            }
        }
    }
}
