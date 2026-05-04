package com.sportsmanager.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fixture implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<Integer, List<Match>> rounds;
    private int totalRounds;

    public Fixture() {
        this.rounds = new HashMap<>();
        this.totalRounds = 0;
    }

    public void addMatch(int round, Match match) {
        rounds.computeIfAbsent(round, k -> new ArrayList<>()).add(match);
        if (round > totalRounds) totalRounds = round;
    }

    public List<Match> getMatchesForRound(int round) {
        return rounds.getOrDefault(round, new ArrayList<>());
    }

    public Map<Integer, List<Match>> getAllRounds() {
        return rounds;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public List<Match> getAllMatches() {
        List<Match> all = new ArrayList<>();
        for (List<Match> roundMatches : rounds.values()) {
            all.addAll(roundMatches);
        }
        return all;
    }

    public int getTotalMatchCount() {
        return getAllMatches().size();
    }
}