package com.arcade.snake;

import java.io.*;
import java.nio.file.*;

public class ScoreManager {
    private static final String FILE_NAME = ".snake_highscore";
    private final Path filePath;

    public ScoreManager() {
        String userHome = System.getProperty("user.home");
        this.filePath = Paths.get(userHome, FILE_NAME);
    }

    public int getHighScore() {
        if (!Files.exists(filePath)) {
            return 0;
        }
        try {
            String content = Files.readString(filePath).trim();
            return Integer.parseInt(content);
        } catch (IOException | NumberFormatException e) {
            return 0;
        }
    }

    public void saveHighScore(int score) {
        int currentHigh = getHighScore();
        if (score > currentHigh) {
            try {
                Files.writeString(filePath, String.valueOf(score));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
