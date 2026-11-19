package com.arcade.snake;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    private final List<Point> body;
    private Direction currentDirection;
    private Direction nextDirection;
    private final int tileSize;

    public Snake(int tileSize) {
        this.tileSize = tileSize;
        body = new ArrayList<>();
        // Initialize snake with 3 segments in the center
        body.add(new Point(120, 120));
        body.add(new Point(100, 120));
        body.add(new Point(80, 120));
        currentDirection = Direction.RIGHT;
        nextDirection = Direction.RIGHT;
    }

    public void reset(int startX, int startY) {
        body.clear();
        body.add(new Point(startX, startY));
        body.add(new Point(startX - tileSize, startY));
        body.add(new Point(startX - 2 * tileSize, startY));
        currentDirection = Direction.RIGHT;
        nextDirection = Direction.RIGHT;
    }

    public void move(boolean grow) {
        currentDirection = nextDirection;
        Point head = body.get(0);
        Point newHead = new Point(head.x, head.y);

        switch (currentDirection) {
            case UP:
                newHead.y -= tileSize;
                break;
            case DOWN:
                newHead.y += tileSize;
                break;
            case LEFT:
                newHead.x -= tileSize;
                break;
            case RIGHT:
                newHead.x += tileSize;
                break;
        }

        body.add(0, newHead);
        if (!grow) {
            body.remove(body.size() - 1);
        }
    }

    public boolean checkCollision(int width, int height) {
        Point head = body.get(0);

        // Wall collision
        if (head.x < 0 || head.x >= width || head.y < 0 || head.y >= height) {
            return true;
        }

        // Self collision
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }

        return false;
    }

    public void setDirection(Direction newDirection) {
        if (this.currentDirection == Direction.UP && newDirection == Direction.DOWN) return;
        if (this.currentDirection == Direction.DOWN && newDirection == Direction.UP) return;
        if (this.currentDirection == Direction.LEFT && newDirection == Direction.RIGHT) return;
        if (this.currentDirection == Direction.RIGHT && newDirection == Direction.LEFT) return;
        this.nextDirection = newDirection;
    }

    public List<Point> getBody() {
        return body;
    }

    public Point getHead() {
        return body.get(0);
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }
}
