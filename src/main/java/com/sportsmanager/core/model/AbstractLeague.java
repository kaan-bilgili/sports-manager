package com.sportsmanager.core.model;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractLeague {
    private String name;
    private List<AbstractTeam> teams;
    private List<Fixture> fixtures;
    private Map<UUID, LeagueStanding> standings;
    protected int currentWeek;
    private int season;

    public AbstractLeague(String name, List<? extends AbstractTeam> teams) {
        this.name = name;
        this.teams = new ArrayList<>(teams);
        this.fixtures = new ArrayList<>();
        this.standings = new LinkedHashMap<>();
        this.currentWeek = 1;
        this.season = 2024;

        for (AbstractTeam team : this.teams) {
            standings.put(team.getId(), new LeagueStanding(team));
        }
    }

    public abstract int getPointsForWin();
    protected abstract AbstractMatch createMatchInstance(AbstractTeam home, AbstractTeam away, int week);

    public void generateFixtures() {
        fixtures.clear();
        List<AbstractTeam> teamList = new ArrayList<>(teams);
        boolean hasBye = teamList.size() % 2 != 0;
        if (hasBye) teamList.add(null);

        int n = teamList.size();
        int rounds = n - 1;
        int matchesPerRound = n / 2;

        for (int round = 0; round < rounds; round++) {
            Fixture fixture = new Fixture(round + 1);
            for (int match = 0; match < matchesPerRound; match++) {
                int home = (round + match) % (n - 1);
                int away = (n - 1 - match + round) % (n - 1);
                if (match == 0) away = n - 1;

                AbstractTeam homeTeam = teamList.get(home);
                AbstractTeam awayTeam = teamList.get(away);

                if (homeTeam != null && awayTeam != null) {
                    fixture.addMatch(createMatchInstance(homeTeam, awayTeam, round + 1));
                }
            }
            fixtures.add(fixture);
        }

        for (int round = 0; round < rounds; round++) {
            Fixture firstHalf = fixtures.get(round);
            Fixture fixture = new Fixture(rounds + round + 1);
            for (AbstractMatch m : firstHalf.getMatches()) {
                fixture.addMatch(createMatchInstance(m.getAwayTeam(), m.getHomeTeam(), rounds + round + 1));
            }
            fixtures.add(fixture);
        }
    }

    public void advanceWeek() {
        if (isFinished()) return;
        Fixture fixture = fixtures.get(currentWeek - 1);
        for (AbstractMatch match : fixture.getMatches()) {
            MatchResult result = match.simulate();
            standings.get(match.getHomeTeam().getId()).update(result, true);
            standings.get(match.getAwayTeam().getId()).update(result, false);
        }
        currentWeek++;
    }

    public void forceAdvanceWeek() {
        currentWeek++;
    }

    public List<LeagueStanding> getStandings() {
        return standings.values().stream()
                .sorted(Comparator
                        .comparingInt(LeagueStanding::getPoints).reversed()
                        .thenComparingInt(LeagueStanding::getGoalDifference).reversed()
                        .thenComparingInt(LeagueStanding::getGoalsFor).reversed()
                        .thenComparing(s -> s.getTeam().getName()))
                .collect(Collectors.toList());
    }

    public boolean isFinished() { return currentWeek > fixtures.size(); }

    public Optional<AbstractTeam> getChampion() {
        return getStandings().stream()
                .findFirst()
                .map(LeagueStanding::getTeam);
    }

    public String getName() { return name; }
    public List<AbstractTeam> getTeams() { return teams; }
    public List<Fixture> getFixtures() { return fixtures; }
    public Map<UUID, LeagueStanding> getStandingsMap() { return standings; }
    public int getCurrentWeek() { return currentWeek; }
    public int getSeason() { return season; }
    public void setSeason(int season) { this.season = season; }
}
