package com.arcade.snake;

import java.awt.*;
import java.util.List;

public class Board {
    private final int width;
    private final int height;
    private final int tileSize;

    public Board(int width, int height, int tileSize) {
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
    }

    public void render(Graphics2D g2d, Snake snake, Food food, boolean gameOver) {
        // Draw background grid/board
        g2d.setColor(new Color(20, 24, 33));
        g2d.fillRect(0, 0, width, height);

        // Draw subtle grid lines
        g2d.setColor(new Color(30, 36, 48));
        for (int x = 0; x < width; x += tileSize) {
            g2d.drawLine(x, 0, x, height);
        }
        for (int y = 0; y < height; y += tileSize) {
            g2d.drawLine(0, y, width, y);
        }

        // Draw Food (Glowing Apple / Circle with stem)
        int foodX = food.getX();
        int foodY = food.getY();
        g2d.setColor(new Color(239, 68, 68)); // Bright red
        g2d.fillOval(foodX + 2, foodY + 2, tileSize - 4, tileSize - 4);
        g2d.setColor(new Color(34, 197, 94)); // Leaf
        g2d.fillRect(foodX + tileSize / 2 - 1, foodY, 2, 4);

        // Draw Snake
        List<Point> body = snake.getBody();
        for (int i = 0; i < body.size(); i++) {
            Point p = body.get(i);
            if (i == 0) {
                // Snake Head with gradient or distinct color
                g2d.setColor(new Color(16, 185, 129)); // Emerald head
                g2d.fillRoundRect(p.x + 1, p.y + 1, tileSize - 2, tileSize - 2, 8, 8);
                
                // Draw eyes based on direction
                g2d.setColor(Color.WHITE);
                Direction dir = snake.getCurrentDirection();
                int eye1X = p.x + 4, eye1Y = p.y + 4;
                int eye2X = p.x + tileSize - 8, eye2Y = p.y + 4;
                switch (dir) {
                    case UP:
                        eye1X = p.x + 4; eye1Y = p.y + 4;
                        eye2X = p.x + tileSize - 8; eye2Y = p.y + 4;
                        break;
                    case DOWN:
                        eye1X = p.x + 4; eye1Y = p.y + tileSize - 8;
                        eye2X = p.x + tileSize - 8; eye2Y = p.y + tileSize - 8;
                        break;
                    case LEFT:
                        eye1X = p.x + 4; eye1Y = p.y + 4;
                        eye2X = p.x + 4; eye2Y = p.y + tileSize - 8;
                        break;
                    case RIGHT:
                        eye1X = p.x + tileSize - 8; eye1Y = p.y + 4;
                        eye2X = p.x + tileSize - 8; eye2Y = p.y + tileSize - 8;
                        break;
                }
                g2d.fillOval(eye1X, eye1Y, 4, 4);
                g2d.fillOval(eye2X, eye2Y, 4, 4);

            } else {
                // Snake Body
                g2d.setColor(new Color(52, 211, 153)); // Lighter green body
                g2d.fillRoundRect(p.x + 2, p.y + 2, tileSize - 4, tileSize - 4, 6, 6);
            }
        }

        // Draw Game Over overlay if applicable
        if (gameOver) {
            g2d.setColor(new Color(0, 0, 0, 180));
            g2d.fillRect(0, 0, width, height);

            g2d.setFont(new Font("SansSerif", Font.BOLD, 36));
            g2d.setColor(new Color(239, 68, 68));
            String goText = "GAME OVER";
            FontMetrics fm = g2d.getFontMetrics();
            g2d.drawString(goText, (width - fm.stringWidth(goText)) / 2, height / 2 - 20);

            g2d.setFont(new Font("SansSerif", Font.PLAIN, 18));
            g2d.setColor(Color.WHITE);
            String restartText = "Press ENTER or SPACE to Restart";
            fm = g2d.getFontMetrics();
            g2d.drawString(restartText, (width - fm.stringWidth(restartText)) / 2, height / 2 + 20);
        }
    }
}
