package com.arcade.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener {
    private static final int BOARD_WIDTH = 600;
    private static final int BOARD_HEIGHT = 500;
    private static final int TILE_SIZE = 20;
    private static final int TIMER_DELAY = 100; // 100ms ticks

    private final GameEngine gameEngine;
    private final Board board;
    private final Timer timer;
    private final ScorePanel scorePanel;

    public GamePanel(ScorePanel scorePanel) {
        this.scorePanel = scorePanel;
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setBackground(new Color(20, 24, 33));
        setFocusable(true);

        this.gameEngine = new GameEngine(BOARD_WIDTH, BOARD_HEIGHT, TILE_SIZE);
        this.board = new Board(BOARD_WIDTH, BOARD_HEIGHT, TILE_SIZE);

        addKeyListener(new TAdapter());

        this.timer = new Timer(TIMER_DELAY, this);
        this.timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameEngine.tick();
        scorePanel.updateScore(gameEngine.getScore());
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        board.render(g2d, gameEngine.getSnake(), gameEngine.getFood(), gameEngine.isGameOver());
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (gameEngine.isGameOver()) {
                if (key == KeyEvent.VK_ENTER || key == KeyEvent.VK_SPACE) {
                    gameEngine.reset();
                    scorePanel.updateScore(gameEngine.getScore());
                    repaint();
                }
                return;
            }

            switch (key) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    gameEngine.getSnake().setDirection(Direction.UP);
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    gameEngine.getSnake().setDirection(Direction.DOWN);
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    gameEngine.getSnake().setDirection(Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    gameEngine.getSnake().setDirection(Direction.RIGHT);
                    break;
            }
        }
    }
}
