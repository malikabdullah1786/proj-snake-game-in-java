package com.arcade.snake;

import java.awt.Point;
import java.util.Random;

public class Food {
    private int x;
    private int y;
    private final Random random = new Random();

    public Food(int boardWidth, int boardHeight, int tileSize, Snake snake) {
        respawn(boardWidth, boardHeight, tileSize, snake);
    }

    public void respawn(int boardWidth, int boardHeight, int tileSize, Snake snake) {
        boolean onSnake;
        do {
            onSnake = false;
            x = random.nextInt(boardWidth / tileSize) * tileSize;
            y = random.nextInt(boardHeight / tileSize) * tileSize;

            for (Point p : snake.getBody()) {
                if (p.x == x && p.y == y) {
                    onSnake = true;
                    break;
                }
            }
        } while (onSnake);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
