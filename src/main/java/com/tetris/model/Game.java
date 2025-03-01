package com.tetris.model;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Manages the Tetris game state and logic
 */
public class Game {
    private Board board;
    private Timer gameTimer;
    private boolean isPaused;
    private int level;
    private int linesCleared;
    private GameListener gameListener;
    private Shape nextShape;
    private Shape currentShape;  // Add this field
    private boolean gameOver;    // Add this field
    
    // Interface for game event listeners
    public interface GameListener {
        void onBoardUpdated();
        void onGameOver();
        void onScoreUpdated(int score);
        void onLevelUpdated(int level);
    }
    
    public Game(int width, int height) {
        this.board = new Board(width, height);
        this.isPaused = false;
        this.level = 1;
        this.linesCleared = 0;
        this.gameOver = false;   // Initialize gameOver
        
        // Initialize shapes
        this.nextShape = Shape.createRandomShape();
        createNewShape();
    }
    
    public void setGameListener(GameListener listener) {
        this.gameListener = listener;
    }
    
    public void start() {
        if (gameTimer != null) {
            gameTimer.cancel();
        }
        
        gameTimer = new Timer();
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isPaused && !board.isGameOver()) {
                    tick();
                }
            }
        }, 0, calculateDropInterval());
    }
    
    private long calculateDropInterval() {
        // Drop interval decreases as level increases (game gets faster)
        return Math.max(100, 1000 - ((level - 1) * 100));
    }
    
    private void tick() {
        boolean moved = board.moveShapeDown();
        
        if (gameListener != null) {
            gameListener.onBoardUpdated();
            
            if (board.isGameOver()) {
                gameTimer.cancel();
                gameListener.onGameOver();
            }
        }
    }
    
    public void moveLeft() {
        if (!isPaused && !board.isGameOver()) {
            board.moveShapeLeft();
            if (gameListener != null) {
                gameListener.onBoardUpdated();
            }
        }
    }
    
    public void moveRight() {
        if (!isPaused && !board.isGameOver()) {
            board.moveShapeRight();
            if (gameListener != null) {
                gameListener.onBoardUpdated();
            }
        }
    }
    
    public void rotate() {
        if (!isPaused && !board.isGameOver()) {
            board.rotateShape();
            if (gameListener != null) {
                gameListener.onBoardUpdated();
            }
        }
    }
    
    public void dropDown() {
        if (!isPaused && !board.isGameOver()) {
            while (board.moveShapeDown()) {
                // Keep moving down until it can't move anymore
            }
            if (gameListener != null) {
                gameListener.onBoardUpdated();
                gameListener.onScoreUpdated(board.getScore());
            }
        }
    }
    
    public void pause() {
        isPaused = true;
    }
    
    public void resume() {
        isPaused = false;
    }
    
    public void restart() {
        board = new Board(board.getWidth(), board.getHeight());
        isPaused = false;
        level = 1;
        linesCleared = 0;
        gameOver = false;
        
        // Initialize shapes
        this.nextShape = Shape.createRandomShape();
        createNewShape();
        
        if (gameTimer != null) {
            gameTimer.cancel();
        }
        
        start();
        
        if (gameListener != null) {
            gameListener.onBoardUpdated();
            gameListener.onScoreUpdated(0);
            gameListener.onLevelUpdated(1);
        }
    }
    
    public void updateLevel(int newLinesCleared) {
        int oldLinesCleared = this.linesCleared;
        this.linesCleared = newLinesCleared;
        
        // Level up every 10 lines
        int newLevel = (newLinesCleared / 10) + 1;
        if (newLevel > level) {
            level = newLevel;
            
            // Restart timer with new interval
            if (gameTimer != null) {
                gameTimer.cancel();
            }
            start();
            
            if (gameListener != null) {
                gameListener.onLevelUpdated(level);
            }
        }
    }
    
    // Modify the method that creates a new shape
    private void createNewShape() {
        // Move the next shape to be the current shape
        currentShape = nextShape;
        
        // Create a new next shape
        nextShape = Shape.createRandomShape();
        
        // Position the current shape at the top center of the board
        currentShape.setPosition(board.getWidth() / 2 - currentShape.getWidth() / 2, 0);
        
        // Set the current shape on the board
        board.setCurrentShape(currentShape);
        
        // Check if game is over
        if (!board.isValidPosition(currentShape)) {
            gameOver = true;
        }
    }

    // Add a getter for the next shape
    public Shape getNextShape() {
        return nextShape;
    }
    
    // Add a getter for the current shape
    public Shape getCurrentShape() {
        return currentShape;
    }

    public Board getBoard() {
        return board;
    }
    
    public boolean isPaused() {
        return isPaused;
    }
    
    public int getLevel() {
        return level;
    }
    
    public boolean isGameOver() {
        return gameOver || board.isGameOver();
    }
    
    public void shutdown() {
        if (gameTimer != null) {
            gameTimer.cancel();
            gameTimer = null;
        }
    }
}