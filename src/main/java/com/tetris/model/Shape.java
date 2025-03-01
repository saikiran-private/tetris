package com.tetris.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Tetris piece composed of multiple blocks
 */
public class Shape {
    private List<Block> blocks;
    private int shapeType;
    private int rotationState;
    
    // Constants for shape types
    public static final int I_SHAPE = 0;
    public static final int J_SHAPE = 1;
    public static final int L_SHAPE = 2;
    public static final int O_SHAPE = 3;
    public static final int S_SHAPE = 4;
    public static final int T_SHAPE = 5;
    public static final int Z_SHAPE = 6;
    
    // Colors for different shapes
    public static final int[] COLORS = {
        0x00FFFF, // Cyan (I)
        0x0000FF, // Blue (J)
        0xFFA500, // Orange (L)
        0xFFFF00, // Yellow (O)
        0x00FF00, // Green (S)
        0x800080, // Purple (T)
        0xFF0000  // Red (Z)
    };

    public Shape(int shapeType) {
        this.shapeType = shapeType;
        this.rotationState = 0;
        this.blocks = new ArrayList<>();
        initializeShape();
    }
    
    private void initializeShape() {
        // Clear existing blocks
        blocks.clear();
        
        // Create blocks based on shape type
        switch (shapeType) {
            case I_SHAPE:
                // I shape (horizontal)
                blocks.add(new Block(0, 0, COLORS[I_SHAPE]));
                blocks.add(new Block(1, 0, COLORS[I_SHAPE]));
                blocks.add(new Block(2, 0, COLORS[I_SHAPE]));
                blocks.add(new Block(3, 0, COLORS[I_SHAPE]));
                break;
            case J_SHAPE:
                // J shape
                blocks.add(new Block(0, 0, COLORS[J_SHAPE]));
                blocks.add(new Block(0, 1, COLORS[J_SHAPE]));
                blocks.add(new Block(1, 1, COLORS[J_SHAPE]));
                blocks.add(new Block(2, 1, COLORS[J_SHAPE]));
                break;
            case L_SHAPE:
                // L shape
                blocks.add(new Block(2, 0, COLORS[L_SHAPE]));
                blocks.add(new Block(0, 1, COLORS[L_SHAPE]));
                blocks.add(new Block(1, 1, COLORS[L_SHAPE]));
                blocks.add(new Block(2, 1, COLORS[L_SHAPE]));
                break;
            case O_SHAPE:
                // O shape (square)
                blocks.add(new Block(0, 0, COLORS[O_SHAPE]));
                blocks.add(new Block(1, 0, COLORS[O_SHAPE]));
                blocks.add(new Block(0, 1, COLORS[O_SHAPE]));
                blocks.add(new Block(1, 1, COLORS[O_SHAPE]));
                break;
            case S_SHAPE:
                // S shape
                blocks.add(new Block(1, 0, COLORS[S_SHAPE]));
                blocks.add(new Block(2, 0, COLORS[S_SHAPE]));
                blocks.add(new Block(0, 1, COLORS[S_SHAPE]));
                blocks.add(new Block(1, 1, COLORS[S_SHAPE]));
                break;
            case T_SHAPE:
                // T shape
                blocks.add(new Block(1, 0, COLORS[T_SHAPE]));
                blocks.add(new Block(0, 1, COLORS[T_SHAPE]));
                blocks.add(new Block(1, 1, COLORS[T_SHAPE]));
                blocks.add(new Block(2, 1, COLORS[T_SHAPE]));
                break;
            case Z_SHAPE:
                // Z shape
                blocks.add(new Block(0, 0, COLORS[Z_SHAPE]));
                blocks.add(new Block(1, 0, COLORS[Z_SHAPE]));
                blocks.add(new Block(1, 1, COLORS[Z_SHAPE]));
                blocks.add(new Block(2, 1, COLORS[Z_SHAPE]));
                break;
        }
    }
    
    public void rotate() {
        // Skip rotation for O shape (square)
        if (shapeType == O_SHAPE) {
            return;
        }
        
        // Find center of rotation (usually the second block for most shapes)
        Block center = blocks.get(1);
        int centerX = center.getX();
        int centerY = center.getY();
        
        // Rotate each block around the center
        for (Block block : blocks) {
            if (block != center) {
                // Translate to origin
                int x = block.getX() - centerX;
                int y = block.getY() - centerY;
                
                // Rotate 90 degrees clockwise: (x,y) -> (y,-x)
                int newX = y;
                int newY = -x;
                
                // Translate back
                block.setX(newX + centerX);
                block.setY(newY + centerY);
            }
        }
        
        // Update rotation state
        rotationState = (rotationState + 1) % 4;
    }
    
    public void move(int deltaX, int deltaY) {
        for (Block block : blocks) {
            block.move(deltaX, deltaY);
        }
    }
    
    public List<Block> getBlocks() {
        return new ArrayList<>(blocks);
    }
    
    public int getShapeType() {
        return shapeType;
    }
    
    public int getRotationState() {
        return rotationState;
    }
    
    public Shape getCopy() {
        Shape copy = new Shape(this.shapeType);
        copy.blocks.clear();
        for (Block block : this.blocks) {
            copy.blocks.add(new Block(block));
        }
        copy.rotationState = this.rotationState;
        return copy;
    }
}
