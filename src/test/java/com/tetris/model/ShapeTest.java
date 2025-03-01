package com.tetris.model;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class ShapeTest {

    @Test
    public void testShapeCreation() {
        Shape shape = new Shape(Shape.I_SHAPE);
        assertEquals(Shape.I_SHAPE, shape.getShapeType());
        assertEquals(0, shape.getRotationState());
        
        List<Block> blocks = shape.getBlocks();
        assertEquals(4, blocks.size());
    }
    
    @Test
    public void testShapeRotation() {
        // Test I shape rotation
        Shape iShape = new Shape(Shape.I_SHAPE);
        
        // Initial state (horizontal)
        List<Block> blocks = iShape.getBlocks();
        assertEquals(0, blocks.get(0).getY());
        assertEquals(0, blocks.get(1).getY());
        assertEquals(0, blocks.get(2).getY());
        assertEquals(0, blocks.get(3).getY());
        
        // Rotate once (should become vertical)
        iShape.rotate();
        blocks = iShape.getBlocks();
        
        // Check if blocks are now arranged vertically
        // The exact coordinates depend on the rotation implementation
        // but we can check if they have the same x-coordinate
        int x = blocks.get(0).getX();
        for (Block block : blocks) {
            assertEquals(x, block.getX());
        }
    }
    
    @Test
    public void testShapeMove() {
        Shape shape = new Shape(Shape.T_SHAPE);
        
        // Get initial positions
        List<Block> initialBlocks = shape.getBlocks();
        int[] initialX = new int[initialBlocks.size()];
        int[] initialY = new int[initialBlocks.size()];
        
        for (int i = 0; i < initialBlocks.size(); i++) {
            initialX[i] = initialBlocks.get(i).getX();
            initialY[i] = initialBlocks.get(i).getY();
        }
        
        // Move the shape
        shape.move(2, 3);
        
        // Check new positions
        List<Block> movedBlocks = shape.getBlocks();
        for (int i = 0; i < movedBlocks.size(); i++) {
            assertEquals(initialX[i] + 2, movedBlocks.get(i).getX());
            assertEquals(initialY[i] + 3, movedBlocks.get(i).getY());
        }
    }
    
    @Test
    public void testShapeCopy() {
        Shape original = new Shape(Shape.Z_SHAPE);
        original.rotate(); // Change rotation state
        
        Shape copy = original.getCopy();
        
        // Verify copy has same properties
        assertEquals(original.getShapeType(), copy.getShapeType());
        assertEquals(original.getRotationState(), copy.getRotationState());
        
        // Verify blocks are the same but not the same instances
        List<Block> originalBlocks = original.getBlocks();
        List<Block> copyBlocks = copy.getBlocks();
        
        assertEquals(originalBlocks.size(), copyBlocks.size());
        
        for (int i = 0; i < originalBlocks.size(); i++) {
            Block originalBlock = originalBlocks.get(i);
            Block copyBlock = copyBlocks.get(i);
            
            assertEquals(originalBlock.getX(), copyBlock.getX());
            assertEquals(originalBlock.getY(), copyBlock.getY());
            assertEquals(originalBlock.getColor(), copyBlock.getColor());
            
            // They should be equal but not the same instance
            assertEquals(originalBlock, copyBlock);
            assertNotSame(originalBlock, copyBlock);
        }
    }
    
    @Test
    public void testOShapeRotation() {
        // O shape should not change when rotated
        Shape oShape = new Shape(Shape.O_SHAPE);
        
        // Get initial positions
        List<Block> initialBlocks = oShape.getBlocks();
        int[] initialX = new int[initialBlocks.size()];
        int[] initialY = new int[initialBlocks.size()];
        
        for (int i = 0; i < initialBlocks.size(); i++) {
            initialX[i] = initialBlocks.get(i).getX();
            initialY[i] = initialBlocks.get(i).getY();
        }
        
        // Rotate the O shape
        oShape.rotate();
        
        // Check positions - should be unchanged
        List<Block> rotatedBlocks = oShape.getBlocks();
        for (int i = 0; i < rotatedBlocks.size(); i++) {
            assertEquals(initialX[i], rotatedBlocks.get(i).getX());
            assertEquals(initialY[i], rotatedBlocks.get(i).getY());
        }
    }
}
