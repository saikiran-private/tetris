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
    
    public Board getBoard() {
        return board;
    }
    
    public boolean isPaused() {
        return isPaused;
    }
    
    public int getLevel() {
        return level;
    }
    
    public void shutdown() {
        if (gameTimer != null) {
            gameTimer.cancel();
            gameTimer = null;
        }
    }
}
