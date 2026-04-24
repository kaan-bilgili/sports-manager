package com.sportsmanager.basketball;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.sportsmanager.domain.Match;
import com.sportsmanager.domain.Player;
import com.sportsmanager.domain.Team;

class BasketballSportTest {

    @Test
    // T30: BasketballSport spor adini dogru dondurur
    void testSportName() {
        BasketballSport sport = new BasketballSport();
        assertEquals("Basketball", sport.getSportName());
    }

    @Test
    // T31: BasketballSport kadro boyutu 5 dir
    void testSquadSize() {
        BasketballSport sport = new BasketballSport();
        assertEquals(5, sport.getSquadSize());
    }

    @Test
    // T32: BasketballSport yedek sayisi 7 dir
    void testSubstituteCount() {
        BasketballSport sport = new BasketballSport();
        assertEquals(7, sport.getSubstituteCount());
    }

    @Test
    // T33: BasketballSport createTeam BasketballTeam dondurur
    void testCreateTeamReturnsBasketballTeam() {
        BasketballSport sport = new BasketballSport();
        Team team = sport.createTeam("Lakers");

        assertInstanceOf(BasketballTeam.class, team);
        assertEquals("Lakers", team.getName());
    }

    @Test
    // T34: BasketballSport createPlayer BasketballPlayer dondurur
    void testCreatePlayerReturnsBasketballPlayer() {
        BasketballSport sport = new BasketballSport();
        Player player = sport.createPlayer("LeBron", 30, 95);

        assertInstanceOf(BasketballPlayer.class, player);
        assertEquals("LeBron", player.getName());
    }

    @Test
    // T35: BasketballSport createMatch BasketballMatch dondurur
    void testCreateMatchReturnsBasketballMatch() {
        BasketballSport sport = new BasketballSport();
        Team home = sport.createTeam("Lakers");
        Team away = sport.createTeam("Celtics");
        Match match = sport.createMatch(home, away);

        assertInstanceOf(BasketballMatch.class, match);
    }
}
