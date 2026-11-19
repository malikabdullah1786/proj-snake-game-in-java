package com.arcade.snake;

import java.util.Random;

public class Food {
    public enum FoodType {
        APPLE(10, 1, 0.70),
        GOLDEN(50, 3, 0.15),
        POISON(-20, 0, 0.15);

        private final int points;
        private final int growth;
        private final double spawnWeight;

        FoodType(int points, int growth, double spawnWeight) {
            this.points = points;
            this.growth = growth;
            this.spawnWeight = spawnWeight;
        }

        public int getPoints() { return points; }
        public int getGrowth() { return growth; }
        public double getSpawnWeight() { return spawnWeight; }
    }

    private int x;
    private int y;
    private FoodType type;
    private final Random random;

    public Food() {
        this.random = new Random();
        this.type = FoodType.APPLE;
        this.x = 0;
        this.y = 0;
    }

    public void respawn(int gridWidth, int gridHeight, Snake snake) {
        // Determine food type based on weights
        double roll = random.nextDouble();
        if (roll < FoodType.APPLE.getSpawnWeight()) {
            type = FoodType.APPLE;
        } else if (roll < FoodType.APPLE.getSpawnWeight() + FoodType.GOLDEN.getSpawnWeight()) {
            type = FoodType.GOLDEN;
        } else {
            type = FoodType.POISON;
        }

        boolean valid;
        do {
            x = random.nextInt(gridWidth);
            y = random.nextInt(gridHeight);
            valid = !snake.checkCollisionWith(x, y);
        } while (!valid);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public FoodType getType() { return type; }
}
