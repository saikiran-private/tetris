package com.tetris.model;

/**
 * Represents a single block in a Tetris shape
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
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getColor() {
        return color;
    }
    
    public void move(int deltaX, int deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }
    
    // Add this method to set the position directly
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Block other = (Block) obj;
        return x == other.x && y == other.y && color == other.color;
    }
    
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + x;
        result = 31 * result + y;
        result = 31 * result + color;
        return result;
    }
}