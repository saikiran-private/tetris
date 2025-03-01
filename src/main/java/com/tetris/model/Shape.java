package com.tetris.model;

/**
 * Represents a Tetris shape (tetromino)
 */
public class Shape {
    private Block[] blocks;
    private int shapeType;
    
    // Shape types
    public static final int I_SHAPE = 0;
    public static final int J_SHAPE = 1;
    public static final int L_SHAPE = 2;
    public static final int O_SHAPE = 3;
    public static final int S_SHAPE = 4;
    public static final int T_SHAPE = 5;
    public static final int Z_SHAPE = 6;
    
    // Colors for each shape type
    private static final int[] COLORS = {
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
        this.blocks = createBlocks(shapeType);
    }
    
    private Block[] createBlocks(int shapeType) {
        Block[] blocks = new Block[4]; // All tetrominoes have 4 blocks
        
        switch (shapeType) {
            case I_SHAPE:
                // I shape (horizontal)
                blocks[0] = new Block(0, 0, COLORS[I_SHAPE]);
                blocks[1] = new Block(1, 0, COLORS[I_SHAPE]);
                blocks[2] = new Block(2, 0, COLORS[I_SHAPE]);
                blocks[3] = new Block(3, 0, COLORS[I_SHAPE]);
                break;
            case J_SHAPE:
                // J shape
                blocks[0] = new Block(0, 0, COLORS[J_SHAPE]);
                blocks[1] = new Block(0, 1, COLORS[J_SHAPE]);
                blocks[2] = new Block(1, 1, COLORS[J_SHAPE]);
                blocks[3] = new Block(2, 1, COLORS[J_SHAPE]);
                break;
            case L_SHAPE:
                // L shape
                blocks[0] = new Block(2, 0, COLORS[L_SHAPE]);
                blocks[1] = new Block(0, 1, COLORS[L_SHAPE]);
                blocks[2] = new Block(1, 1, COLORS[L_SHAPE]);
                blocks[3] = new Block(2, 1, COLORS[L_SHAPE]);
                break;
            case O_SHAPE:
                // O shape (square)
                blocks[0] = new Block(0, 0, COLORS[O_SHAPE]);
                blocks[1] = new Block(1, 0, COLORS[O_SHAPE]);
                blocks[2] = new Block(0, 1, COLORS[O_SHAPE]);
                blocks[3] = new Block(1, 1, COLORS[O_SHAPE]);
                break;
            case S_SHAPE:
                // S shape
                blocks[0] = new Block(1, 0, COLORS[S_SHAPE]);
                blocks[1] = new Block(2, 0, COLORS[S_SHAPE]);
                blocks[2] = new Block(0, 1, COLORS[S_SHAPE]);
                blocks[3] = new Block(1, 1, COLORS[S_SHAPE]);
                break;
            case T_SHAPE:
                // T shape
                blocks[0] = new Block(1, 0, COLORS[T_SHAPE]);
                blocks[1] = new Block(0, 1, COLORS[T_SHAPE]);
                blocks[2] = new Block(1, 1, COLORS[T_SHAPE]);
                blocks[3] = new Block(2, 1, COLORS[T_SHAPE]);
                break;
            case Z_SHAPE:
                // Z shape
                blocks[0] = new Block(0, 0, COLORS[Z_SHAPE]);
                blocks[1] = new Block(1, 0, COLORS[Z_SHAPE]);
                blocks[2] = new Block(1, 1, COLORS[Z_SHAPE]);
                blocks[3] = new Block(2, 1, COLORS[Z_SHAPE]);
                break;
            default:
                // Default to I shape
                blocks[0] = new Block(0, 0, COLORS[I_SHAPE]);
                blocks[1] = new Block(1, 0, COLORS[I_SHAPE]);
                blocks[2] = new Block(2, 0, COLORS[I_SHAPE]);
                blocks[3] = new Block(3, 0, COLORS[I_SHAPE]);
        }
        
        return blocks;
    }
    
    public void rotate() {
        // Skip rotation for O shape (square)
        if (shapeType == O_SHAPE) {
            return;
        }
        
        // Find the center of rotation (usually the second block for most shapes)
        int centerX = blocks[1].getX();
        int centerY = blocks[1].getY();
        
        // Rotate each block around the center
        for (Block block : blocks) {
            int x = block.getX() - centerX;
            int y = block.getY() - centerY;
            
            // Rotate 90 degrees clockwise: (x, y) -> (-y, x)
            block.setPosition(centerX - y, centerY + x);
        }
    }
    
    public void move(int deltaX, int deltaY) {
        for (Block block : blocks) {
            block.move(deltaX, deltaY);
        }
    }
    
    public void setPosition(int x, int y) {
        // Calculate the current top-left position
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        
        for (Block block : blocks) {
            minX = Math.min(minX, block.getX());
            minY = Math.min(minY, block.getY());
        }
        
        // Calculate the offset to move to the new position
        int deltaX = x - minX;
        int deltaY = y - minY;
        
        // Move all blocks by the offset
        move(deltaX, deltaY);
    }
    
    public Shape getCopy() {
        Shape copy = new Shape(shapeType);
        
        for (int i = 0; i < blocks.length; i++) {
            copy.blocks[i].setPosition(blocks[i].getX(), blocks[i].getY());
        }
        
        return copy;
    }
    
    public Block[] getBlocks() {
        return blocks;
    }
    
    public int getShapeType() {
        return shapeType;
    }
    
    public int getWidth() {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        
        for (Block block : blocks) {
            minX = Math.min(minX, block.getX());
            maxX = Math.max(maxX, block.getX());
        }
        
        return maxX - minX + 1;
    }
    
    public int getHeight() {
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        
        for (Block block : blocks) {
            minY = Math.min(minY, block.getY());
            maxY = Math.max(maxY, block.getY());
        }
        
        return maxY - minY + 1;
    }
    
    // Static method to create a random shape
    public static Shape createRandomShape() {
        int shapeType = (int) (Math.random() * 7); // 7 different shapes
        return new Shape(shapeType);
    }
}