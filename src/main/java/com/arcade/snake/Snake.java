package com.arcade.snake;

public class Snake {
    private static final int MAX_LENGTH = 3600;
    private final int[] x = new int[MAX_LENGTH];
    private final int[] y = new int[MAX_LENGTH];
    private int length;
    private Direction currentDirection;
    private Direction nextDirection;

    public Snake(int startX, int startY, int initialLength, Direction direction) {
        this.length = Math.max(2, initialLength);
        this.currentDirection = direction;
        this.nextDirection = direction;

        for (int i = 0; i < length; i++) {
            x[i] = startX - (i * direction.getDx());
            y[i] = startY - (i * direction.getDy());
        }
    }

    public void move(int gridWidth, int gridHeight, boolean wrap) {
        currentDirection = nextDirection;

        // Shift body segments
        for (int i = length - 1; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        // Move head
        x[0] += currentDirection.getDx();
        y[0] += currentDirection.getDy();

        if (wrap) {
            if (x[0] < 0) x[0] = gridWidth - 1;
            else if (x[0] >= gridWidth) x[0] = 0;

            if (y[0] < 0) y[0] = gridHeight - 1;
            else if (y[0] >= gridHeight) y[0] = 0;
        }
    }

    public void grow() {
        if (length < MAX_LENGTH) {
            x[length] = x[length - 1];
            y[length] = y[length - 1];
            length++;
        }
    }

    public void setDirection(Direction newDirection) {
        if (!newDirection.isOpposite(currentDirection)) {
            nextDirection = newDirection;
        }
    }

    public boolean checkSelfCollision() {
        int headX = x[0];
        int headY = y[0];
        for (int i = 1; i < length; i++) {
            if (x[i] == headX && y[i] == headY) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCollisionWith(int px, int py) {
        for (int i = 0; i < length; i++) {
            if (x[i] == px && y[i] == py) {
                return true;
            }
        }
        return false;
    }

    public int getHeadX() {
        return x[0];
    }

    public int getHeadY() {
        return y[0];
    }

    public int getLength() {
        return length;
    }

    public int getX(int index) {
        return x[index];
    }

    public int getY(int index) {
        return y[index];
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }
}
