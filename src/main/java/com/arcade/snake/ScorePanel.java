package com.arcade.snake;

import javax.swing.JPanel;
import java.awt.*;

public class ScorePanel extends JPanel {
    private int score = 0;
    private final ScoreManager scoreManager;

    public ScorePanel() {
        this.scoreManager = new ScoreManager();
        setPreferredSize(new Dimension(600, 45));
        setBackground(new Color(15, 17, 23));
    }

    public void updateScore(int score) {
        this.score = score;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(new Color(240, 240, 245));
        g2d.setFont(new Font("Monospaced", Font.BOLD, 18));

        String scoreText = "SCORE: " + score;
        int highScore = Math.max(score, scoreManager.getHighScore());
        String highText = "HIGH SCORE: " + highScore;

        g2d.drawString(scoreText, 25, 28);
        
        FontMetrics fm = g2d.getFontMetrics();
        int highTextWidth = fm.stringWidth(highText);
        g2d.drawString(highText, getWidth() - highTextWidth - 25, 28);
    }
}
