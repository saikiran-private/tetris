package com.tetris.util;

import com.tetris.model.Game;
import com.tetris.model.Board;

/**
 * A simple demonstration of how to use the Game class programmatically.
 * This is not part of the web application but can be useful for testing
 * or for creating a standalone version of the game.
 */
public class GameDemo {
    
    public static void main(String[] args) {
        // Create a new game with a 10x20 board
        Game game = new Game(10, 20);
        
        // Set up a game listener to handle events
        game.setGameListener(new Game.GameListener() {
            @Override
            public void onBoardUpdated() {
                // Print the current board state
                printBoard(game.getBoard());
                System.out.println();
            }
            
            @Override
            public void onGameOver() {
                System.out.println("Game Over!");
                System.out.println("Final Score: " + game.getBoard().getScore());
            }
            
            @Override
            public void onScoreUpdated(int score) {
                System.out.println("Score: " + score);
            }
            
            @Override
            public void onLevelUpdated(int level) {
                System.out.println("Level: " + level);
            }
        });
        
        // Start the game
        game.start();
        
        // Simulate some game actions
        try {
            // Wait a bit for the game to initialize
            Thread.sleep(500);
            
            // Move left
            System.out.println("Moving left...");
            game.moveLeft();
            Thread.sleep(500);
            
            // Rotate
            System.out.println("Rotating...");
            game.rotate();
            Thread.sleep(500);
            
            // Move right
            System.out.println("Moving right...");
            game.moveRight();
            Thread.sleep(500);
            
            // Drop down
            System.out.println("Dropping down...");
            game.dropDown();
            Thread.sleep(500);
            
            // Pause the game
            System.out.println("Pausing game...");
            game.pause();
            Thread.sleep(1000);
            
            // Resume the game
            System.out.println("Resuming game...");
            game.resume();
            Thread.sleep(1000);
            
            // Let the game run for a bit
            Thread.sleep(5000);
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Shutdown the game
            game.shutdown();
        }
    }
    
    /**
     * Print the current state of the board to the console.
     * This is a simple ASCII representation of the board.
     */
    private static void printBoard(Board board) {
        int[][] grid = board.getGrid();
        
        // Print top border
        System.out.println("+" + "-".repeat(board.getWidth() * 2 + 1) + "+");
        
        // Print grid
        for (int row = 0; row < board.getHeight(); row++) {
            System.out.print("| ");
            for (int col = 0; col < board.getWidth(); col++) {
                if (grid[row][col] != 0) {
                    System.out.print("# ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println("|");
        }
        
        // Print bottom border
        System.out.println("+" + "-".repeat(board.getWidth() * 2 + 1) + "+");
    }
}
