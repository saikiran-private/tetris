package com.tetris.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Tetris game board
 */
public class Board {
    private int width;
    private int height;
    private int[][] grid;
    private Shape currentShape;
    private Shape nextShape;
    private int score;
    private boolean gameOver;
    
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new int[height][width];
        this.score = 0;
        this.gameOver = false;
        initializeBoard();
    }
    
    private void initializeBoard() {
        // Initialize the grid with zeros (empty cells)
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = 0;
            }
        }
        
        // Create the first shape
        createNewShape();
    }
    
    public void createNewShape() {
        // If there's a next shape, make it the current shape
        if (nextShape != null) {
            currentShape = nextShape;
        } else {
            // Create a random shape for the first time
            int shapeType = (int) (Math.random() * 7);
            currentShape = new Shape(shapeType);
        }
        
        // Create a new random shape as the next shape
        int shapeType = (int) (Math.random() * 7);
        nextShape = new Shape(shapeType);
        
        // Position the current shape at the top center of the board
        int startX = (width / 2) - 1;
        currentShape.move(startX, 0);
        
        // Check if the new shape can be placed
        if (!isValidPosition(currentShape)) {
            gameOver = true;
        }
    }
    
    public boolean moveShapeDown() {
        Shape movedShape = currentShape.getCopy();
        movedShape.move(0, 1);
        
        if (isValidPosition(movedShape)) {
            currentShape = movedShape;
            return true;
        } else {
            // Lock the current shape in place
            placeShape();
            // Check for completed lines
            int linesCleared = clearLines();
            // Update score
            updateScore(linesCleared);
            // Create a new shape
            createNewShape();
            return false;
        }
    }
    
    public boolean moveShapeLeft() {
        Shape movedShape = currentShape.getCopy();
        movedShape.move(-1, 0);
        
        if (isValidPosition(movedShape)) {
            currentShape = movedShape;
            return true;
        }
        return false;
    }
    
    public boolean moveShapeRight() {
        Shape movedShape = currentShape.getCopy();
        movedShape.move(1, 0);
        
        if (isValidPosition(movedShape)) {
            currentShape = movedShape;
            return true;
        }
        return false;
    }
    
    public boolean rotateShape() {
        Shape rotatedShape = currentShape.getCopy();
        rotatedShape.rotate();
        
        if (isValidPosition(rotatedShape)) {
            currentShape = rotatedShape;
            return true;
        }
        return false;
    }
    
    public boolean isValidPosition(Shape shape) {
        for (Block block : shape.getBlocks()) {
            int x = block.getX();
            int y = block.getY();
            
            // Check if the block is out of bounds
            if (x < 0 || x >= width || y < 0 || y >= height) {
                return false;
            }
            
            // Check if the block overlaps with existing blocks on the grid
            if (y >= 0 && grid[y][x] != 0) {
                return false;
            }
        }
        return true;
    }
    
    private void placeShape() {
        for (Block block : currentShape.getBlocks()) {
            int x = block.getX();
            int y = block.getY();
            
            // Only place blocks that are within the grid
            if (y >= 0 && y < height && x >= 0 && x < width) {
                grid[y][x] = block.getColor();
            }
        }
    }
    
    private int clearLines() {
        List<Integer> linesToClear = new ArrayList<>();
        
        // Find completed lines
        for (int row = 0; row < height; row++) {
            boolean lineComplete = true;
            for (int col = 0; col < width; col++) {
                if (grid[row][col] == 0) {
                    lineComplete = false;
                    break;
                }
            }
            if (lineComplete) {
                linesToClear.add(row);
            }
        }
        
        // Clear the lines and move blocks down
        for (int lineIndex : linesToClear) {
            // Clear the line
            for (int col = 0; col < width; col++) {
                grid[lineIndex][col] = 0;
            }
            
            // Move all blocks above this line down
            for (int row = lineIndex; row > 0; row--) {
                for (int col = 0; col < width; col++) {
                    grid[row][col] = grid[row - 1][col];
                }
            }
            
            // Clear the top line
            for (int col = 0; col < width; col++) {
                grid[0][col] = 0;
            }
        }
        
        return linesToClear.size();
    }
    
    private void updateScore(int linesCleared) {
        // Simple scoring: 100 points per line, with bonus for multiple lines
        switch (linesCleared) {
            case 1:
                score += 100;
                break;
            case 2:
                score += 300;
                break;
            case 3:
                score += 500;
                break;
            case 4:
                score += 800;
                break;
        }
    }
    
    public int[][] getGrid() {
        // Create a copy of the grid with the current shape
        int[][] displayGrid = new int[height][width];
        
        // Copy the fixed blocks
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                displayGrid[row][col] = grid[row][col];
            }
        }
        
        // Add the current shape
        if (currentShape != null) {
            for (Block block : currentShape.getBlocks()) {
                int x = block.getX();
                int y = block.getY();
                
                if (y >= 0 && y < height && x >= 0 && x < width) {
                    displayGrid[y][x] = block.getColor();
                }
            }
        }
        
        return displayGrid;
    }
    
    public Shape getCurrentShape() {
        return currentShape;
    }
    
    public Shape getNextShape() {
        return nextShape;
    }
    
    public int getScore() {
        return score;
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
}
