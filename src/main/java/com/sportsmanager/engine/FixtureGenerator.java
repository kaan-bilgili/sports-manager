package com.sportsmanager.engine;

import java.util.ArrayList;
import java.util.List;

import com.sportsmanager.domain.Fixture;
import com.sportsmanager.domain.Match;
import com.sportsmanager.domain.Team;
import com.sportsmanager.sport.Sport;

public class FixtureGenerator {

    private final Sport sport;

    public FixtureGenerator(Sport sport) {
        this.sport = sport;
    }

    public Fixture generate(List<Team> teams) {
        Fixture fixture = new Fixture();
        List<Team> teamList = new ArrayList<>(teams);

        int n = teamList.size();
        if (n % 2 != 0) {
            teamList.add(null);
            n++;
        }

        int totalRounds = (n - 1) * 2;
        int halfRounds = n - 1;

        for (int round = 0; round < totalRounds; round++) {
            int actualRound = round % halfRounds;
            for (int i = 0; i < n / 2; i++) {
                int home = (actualRound + i) % (n - 1);
                int away = (n - 1 - i + actualRound) % (n - 1);

                if (i == 0) away = n - 1;

                Team homeTeam = teamList.get(home);
                Team awayTeam = teamList.get(away);

                if (homeTeam == null || awayTeam == null) continue;

                if (round >= halfRounds) {
                    Match match = sport.createMatch(awayTeam, homeTeam);
                    fixture.addMatch(round + 1, match);
                } else {
                    Match match = sport.createMatch(homeTeam, awayTeam);
                    fixture.addMatch(round + 1, match);
                }
            }
        }

        return fixture;
    }
}