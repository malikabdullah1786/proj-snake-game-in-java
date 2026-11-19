package com.arcade.snake;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private final int width;
    private final int height;
    private boolean wallsEnabled;
    private final List<Point> obstacles;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.wallsEnabled = false;
        this.obstacles = new ArrayList<>();
    }

    public void generateObstacles(int count, Snake snake) {
        obstacles.clear();
        Random random = new Random();
        int margin = 3;
        while (obstacles.size() < count) {
            int ox = margin + random.nextInt(width - 2 * margin);
            int oy = margin + random.nextInt(height - 2 * margin);
            Point p = new Point(ox, oy);
            if (!snake.checkCollisionWith(ox, oy) && !obstacles.contains(p)) {
                obstacles.add(p);
            }
        }
    }

    public boolean checkObstacleCollision(int x, int y) {
        if (wallsEnabled) {
            if (x < 0 || x >= width || y < 0 || y >= height) {
                return true;
            }
        }
        for (Point p : obstacles) {
            if (p.x == x && p.y == y) {
                return true;
            }
        }
        return false;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean isWallsEnabled() { return wallsEnabled; }
    public void setWallsEnabled(boolean wallsEnabled) { this.wallsEnabled = wallsEnabled; }
    public List<Point> getObstacles() { return obstacles; }
}
