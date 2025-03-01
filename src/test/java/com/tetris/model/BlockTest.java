package com.tetris.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class BlockTest {
    
    @Test
    public void testBlockCreation() {
        Block block = new Block(5, 10, 0xFF0000);
        
        assertEquals(5, block.getX());
        assertEquals(10, block.getY());
        assertEquals(0xFF0000, block.getColor());
    }
    
    @Test
    public void testBlockCopy() {
        Block original = new Block(5, 10, 0xFF0000);
        Block copy = new Block(original.getX(), original.getY(), original.getColor());
        
        assertEquals(original.getX(), copy.getX());
        assertEquals(original.getY(), copy.getY());
        assertEquals(original.getColor(), copy.getColor());
        
        // Modify the copy
        copy.setPosition(7, 12);
        
        // Original should remain unchanged
        assertEquals(5, original.getX());
        assertEquals(10, original.getY());
        
        // Copy should be updated
        assertEquals(7, copy.getX());
        assertEquals(12, copy.getY());
    }
    
    @Test
    public void testBlockMove() {
        Block block = new Block(5, 10, 0xFF0000);
        
        block.move(3, -2);
        
        assertEquals(8, block.getX());
        assertEquals(8, block.getY());
    }
    
    @Test
    public void testBlockEquals() {
        Block block1 = new Block(5, 10, 0xFF0000);
        Block block2 = new Block(5, 10, 0xFF0000);
        Block block3 = new Block(6, 10, 0xFF0000);
        Block block4 = new Block(5, 11, 0xFF0000);
        Block block5 = new Block(5, 10, 0x00FF00);
        
        // Same position and color
        assertEquals(block1, block2);
        
        // Different X
        assertNotEquals(block1, block3);
        
        // Different Y
        assertNotEquals(block1, block4);
        
        // Different color
        assertNotEquals(block1, block5);
    }
    
    @Test
    public void testBlockHashCode() {
        Block block1 = new Block(5, 10, 0xFF0000);
        Block block2 = new Block(5, 10, 0xFF0000);
        
        // Same position and color should have same hash code
        assertEquals(block1.hashCode(), block2.hashCode());
    }
}