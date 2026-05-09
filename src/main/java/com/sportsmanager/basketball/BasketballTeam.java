package com.sportsmanager.basketball;

import com.sportsmanager.domain.Player;
import com.sportsmanager.domain.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BasketballTeam extends Team {

    private static final int STARTERS = 5;
    private static final int BENCH_SIZE = 7;
    private static final int MATCHDAY_SQUAD = 12;
    private static final int TOTAL_SQUAD = 15;

    public BasketballTeam(String name) {
        super(name);
    }

    public int getSquadSize() { return STARTERS; }
    public int getSubstituteCount() { return BENCH_SIZE; }
    public int getMatchdaySquadSize() { return MATCHDAY_SQUAD; }
    public int getTotalSquadSize() { return TOTAL_SQUAD; }

    public List<BasketballPlayer> getBasketballPlayers() {
        return players.stream()
                .filter(p -> p instanceof BasketballPlayer)
                .map(p -> (BasketballPlayer) p)
                .collect(Collectors.toList());
    }

    public List<BasketballPlayer> getPlayersByPosition(
            BasketballPlayer.Position position) {
        return getBasketballPlayers().stream()
                .filter(p -> p.getBasketballPosition() == position)
                .collect(Collectors.toList());
    }

    // Matchday squad — first 12 available
    public List<Player> getMatchdaySquad() {
        return getAvailablePlayers().stream()
                .limit(MATCHDAY_SQUAD)
                .collect(Collectors.toList());
    }

    // Starters — first 5 available
    public List<Player> getStarters() {
    List<BasketballPlayer> all = getBasketballPlayers().stream()
            .filter(p -> !p.isInjured())
            .collect(Collectors.toList());

    List<Player> starters = new ArrayList<>();

    // Her pozisyondan en iyi 1 oyuncu
    BasketballPlayer.Position[] positions = {
        BasketballPlayer.Position.POINT_GUARD,
        BasketballPlayer.Position.SHOOTING_GUARD,
        BasketballPlayer.Position.SMALL_FORWARD,
        BasketballPlayer.Position.POWER_FORWARD,
        BasketballPlayer.Position.CENTER
    };

    for (BasketballPlayer.Position pos : positions) {
        all.stream()
                .filter(p -> p.getBasketballPosition() == pos)
                .max(java.util.Comparator.comparingInt(Player::getSkillLevel))
                .ifPresent(starters::add);
    }

    // 5'e tamamlanamadıysa kalan en iyi oyuncuları ekle
    if (starters.size() < STARTERS) {
        all.stream()
                .filter(p -> !starters.contains(p))
                .sorted((a, b) -> b.getSkillLevel() - a.getSkillLevel())
                .limit(STARTERS - starters.size())
                .forEach(starters::add);
    }

    return starters;
}

public List<Player> getBench() {
    List<Player> starters = getStarters();
    return getAvailablePlayers().stream()
            .filter(p -> !starters.contains(p))
            .sorted((a, b) -> b.getSkillLevel() - a.getSkillLevel())
            .limit(BENCH_SIZE)
            .collect(Collectors.toList());
}
}