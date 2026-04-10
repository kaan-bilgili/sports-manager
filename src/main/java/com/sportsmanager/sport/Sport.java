package com.sportsmanager.sport;

import com.sportsmanager.domain.Match;
import com.sportsmanager.domain.Player;
import com.sportsmanager.domain.Team;

public interface Sport {
    String getSportName();
    Team createTeam(String name);
    Player createPlayer(String name, int age, int skillLevel);
    Match createMatch(Team homeTeam, Team awayTeam);
    int getSquadSize();
    int getSubstituteCount();
}