package com.sportsmanager.basketball;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.sportsmanager.domain.Player;
import com.sportsmanager.domain.Team;

public class BasketballTacticService {

    public static List<Player> getStarters(
            Team team,
            BasketballTactic tactic) {

        if (!(team instanceof BasketballTeam))
            return new ArrayList<>();

        List<Player> available = team.getAvailablePlayers()
                .stream()
                .sorted(Comparator.comparingInt(
                        Player::getSkillLevel).reversed())
                .collect(Collectors.toList());

        // starter 5
        return available.stream()
                .limit(5)
                .collect(Collectors.toList());
    }

    public static List<Player> getBench(
            Team team,
            BasketballTactic tactic) {

        List<Player> starters = getStarters(team, tactic);

        return team.getAvailablePlayers()
                .stream()
                .filter(p -> !starters.contains(p))
                .sorted(Comparator.comparingInt(
                        Player::getSkillLevel).reversed())
                .limit(7)
                .collect(Collectors.toList());
    }
}