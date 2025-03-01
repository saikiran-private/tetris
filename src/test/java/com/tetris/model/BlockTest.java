package com.tetris.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class BlockTest {

    @Test
    public void testBlockCreation() {
        Block block = new Block(5, 10, 0xFF0000);
        assertEquals(5, block.getX());
        assertEquals(10, block.getY());
        assertEquals(0xFF0000, block.getColor());
    }
    
    @Test
    public void testBlockCopyConstructor() {
        Block original = new Block(3, 7, 0x00FF00);
        Block copy = new Block(original);
        
        assertEquals(original.getX(), copy.getX());
        assertEquals(original.getY(), copy.getY());
        assertEquals(original.getColor(), copy.getColor());
        
        // Verify that modifying the copy doesn't affect the original
        copy.setX(8);
        copy.setY(12);
        
        assertEquals(3, original.getX());
        assertEquals(7, original.getY());
        assertEquals(8, copy.getX());
        assertEquals(12, copy.getY());
    }
    
    @Test
    public void testBlockMove() {
        Block block = new Block(2, 3, 0x0000FF);
        block.move(1, 2);
        
        assertEquals(3, block.getX());
        assertEquals(5, block.getY());
        
        block.move(-2, -1);
        
        assertEquals(1, block.getX());
        assertEquals(4, block.getY());
    }
    
    @Test
    public void testBlockEquals() {
        Block block1 = new Block(1, 2, 0xFF0000);
        Block block2 = new Block(1, 2, 0xFF0000);
        Block block3 = new Block(2, 2, 0xFF0000);
        Block block4 = new Block(1, 3, 0xFF0000);
        Block block5 = new Block(1, 2, 0x00FF00);
        
        assertEquals(block1, block2);
        assertNotEquals(block1, block3);
        assertNotEquals(block1, block4);
        assertNotEquals(block1, block5);
    }
    
    @Test
    public void testBlockHashCode() {
        Block block1 = new Block(1, 2, 0xFF0000);
        Block block2 = new Block(1, 2, 0xFF0000);
        
        assertEquals(block1.hashCode(), block2.hashCode());
    }
}
