<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tetris Game</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tetris.css">
</head>
<body>
    <div class="game-container">
        <div class="game-header">
            <h1>Tetris</h1>
            <div class="game-info">
                <div class="score-container">
                    <p>Score: <span id="score">0</span></p>
                </div>
                <div class="level-container">
                    <p>Level: <span id="level">1</span></p>
                </div>
            </div>
            <div class="game-controls">
                <button id="new-game-btn">New Game</button>
                <button id="pause-btn">Pause</button>
            </div>
        </div>
        
        <div class="game-area">
            <div class="game-board" id="game-board">
                <!-- Game board will be rendered here by JavaScript -->
            </div>
            
            <div class="game-sidebar">
                <div class="next-piece-container">
                    <h3>Next Piece</h3>
                    <div class="next-piece" id="next-piece">
                        <!-- Next piece will be rendered here by JavaScript -->
                    </div>
                </div>
                
                <div class="instructions">
                    <h3>Controls</h3>
                    <ul>
                        <li>Left Arrow: Move Left</li>
                        <li>Right Arrow: Move Right</li>
                        <li>Up Arrow: Rotate</li>
                        <li>Down Arrow: Move Down</li>
                        <li>Space: Drop</li>
                        <li>P: Pause/Resume</li>
                    </ul>
                </div>
            </div>
        </div>
        
        <div class="game-over-overlay" id="game-over-overlay">
            <div class="game-over-message">
                <h2>Game Over</h2>
                <p>Your score: <span id="final-score">0</span></p>
                <button id="restart-btn">Play Again</button>
            </div>
        </div>
    </div>
    
    <script src="${pageContext.request.contextPath}/js/tetris.js"></script>
</body>
</html>
