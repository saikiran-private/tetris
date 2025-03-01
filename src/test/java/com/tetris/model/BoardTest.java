package com.tetris.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class BoardTest {
    
    @Test
    public void testBoardCreation() {
        Board board = new Board(10, 20);
        
        assertEquals(10, board.getWidth());
        assertEquals(20, board.getHeight());
        assertEquals(0, board.getScore());
        assertFalse(board.isGameOver());
        
        // The grid should be initialized with zeros
        int[][] grid = board.getGrid();
        for (int row = 0; row < 20; row++) {
            for (int col = 0; col < 10; col++) {
                assertEquals(0, grid[row][col]);
            }
        }
    }
    
    @Test
    public void testMoveShapeDown() {
        Board board = new Board(10, 20);
        
        // Create a shape and set it on the board
        Shape shape = Shape.createRandomShape();
        board.setCurrentShape(shape);
        
        // Get the initial position of the shape
        Shape initialShape = board.getCurrentShape();
        int initialY = initialShape.getBlocks()[0].getY();
        
        // Move the shape down
        board.moveShapeDown();
        
        // Get the new position of the shape
        Shape movedShape = board.getCurrentShape();
        int newY = movedShape.getBlocks()[0].getY();
        
        // The Y coordinate should have increased by 1
        assertEquals(initialY + 1, newY);
    }
    
    @Test
    public void testMoveShapeLeftAndRight() {
        Board board = new Board(10, 20);
        
        // Create a shape and set it on the board
        Shape shape = Shape.createRandomShape();
        // Position it in the middle to ensure it can move both left and right
        shape.setPosition(5, 0);
        board.setCurrentShape(shape);
        
        // Get the initial position of the shape
        Shape initialShape = board.getCurrentShape();
        int initialX = initialShape.getBlocks()[0].getX();
        
        // Move the shape left
        board.moveShapeLeft();
        
        // Get the new position of the shape
        Shape movedLeftShape = board.getCurrentShape();
        int leftX = movedLeftShape.getBlocks()[0].getX();
        
        // The X coordinate should have decreased by 1
        assertEquals(initialX - 1, leftX);
        
        // Move the shape right twice (to get back to the right of the initial position)
        board.moveShapeRight();
        board.moveShapeRight();
        
        // Get the new position of the shape
        Shape movedRightShape = board.getCurrentShape();
        int rightX = movedRightShape.getBlocks()[0].getX();
        
        // The X coordinate should have increased by 1 from the initial position
        assertEquals(initialX + 1, rightX);
    }
    
    @Test
    public void testRotateShape() {
        Board board = new Board(10, 20);
        
        // Create a shape (use a specific type that changes when rotated, like I)
        Shape shape = new Shape(0); // I shape
        // Position it in the middle to ensure it can rotate
        shape.setPosition(5, 5);
        board.setCurrentShape(shape);
        
        // Get the current shape
        Shape currentShape = board.getCurrentShape();
        
        // Store the initial block positions
        Block[] initialBlocks = new Block[currentShape.getBlocks().length];
        for (int i = 0; i < currentShape.getBlocks().length; i++) {
            Block block = currentShape.getBlocks()[i];
            initialBlocks[i] = new Block(block.getX(), block.getY(), block.getColor());
        }
        
        // Rotate the shape
        board.rotateShape();
        
        // Get the rotated shape
        Shape rotatedShape = board.getCurrentShape();
        Block[] rotatedBlocks = rotatedShape.getBlocks();
        
        // Check that the blocks have changed position
        boolean hasChanged = false;
        for (int i = 0; i < initialBlocks.length; i++) {
            if (initialBlocks[i].getX() != rotatedBlocks[i].getX() || 
                initialBlocks[i].getY() != rotatedBlocks[i].getY()) {
                hasChanged = true;
                break;
            }
        }
        
        assertTrue("Shape should change when rotated", hasChanged);
    }
    
    @Test
    public void testIsValidPosition() {
        Board board = new Board(10, 20);
        
        // Create a shape
        Shape shape = Shape.createRandomShape();
        
        // Position it in a valid location
        shape.setPosition(5, 5);
        
        // Check that the position is valid
        assertTrue(board.isValidPosition(shape));
        
        // Position it partially outside the board
        shape.setPosition(-1, 5);
        
        // Check that the position is invalid
        assertFalse(board.isValidPosition(shape));
        
        // Position it at the bottom of the board
        shape.setPosition(5, 20 - shape.getHeight());
        
        // Check that the position is valid
        assertTrue(board.isValidPosition(shape));
        
        // Position it below the bottom of the board
        shape.setPosition(5, 20 - shape.getHeight() + 1);
        
        // Check that the position is invalid
        assertFalse(board.isValidPosition(shape));
    }
}