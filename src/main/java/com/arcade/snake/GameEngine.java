package com.arcade.snake;

import java.awt.Point;

public class GameEngine {
    private final int boardWidth;
    private final int boardHeight;
    private final int tileSize;

    private Snake snake;
    private Food food;
    private ScoreManager scoreManager;
    private int score;
    private boolean gameOver;

    public GameEngine(int boardWidth, int boardHeight, int tileSize) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.tileSize = tileSize;
        this.scoreManager = new ScoreManager();
        reset();
    }

    public void reset() {
        this.snake = new Snake(tileSize);
        // Center start position aligned to grid
        int startX = (boardWidth / (2 * tileSize)) * tileSize;
        int startY = (boardHeight / (2 * tileSize)) * tileSize;
        this.snake.reset(startX, startY);
        this.food = new Food(boardWidth, boardHeight, tileSize, snake);
        this.score = 0;
        this.gameOver = false;
    }

    public void tick() {
        if (gameOver) {
            return;
        }

        Point head = snake.getHead();
        int nextX = head.x;
        int nextY = head.y;

        // Peek next position based on direction
        switch (snake.getCurrentDirection()) {
            case UP:
                nextY -= tileSize;
                break;
            case DOWN:
                nextY += tileSize;
                break;
            case LEFT:
                nextX -= tileSize;
                break;
            case RIGHT:
                nextX += tileSize;
                break;
        }

        // Check if food eaten
        boolean grow = (nextX == food.getX() && nextY == food.getY());

        snake.move(grow);

        if (snake.checkCollision(boardWidth, boardHeight)) {
            gameOver = true;
            scoreManager.saveHighScore(score);
            return;
        }

        if (grow) {
            score += 10;
            food.respawn(boardWidth, boardHeight, tileSize, snake);
        }
    }

    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

    public int getScore() {
        return score;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
