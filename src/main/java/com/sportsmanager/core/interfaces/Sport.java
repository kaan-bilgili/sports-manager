package com.sportsmanager.core.interfaces;

import com.sportsmanager.core.model.*;
import java.util.List;

public interface Sport {
    String getName();
    int getTeamSize();
    int getSubstituteCount();
    List<? extends Position> getPositions();
    AbstractLeague createLeague(String name, List<? extends AbstractTeam> teams);
    AbstractTeam createTeam(String name);
    AbstractPlayer createPlayer(String name, int age, Position position);
    AbstractMatch createMatch(AbstractTeam home, AbstractTeam away);
}
