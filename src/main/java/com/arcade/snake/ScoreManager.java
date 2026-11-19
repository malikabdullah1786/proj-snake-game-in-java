package com.arcade.snake;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreManager
{
    private static final String HIGH_SCORE_FILE = ".snake_highscore";
    private int highScore;
    private final List<Integer> topScores;

    public ScoreManager() {
        this.topScores = new ArrayList<>();
        this.highScore = 0;
        loadHighScore();
    }

    public int getHighScore() {
        return highScore;
    }

    public List<Integer> getTopScores() {
        return Collections.unmodifiableList(topScores);
    }

    public boolean checkNewHighScore(int score) {
        if (score > highScore) {
            highScore = score;
        }
        if (!topScores.contains(score)) {
            topScores.add(score);
            topScores.sort(Collections.reverseOrder());
            if (topScores.size() > 5) {
                topScores.remove(topScores.size() - 1);
            }
        }
        saveHighScore();
        return score >= highScore;
    }

    private void loadHighScore() {
        Path path = getHighScoreFilePath();
        if (Files.exists(path)) {
            try {
                List<String> lines = Files.readAllLines(path);
                topScores.clear();
                for (String line : lines) {
                    try {
                        topScores.add(Integer.parseInt(line.trim()));
                    } catch (NumberFormatException ignored) {}
                }
                topScores.sort(Collections.reverseOrder());
                if (!topScores.isEmpty()) {
                    highScore = topScores.get(0);
                }
            } catch (IOException e) {
                System.err.println("Failed to load high score: " + e.getMessage());
            }
        }
        if (topScores.isEmpty()) {
            topScores.add(0);
            highScore = 0;
        }
    }

    private void saveHighScore() {
        Path path = getHighScoreFilePath();
        try {
            List<String> lines = new ArrayList<>();
            for (Integer score : topScores) {
                lines.add(score.toString());
            }
            Files.write(path, lines);
        } catch (IOException e) {
            System.err.println("Failed to save high score: " + e.getMessage());
        }
    }

    private Path getHighScoreFilePath() {
        String userHome = System.getProperty("user.home");
        return Paths.get(userHome, HIGH_SCORE_FILE);
    }
}
