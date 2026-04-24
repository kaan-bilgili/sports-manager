package com.sportsmanager.engine;

import java.io.*;

public class GameSaveManager {

    private static final String DEFAULT_SAVE_PATH = "savegame.dat";

    public void save(GameFacade facade, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filePath))) {
            oos.writeObject(facade);
            System.out.println("Game saved to: " + filePath);
        }
    }

    public void save(GameFacade facade) throws IOException {
        save(facade, DEFAULT_SAVE_PATH);
    }

    public GameFacade load(String filePath) throws IOException, ClassNotFoundException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("Save file not found: " + filePath);
        }
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filePath))) {
            GameFacade facade = (GameFacade) ois.readObject();
            System.out.println("Game loaded from: " + filePath);
            return facade;
        }
    }

    public GameFacade load() throws IOException, ClassNotFoundException {
        return load(DEFAULT_SAVE_PATH);
    }

    public boolean saveExists() {
        return new File(DEFAULT_SAVE_PATH).exists();
    }

    public boolean saveExists(String filePath) {
        return new File(filePath).exists();
    }
}