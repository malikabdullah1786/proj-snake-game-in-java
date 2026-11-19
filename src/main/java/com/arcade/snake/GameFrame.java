package com.arcade.snake;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        setTitle("Retro Arcade Snake");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setLayout(new BorderLayout());

        ScorePanel scorePanel = new ScorePanel();
        GamePanel gamePanel = new GamePanel(scorePanel);

        add(scorePanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
