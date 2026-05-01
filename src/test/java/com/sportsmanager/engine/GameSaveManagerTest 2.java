package com.sportsmanager.engine;

import com.sportsmanager.football.FootballSport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GameSaveManagerTest {

    private static final String TEST_SAVE_PATH = "test_savegame.dat";

    @AfterEach
    void cleanup() {
        new File(TEST_SAVE_PATH).delete();
    }

    @Test
    void testSaveAndLoadCycle() throws IOException, ClassNotFoundException {
        GameFacade facade = new GameFacade(new FootballSport());
        facade.initGame(4);

        GameSaveManager manager = new GameSaveManager();
        manager.save(facade, TEST_SAVE_PATH);

        assertTrue(manager.saveExists(TEST_SAVE_PATH));

        GameFacade loaded = manager.load(TEST_SAVE_PATH);
        assertNotNull(loaded);
        assertEquals("Football League", loaded.getLeague().getName());
        assertEquals(4, loaded.getLeague().getTeams().size());
    }

    @Test
    void testSaveFileExists() throws IOException {
        GameFacade facade = new GameFacade(new FootballSport());
        facade.initGame(4);

        GameSaveManager manager = new GameSaveManager();
        assertFalse(manager.saveExists(TEST_SAVE_PATH));

        manager.save(facade, TEST_SAVE_PATH);
        assertTrue(manager.saveExists(TEST_SAVE_PATH));
    }

    @Test
    void testLoadNonExistentFileThrowsException() {
        GameSaveManager manager = new GameSaveManager();
        assertThrows(Exception.class, () -> {
            manager.load("nonexistent.dat");
        });
    }
}