package com.tetris.model;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class GameTest {
    
    private Game game;
    
    @Before
    public void setUp() {
        game = new Game(10, 20);
    }
    
    @After
    public void tearDown() {
        game.shutdown();
    }
    
    @Test
    public void testGameCreation() {
        assertNotNull(game.getBoard());
        assertEquals(1, game.getLevel());
        assertFalse(game.isPaused());
    }
    
    @Test
    public void testGamePauseResume() {
        // Initially not paused
        assertFalse(game.isPaused());
        
        // Pause the game
        game.pause();
        assertTrue(game.isPaused());
        
        // Resume the game
        game.resume();
        assertFalse(game.isPaused());
    }
    
    @Test
    public void testGameRestart() {
        // Start the game
        game.start();
        
        // Pause to prevent timer interference
        game.pause();
        
        // Move the current shape down a few times
        for (int i = 0; i < 5; i++) {
            game.getBoard().moveShapeDown();
        }
        
        // Restart the game
        game.restart();
        
        // Check if game state is reset
        assertEquals(1, game.getLevel());
        assertFalse(game.isPaused());
        assertEquals(0, game.getBoard().getScore());
    }
    
    @Test
    public void testGameListener() {
        final AtomicBoolean boardUpdated = new AtomicBoolean(false);
        final AtomicInteger scoreUpdated = new AtomicInteger(0);
        final AtomicInteger levelUpdated = new AtomicInteger(0);
        
        game.setGameListener(new Game.GameListener() {
            @Override
            public void onBoardUpdated() {
                boardUpdated.set(true);
            }
            
            @Override
            public void onGameOver() {
                // Not testing game over in this test
            }
            
            @Override
            public void onScoreUpdated(int score) {
                scoreUpdated.set(score);
            }
            
            @Override
            public void onLevelUpdated(int level) {
                levelUpdated.set(level);
            }
        });
        
        // Trigger board update
        game.moveLeft();
        assertTrue(boardUpdated.get());
        
        // Reset flag
        boardUpdated.set(false);
        
        // Trigger another board update
        game.rotate();
        assertTrue(boardUpdated.get());
    }
    
    @Test
    public void testLevelUpdate() {
        // Start at level 1
        assertEquals(1, game.getLevel());
        
        // Update to 10 lines cleared (should be level 2)
        game.updateLevel(10);
        assertEquals(2, game.getLevel());
        
        // Update to 25 lines cleared (should be level 3)
        game.updateLevel(25);
        assertEquals(3, game.getLevel());
    }
}
