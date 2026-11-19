package com.arcade.snake;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GamePanel extends JPanel {
    private GameEngine gameEngine;
    private static final int CELL_SIZE = 20;

    public GamePanel() {
        setPreferredSize(new Dimension(600, 600));
        setBackground(new Color(20, 20, 30));
        setFocusable(true);
    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameEngine == null) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GameEngine.GameState state = gameEngine.getState();

        if (state == GameEngine.GameState.MENU) {
            drawMenu(g2d);
        } else {
            drawBoard(g2d);
            drawFood(g2d);
            drawSnake(g2d);
            drawObstacles(g2d);

            if (state == GameEngine.GameState.PAUSED) {
                drawOverlay(g2d, "PAUSED", "Press SPACE or P to Resume");
            } else if (state == GameEngine.GameState.GAMEOVER) {
                drawOverlay(g2d, "GAME OVER", "Press ENTER to Play Again");
            }
        }
    }

    private void drawMenu(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 36));
        String title = "RETRO SNAKE ARCADE";
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString(title, (getWidth() - fm.stringWidth(title)) / 2, 180);

        g2d.setFont(new Font("Arial", Font.PLAIN, 18));
        String sub = "Press ENTER to Start Classic Game";
        fm = g2d.getFontMetrics();
        g2d.drawString(sub, (getWidth() - fm.stringWidth(sub)) / 2, 260);

        String controls = "Controls: Arrow Keys / WASD to Move, SPACE to Pause";
        g2d.drawString(controls, (getWidth() - g2d.getFontMetrics().stringWidth(controls)) / 2, 340);
        
        String modeInfo = "Modes: Classic, Arcade, Obstacles, Campaign";
        g2d.drawString(modeInfo, (getWidth() - g2d.getFontMetrics().stringWidth(modeInfo)) / 2, 380);
    }

    private void drawBoard(Graphics2D g2d) {
        if (gameEngine.isGridLinesEnabled()) {
            g2d.setColor(new Color(35, 35, 48));
            for (int i = 0; i < getWidth(); i += CELL_SIZE) {
                g2d.drawLine(i, 0, i, getHeight());
            }
            for (int j = 0; j < getHeight(); j += CELL_SIZE) {
                g2d.drawLine(0, j, getWidth(), j);
            }
        }

        Board board = gameEngine.getBoard();
        if (board.isWallsEnabled()) {
            g2d.setColor(new Color(200, 50, 50));
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRect(0, 0, board.getWidth() * CELL_SIZE, board.getHeight() * CELL_SIZE);
        }
    }

    private void drawSnake(Graphics2D g2d) {
        Snake snake = gameEngine.getSnake();
        for (int i = 0; i < snake.getLength(); i++) {
            if (i == 0) {
                g2d.setColor(new Color(50, 220, 100)); // Head
            } else {
                g2d.setColor(new Color(40, 180, 80)); // Body
            }
            g2d.fillRoundRect(snake.getX(i) * CELL_SIZE + 2, snake.getY(i) * CELL_SIZE + 2, CELL_SIZE - 4, CELL_SIZE - 4, 8, 8);
        }
    }

    private void drawFood(Graphics2D g2d) {
        Food food = gameEngine.getFood();
        Food.FoodType type = food.getType();
        if (type == Food.FoodType.APPLE) {
            g2d.setColor(new Color(230, 60, 60));
        } else if (type == Food.FoodType.GOLDEN) {
            g2d.setColor(new Color(255, 215, 0));
        } else {
            g2d.setColor(new Color(150, 50, 200)); // Poison
        }
        g2d.fillOval(food.getX() * CELL_SIZE + 3, food.getY() * CELL_SIZE + 3, CELL_SIZE - 6, CELL_SIZE - 6);
    }

    private void drawObstacles(Graphics2D g2d) {
        Board board = gameEngine.getBoard();
        g2d.setColor(new Color(100, 100, 120));
        for (Point p : board.getObstacles()) {
            g2d.fillRect(p.x * CELL_SIZE + 1, p.y * CELL_SIZE + 1, CELL_SIZE - 2, CELL_SIZE - 2);
        }
    }

    private void drawOverlay(Graphics2D g2d, String title, String subtitle) {
        g2d.setColor(new Color(0, 0, 0, 160));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 36));
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString(title, (getWidth() - fm.stringWidth(title)) / 2, getHeight() / 2 - 20);

        g2d.setFont(new Font("Arial", Font.PLAIN, 18));
        fm = g2d.getFontMetrics();
        g2d.drawString(subtitle, (getWidth() - fm.stringWidth(subtitle)) / 2, getHeight() / 2 + 25);
    }
}
