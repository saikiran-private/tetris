package com.tetris.model;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;

public class ShapeTest {
    
    @Test
    public void testShapeCreation() {
        Shape shape = new Shape(Shape.I_SHAPE);
        
        assertEquals(Shape.I_SHAPE, shape.getShapeType());
        assertEquals(4, shape.getBlocks().length);
        
        // Check that blocks are properly initialized
        Block[] blocks = shape.getBlocks();
        assertNotNull(blocks);
        assertEquals(4, blocks.length);
    }
    
    @Test
    public void testShapeMove() {
        Shape shape = new Shape(Shape.I_SHAPE);
        
        // Get initial positions
        Block[] initialBlocks = shape.getBlocks();
        int[] initialX = new int[initialBlocks.length];
        int[] initialY = new int[initialBlocks.length];
        
        for (int i = 0; i < initialBlocks.length; i++) {
            initialX[i] = initialBlocks[i].getX();
            initialY[i] = initialBlocks[i].getY();
        }
        
        // Move the shape
        shape.move(3, 2);
        
        // Check new positions
        Block[] movedBlocks = shape.getBlocks();
        for (int i = 0; i < movedBlocks.length; i++) {
            assertEquals(initialX[i] + 3, movedBlocks[i].getX());
            assertEquals(initialY[i] + 2, movedBlocks[i].getY());
        }
    }
    
    @Test
    public void testShapeSetPosition() {
        Shape shape = new Shape(Shape.I_SHAPE);
        
        // Set position to (5, 10)
        shape.setPosition(5, 10);
        
        // Check that the top-left block is at (5, 10)
        Block[] blocks = shape.getBlocks();
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        
        for (Block block : blocks) {
            minX = Math.min(minX, block.getX());
            minY = Math.min(minY, block.getY());
        }
        
        assertEquals(5, minX);
        assertEquals(10, minY);
    }
    
    @Test
    public void testShapeCopy() {
        Shape original = new Shape(Shape.T_SHAPE);
        Shape copy = original.getCopy();
        
        // Check that the shape type is the same
        assertEquals(original.getShapeType(), copy.getShapeType());
        
        // Check that the blocks are the same
        Block[] originalBlocks = original.getBlocks();
        Block[] copyBlocks = copy.getBlocks();
        
        assertEquals(originalBlocks.length, copyBlocks.length);
        
        for (int i = 0; i < originalBlocks.length; i++) {
            assertEquals(originalBlocks[i].getX(), copyBlocks[i].getX());
            assertEquals(originalBlocks[i].getY(), copyBlocks[i].getY());
            assertEquals(originalBlocks[i].getColor(), copyBlocks[i].getColor());
        }
        
        // Modify the copy
        copy.move(3, 2);
        
        // Check that the original is unchanged
        for (int i = 0; i < originalBlocks.length; i++) {
            assertNotEquals(originalBlocks[i].getX(), copyBlocks[i].getX());
            assertNotEquals(originalBlocks[i].getY(), copyBlocks[i].getY());
        }
    }
    
    @Test
    public void testShapeRotate() {
        // Test with I shape which changes when rotated
        Shape shape = new Shape(Shape.I_SHAPE);
        
        // Get initial positions
        Block[] initialBlocks = shape.getBlocks();
        int[] initialX = new int[initialBlocks.length];
        int[] initialY = new int[initialBlocks.length];
        
        for (int i = 0; i < initialBlocks.length; i++) {
            initialX[i] = initialBlocks[i].getX();
            initialY[i] = initialBlocks[i].getY();
        }
        
        // Rotate the shape
        shape.rotate();
        
        // Check that the positions have changed
        Block[] rotatedBlocks = shape.getBlocks();
        boolean hasChanged = false;
        
        for (int i = 0; i < rotatedBlocks.length; i++) {
            if (initialX[i] != rotatedBlocks[i].getX() || initialY[i] != rotatedBlocks[i].getY()) {
                hasChanged = true;
                break;
            }
        }
        
        assertTrue("Shape should change when rotated", hasChanged);
    }
}