package com.tetris.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void testBoardCreation() {
        Board board = new Board(10, 20);
        assertEquals(10, board.getWidth());
        assertEquals(20, board.getHeight());
        assertEquals(0, board.getScore());
        assertFalse(board.isGameOver());
        assertNotNull(board.getCurrentShape());
        assertNotNull(board.getNextShape());
    }
    
    @Test
    public void testMoveShapeDown() {
        Board board = new Board(10, 20);
        Shape initialShape = board.getCurrentShape();
        
        // Get initial Y position of first block
        int initialY = initialShape.getBlocks().get(0).getY();
        
        // Move shape down
        boolean moved = board.moveShapeDown();
        
        // Check if shape moved down
        assertTrue(moved);
        assertEquals(initialY + 1, board.getCurrentShape().getBlocks().get(0).getY());
    }
    
    @Test
    public void testMoveShapeLeftAndRight() {
        Board board = new Board(10, 20);
        Shape initialShape = board.getCurrentShape();
        
        // Get initial X position of first block
        int initialX = initialShape.getBlocks().get(0).getX();
        
        // Move shape left
        boolean movedLeft = board.moveShapeLeft();
        
        // Check if shape moved left
        assertTrue(movedLeft);
        assertEquals(initialX - 1, board.getCurrentShape().getBlocks().get(0).getX());
        
        // Move shape right twice (back to original + 1)
        board.moveShapeRight();
        boolean movedRight = board.moveShapeRight();
        
        // Check if shape moved right
        assertTrue(movedRight);
        assertEquals(initialX + 1, board.getCurrentShape().getBlocks().get(0).getX());
    }
    
    @Test
    public void testRotateShape() {
        Board board = new Board(10, 20);
        
        // Force an I shape for predictable rotation
        // This is a bit of a hack since we can't directly set the shape
        // In a real test, we might use dependency injection or a factory method
        while (board.getCurrentShape().getShapeType() != Shape.I_SHAPE) {
            board = new Board(10, 20);
        }
        
        Shape initialShape = board.getCurrentShape();
        
        // Get initial state
        int initialRotationState = initialShape.getRotationState();
        
        // Rotate shape
        boolean rotated = board.rotateShape();
        
        // Check if shape rotated
        assertTrue(rotated);
        assertEquals((initialRotationState + 1) % 4, board.getCurrentShape().getRotationState());
    }
    
    @Test
    public void testIsValidPosition() {
        Board board = new Board(10, 20);
        Shape currentShape = board.getCurrentShape();
        
        // Current position should be valid
        assertTrue(board.isValidPosition(currentShape));
        
        // Create a copy and move it far to the right (out of bounds)
        Shape outOfBoundsShape = currentShape.getCopy();
        outOfBoundsShape.move(board.getWidth(), 0);
        
        // Position should be invalid
        assertFalse(board.isValidPosition(outOfBoundsShape));
    }
}
