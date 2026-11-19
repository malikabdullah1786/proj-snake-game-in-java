package com.arcade.snake;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    private JLabel scoreLabel;
    private JLabel highScoreLabel;
    private JLabel levelLabel;
    private GameEngine gameEngine;

    public ScorePanel() {
        setBackground(new Color(30, 30, 45));
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));

        scoreLabel = new JLabel("Score: 0");
        highScoreLabel = new JLabel("High Score: 0");
        levelLabel = new JLabel("Level: 1");

        Font font = new Font("Arial", Font.BOLD, 14);
        scoreLabel.setFont(font);
        scoreLabel.setForeground(Color.WHITE);
        highScoreLabel.setFont(font);
        highScoreLabel.setForeground(Color.WHITE);
        levelLabel.setFont(font);
        levelLabel.setForeground(Color.WHITE);

        add(scoreLabel);
        add(highScoreLabel);
        add(levelLabel);
    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        if (gameEngine != null) {
            updateScore(gameEngine.getScore(), gameEngine.getScoreManager().getHighScore(), gameEngine.getLevel());
        }
    }

    public void updateScore(int score, int highScore, int level) {
        scoreLabel.setText("Score: " + score);
        highScoreLabel.setText("High Score: " + highScore);
        levelLabel.setText("Level: " + level);
    }
}
