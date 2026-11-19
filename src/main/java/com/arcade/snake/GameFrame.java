package com.arcade.snake;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private final GameEngine gameEngine;
    private final GamePanel gamePanel;
    private final ScorePanel scorePanel;

    public GameFrame() {
        setTitle("Retro Snake Arcade");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        gamePanel = new GamePanel();
        scorePanel = new ScorePanel();
        gameEngine = new GameEngine(this, gamePanel, scorePanel);

        gamePanel.setGameEngine(gameEngine);
        scorePanel.setGameEngine(gameEngine);

        add(scorePanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);

        // Keyboard controls
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                int key = e.getKeyCode();
                if (key == java.awt.event.KeyEvent.VK_UP || key == java.awt.event.KeyEvent.VK_W) {
                    gameEngine.setDirection(Direction.UP);
                } else if (key == java.awt.event.KeyEvent.VK_DOWN || key == java.awt.event.KeyEvent.VK_S) {
                    gameEngine.setDirection(Direction.DOWN);
                } else if (key == java.awt.event.KeyEvent.VK_LEFT || key == java.awt.event.KeyEvent.VK_A) {
                    gameEngine.setDirection(Direction.LEFT);
                } else if (key == java.awt.event.KeyEvent.VK_RIGHT || key == java.awt.event.KeyEvent.VK_D) {
                    gameEngine.setDirection(Direction.RIGHT);
                } else if (key == java.awt.event.KeyEvent.VK_SPACE || key == java.awt.event.KeyEvent.VK_P) {
                    gameEngine.togglePause();
                } else if (key == java.awt.event.KeyEvent.VK_ENTER) {
                    if (gameEngine.getState() == GameEngine.GameState.MENU || gameEngine.getState() == GameEngine.GameState.GAMEOVER) {
                        gameEngine.startNewGame(gameEngine.getMode(), gameEngine.getDifficulty());
                    }
                }
            }
        });

        setFocusable(true);
        pack();
        setLocationRelativeTo(null);
    }
}
