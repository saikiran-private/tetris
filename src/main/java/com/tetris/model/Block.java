package com.tetris.model;

/**
 * Represents a single block in a Tetris piece
 */
public class Block {
    private int x;
    private int y;
    private int color;

    public Block(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public Block(Block block) {
        this.x = block.getX();
        this.y = block.getY();
        this.color = block.getColor();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void move(int deltaX, int deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Block block = (Block) obj;
        return x == block.x && y == block.y && color == block.color;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + color;
        return result;
    }
}
