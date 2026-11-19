package com.arcade.snake;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;

public class GameEngine {
    public enum GameMode {
        CLASSIC, ARCADE, OBSTACLES, CAMPAIGN
    }

    public enum Difficulty {
        EASY(130), MEDIUM(90), HARD(50);
        private final int tickDelayMs;
        Difficulty(int delay) { this.tickDelayMs = delay; }
        public int getTickDelayMs() { return tickDelayMs; }
    }

    public enum GameState {
        MENU, PLAYING, PAUSED, GAMEOVER
    }

    private GameState state;
    private GameMode mode;
    private Difficulty difficulty;
    private Board board;
    private Snake snake;
    private Food food;
    private ScoreManager scoreManager;
    private Timer gameTimer;

    private int score;
    private int applesEaten;
    private int currentSpeedMs;
    private boolean soundEnabled;
    private boolean gridLinesEnabled;
    private int level;

    private final GamePanel gamePanel;
    private final ScorePanel scorePanel;
    private final GameFrame gameFrame;

    public GameEngine(GameFrame gameFrame, GamePanel gamePanel, ScorePanel scorePanel) {
        this.gameFrame = gameFrame;
        this.gamePanel = gamePanel;
        this.scorePanel = scorePanel;
        this.scoreManager = new ScoreManager();
        
        this.state = GameState.MENU;
        this.mode = GameMode.CLASSIC;
        this.difficulty = Difficulty.MEDIUM;
        this.currentSpeedMs = difficulty.getTickDelayMs();
        this.soundEnabled = true;
        this.gridLinesEnabled = true;
        this.level = 1;

        int gridW = 30;
        int gridH = 30;
        this.board = new Board(gridW, gridH);
        this.snake = new Snake(gridW / 2, gridH / 2, 4, Direction.RIGHT);
        this.food = new Food();

        // Initialize Timer
        this.gameTimer = new Timer(currentSpeedMs, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGame();
            }
        });
    }

    public void startNewGame(GameMode selectedMode, Difficulty selectedDifficulty) {
        this.mode = selectedMode;
        this.difficulty = selectedDifficulty;
        this.currentSpeedMs = difficulty.getTickDelayMs();
        this.score = 0;
        this.applesEaten = 0;
        this.level = 1;

        int gridW = 30;
        int gridH = 30;
        this.board = new Board(gridW, gridH);
        
        if (mode == GameMode.CLASSIC) {
            board.setWallsEnabled(false);
        } else if (mode == GameMode.ARCADE) {
            board.setWallsEnabled(true);
        } else if (mode == GameMode.OBSTACLES) {
            board.setWallsEnabled(false);
            board.generateObstacles(8, snake);
        } else if (mode == GameMode.CAMPAIGN) {
            board.setWallsEnabled(true);
            board.generateObstacles(5 + level * 2, snake);
        }

        this.snake = new Snake(gridW / 2, gridH / 2, 4, Direction.RIGHT);
        this.food.respawn(gridW, gridH, snake);

        this.state = GameState.PLAYING;
        gameTimer.setDelay(currentSpeedMs);
        if (!gameTimer.isRunning()) {
            gameTimer.start();
        }

        scorePanel.updateScore(score, scoreManager.getHighScore(), level);
        gamePanel.repaint();
    }

    public void togglePause() {
        if (state == GameState.PLAYING) {
            state = GameState.PAUSED;
            gameTimer.stop();
            gamePanel.repaint();
        } else if (state == GameState.PAUSED) {
            state = GameState.PLAYING;
            gameTimer.start();
            gamePanel.repaint();
        }
    }

    public void setDirection(Direction dir) {
        if (state == GameState.PLAYING) {
            snake.setDirection(dir);
        }
    }

    private void updateGame() {
        if (state != GameState.PLAYING) return;

        boolean wrap = !board.isWallsEnabled();
        snake.move(board.getWidth(), board.getHeight(), wrap);

        // Check collisions with obstacles or walls
        if (board.checkObstacleCollision(snake.getHeadX(), snake.getHeadY())) {
            gameOver();
            return;
        }

        // Check self collision
        if (snake.checkSelfCollision()) {
            gameOver();
            return;
        }

        // Check food collision
        if (snake.getHeadX() == food.getX() && snake.getHeadY() == food.getY()) {
            if (soundEnabled) {
                Toolkit.getDefaultToolkit().beep();
            }
            Food.FoodType fType = food.getType();
            score += fType.getPoints();
            if (score < 0) score = 0;
            applesEaten++;

            if (fType == Food.FoodType.POISON) {
                // Shrink effect or penalty handled by snake growth/shrinking logic if needed
            } else {
                for (int i = 0; i < fType.getGrowth(); i++) {
                    snake.grow();
                }
            }

            // Level progression in Campaign mode
            if (mode == GameMode.CAMPAIGN && applesEaten >= level * 5) {
                level++;
                currentSpeedMs = Math.max(30, currentSpeedMs - 8);
                gameTimer.setDelay(currentSpeedMs);
                board.generateObstacles(5 + level * 2, snake);
            }

            food.respawn(board.getWidth(), board.getHeight(), snake);
        }

        scoreManager.checkNewHighScore(score);
        scorePanel.updateScore(score, scoreManager.getHighScore(), level);
        gamePanel.repaint();
    }

    private void gameOver() {
        state = GameState.GAMEOVER;
        gameTimer.stop();
        if (soundEnabled) {
            Toolkit.getDefaultToolkit().beep();
        }
        scoreManager.checkNewHighScore(score);
        scorePanel.updateScore(score, scoreManager.getHighScore(), level);
        gamePanel.repaint();
    }

    public GameState getState() { return state; }
    public GameMode getMode() { return mode; }
    public Difficulty getDifficulty() { return difficulty; }
    public Board getBoard() { return board; }
    public Snake getSnake() { return snake; }
    public Food getFood() { return food; }
    public ScoreManager getScoreManager() { return scoreManager; }
    public int getScore() { return score; }
    public int getLevel() { return level; }
    public boolean isGridLinesEnabled() { return gridLinesEnabled; }
}
