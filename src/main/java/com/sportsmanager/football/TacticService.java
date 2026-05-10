package com.sportsmanager.football;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.sportsmanager.domain.Player;
import com.sportsmanager.domain.Team;

public class TacticService {

    public static List<Player> getStartingXI(Team team, Tactic tactic) {
        if (!(team instanceof FootballTeam)) return new ArrayList<>();

        List<FootballPlayer> available = team.getAvailablePlayers()
                .stream()
                .filter(p -> p instanceof FootballPlayer)
                .map(p -> (FootballPlayer) p)
                .collect(Collectors.toList());

        List<Player> starters = new ArrayList<>();

        // GK
        addByPosition(available, starters,
                FootballPlayer.Position.GOALKEEPER,
                tactic.getGoalkeepers());

        // DEF
        addByPosition(available, starters,
                FootballPlayer.Position.DEFENDER,
                tactic.getDefenders());

        // MID
        addByPosition(available, starters,
                FootballPlayer.Position.MIDFIELDER,
                tactic.getMidfielders());

        // FWD
        addByPosition(available, starters,
                FootballPlayer.Position.FORWARD,
                tactic.getForwards());

        // 11'e tamamla
        if (starters.size() < 11) {
            available.stream()
                    .filter(p -> !starters.contains(p))
                    .sorted(Comparator.comparingInt(
                            Player::getSkillLevel).reversed())
                    .limit(11 - starters.size())
                    .forEach(starters::add);
        }

        return starters;
    }

    public static List<Player> getBench(Team team, Tactic tactic) {
        List<Player> starters = getStartingXI(team, tactic);
        return team.getAvailablePlayers().stream()
                .filter(p -> !starters.contains(p))
                .sorted(Comparator.comparingInt(
                        Player::getSkillLevel).reversed())
                .limit(7)
                .collect(Collectors.toList());
    }

    private static void addByPosition(List<FootballPlayer> available,
            List<Player> starters,
            FootballPlayer.Position position, int count) {
        available.stream()
                .filter(p -> p.getFootballPosition() == position
                        && !starters.contains(p))
                .sorted(Comparator.comparingInt(
                        Player::getSkillLevel).reversed())
                .limit(count)
                .forEach(starters::add);
    }
}