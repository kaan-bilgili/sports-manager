package com.sportsmanager.football;

import com.sportsmanager.domain.Player;
import com.sportsmanager.domain.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FootballTeam extends Team {

    private static final int STARTING_XI = 11;
    private static final int BENCH_SIZE = 7;
    private static final int MATCHDAY_SQUAD = 18;
    private static final int TOTAL_SQUAD = 22;

    public FootballTeam(String name) {
        super(name);
    }

    public int getSquadSize() { return STARTING_XI; }
    public int getSubstituteCount() { return BENCH_SIZE; }
    public int getMatchdaySquadSize() { return MATCHDAY_SQUAD; }
    public int getTotalSquadSize() { return TOTAL_SQUAD; }

    public List<FootballPlayer> getFootballPlayers() {
        return players.stream()
                .filter(p -> p instanceof FootballPlayer)
                .map(p -> (FootballPlayer) p)
                .collect(Collectors.toList());
    }

    public List<FootballPlayer> getPlayersByPosition(
            FootballPlayer.Position position) {
        return getFootballPlayers().stream()
                .filter(p -> p.getFootballPosition() == position)
                .collect(Collectors.toList());
    }

    // Matchday squad — first 18 available players
    public List<Player> getMatchdaySquad() {
        List<Player> available = getAvailablePlayers();
        return available.stream()
                .limit(MATCHDAY_SQUAD)
                .collect(Collectors.toList());
    }

    // Starting XI — first 11 available
   public List<Player> getStartingXI() {
    List<FootballPlayer> all = getFootballPlayers().stream()
            .filter(p -> !p.isInjured())
            .collect(Collectors.toList());

    List<Player> starters = new ArrayList<>();

    // 1 GK
    all.stream()
            .filter(p -> p.getFootballPosition() ==
                    FootballPlayer.Position.GOALKEEPER)
            .max(java.util.Comparator.comparingInt(Player::getSkillLevel))
            .ifPresent(starters::add);

    // 4 DEF
    all.stream()
            .filter(p -> p.getFootballPosition() ==
                    FootballPlayer.Position.DEFENDER)
            .sorted((a, b) -> b.getSkillLevel() - a.getSkillLevel())
            .limit(4)
            .forEach(starters::add);

    // 4 MID
    all.stream()
            .filter(p -> p.getFootballPosition() ==
                    FootballPlayer.Position.MIDFIELDER)
            .sorted((a, b) -> b.getSkillLevel() - a.getSkillLevel())
            .limit(4)
            .forEach(starters::add);

    // 2 FWD
    all.stream()
            .filter(p -> p.getFootballPosition() ==
                    FootballPlayer.Position.FORWARD)
            .sorted((a, b) -> b.getSkillLevel() - a.getSkillLevel())
            .limit(2)
            .forEach(starters::add);

    // 11'e tamamlanamadıysa kalan en iyileri ekle
    if (starters.size() < STARTING_XI) {
        all.stream()
                .filter(p -> !starters.contains(p))
                .sorted((a, b) -> b.getSkillLevel() - a.getSkillLevel())
                .limit(STARTING_XI - starters.size())
                .forEach(starters::add);
    }

    return starters;
}

public List<Player> getBench() {
    List<Player> starters = getStartingXI();
    return getAvailablePlayers().stream()
            .filter(p -> !starters.contains(p))
            .sorted((a, b) -> b.getSkillLevel() - a.getSkillLevel())
            .limit(BENCH_SIZE)
            .collect(Collectors.toList());
}
}