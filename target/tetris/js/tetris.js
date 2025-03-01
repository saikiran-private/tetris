document.addEventListener('DOMContentLoaded', function() {
    // Game elements
    const gameBoard = document.getElementById('game-board');
    const scoreElement = document.getElementById('score');
    const levelElement = document.getElementById('level');
    const newGameBtn = document.getElementById('new-game-btn');
    const pauseBtn = document.getElementById('pause-btn');
    const gameOverOverlay = document.getElementById('game-over-overlay');
    const finalScoreElement = document.getElementById('final-score');
    const restartBtn = document.getElementById('restart-btn');
    const nextPieceContainer = document.getElementById('next-piece');
    
    // Game state
    let isPaused = false;
    let gameOver = false;
    let updateInterval;
    
    // Colors for blocks
    const colors = [
        '#00FFFF', // Cyan (I)
        '#0000FF', // Blue (J)
        '#FFA500', // Orange (L)
        '#FFFF00', // Yellow (O)
        '#00FF00', // Green (S)
        '#800080', // Purple (T)
        '#FF0000'  // Red (Z)
    ];
    
    // Initialize the game board
    function initializeBoard() {
        // Clear the game board
        gameBoard.innerHTML = '';
        
        // Create cells for the game board (10x20 grid)
        for (let row = 0; row < 20; row++) {
            for (let col = 0; col < 10; col++) {
                const cell = document.createElement('div');
                cell.className = 'game-cell';
                cell.id = `cell-${row}-${col}`;
                gameBoard.appendChild(cell);
            }
        }
    }
    
    // Update the game board based on the current state
    function updateBoard(grid) {
        for (let row = 0; row < 20; row++) {
            for (let col = 0; col < 10; col++) {
                const cell = document.getElementById(`cell-${row}-${col}`);
                const colorValue = grid[row][col];
                
                if (colorValue === 0) {
                    cell.style.backgroundColor = '#ecf0f1';
                } else {
                    // Convert the integer color to a hex string
                    const colorIndex = getColorIndex(colorValue);
                    cell.style.backgroundColor = colors[colorIndex] || '#333';
                }
            }
        }
    }
    
    // Helper function to get color index from color value
    function getColorIndex(colorValue) {
        // This is a simple mapping based on the color values in the Shape class
        // In a real implementation, you might want to send the actual color index from the server
        switch (colorValue) {
            case 0x00FFFF: return 0; // Cyan (I)
            case 0x0000FF: return 1; // Blue (J)
            case 0xFFA500: return 2; // Orange (L)
            case 0xFFFF00: return 3; // Yellow (O)
            case 0x00FF00: return 4; // Green (S)
            case 0x800080: return 5; // Purple (T)
            case 0xFF0000: return 6; // Red (Z)
            default: return 0;
        }
    }
    
    // Update the next piece display
    function updateNextPiece(nextShape) {
        // Clear the next piece container
        nextPieceContainer.innerHTML = '';
        
        if (!nextShape || nextShape.length === 0) return;
        
        // Find the dimensions of the next shape
        let minX = Number.MAX_VALUE;
        let maxX = Number.MIN_VALUE;
        let minY = Number.MAX_VALUE;
        let maxY = Number.MIN_VALUE;
        
        nextShape.forEach(block => {
            minX = Math.min(minX, block.x);
            maxX = Math.max(maxX, block.x);
            minY = Math.min(minY, block.y);
            maxY = Math.max(maxY, block.y);
        });
        
        const width = maxX - minX + 1;
        const height = maxY - minY + 1;
        
        // Create a mini grid for the next piece
        const miniGrid = document.createElement('div');
        miniGrid.className = 'mini-grid';
        miniGrid.style.display = 'grid';
        miniGrid.style.gridTemplateColumns = `repeat(${width}, 1fr)`;
        miniGrid.style.gridTemplateRows = `repeat(${height}, 1fr)`;
        miniGrid.style.gap = '2px';
        miniGrid.style.width = '100%';
        miniGrid.style.height = '100%';
        
        // Create empty cells for the mini grid
        for (let y = 0; y < height; y++) {
            for (let x = 0; x < width; x++) {
                const cell = document.createElement('div');
                cell.className = 'mini-cell';
                cell.style.backgroundColor = '#ecf0f1';
                miniGrid.appendChild(cell);
            }
        }
        
        // Fill in the cells for the next shape
        nextShape.forEach(block => {
            const x = block.x - minX;
            const y = block.y - minY;
            const index = y * width + x;
            const cells = miniGrid.querySelectorAll('.mini-cell');
            
            if (index >= 0 && index < cells.length) {
                const colorIndex = getColorIndex(block.color);
                cells[index].style.backgroundColor = colors[colorIndex] || '#333';
            }
        });
        
        nextPieceContainer.appendChild(miniGrid);
    }
    
    // Update the game state from the server
    function updateGameState() {
        if (gameOver) return;
        
        fetch('tetris?action=getState')
            .then(response => response.json())
            .then(data => {
                updateBoard(data.grid);
                scoreElement.textContent = data.score;
                levelElement.textContent = data.level;
                
                // Update the next piece if available
                if (data.nextShape) {
                    updateNextPiece(data.nextShape);
                }
                
                if (data.gameOver && !gameOver) {
                    gameOver = true;
                    showGameOver(data.score);
                }
            })
            .catch(error => console.error('Error updating game state:', error));
    }
    
    // Start a new game
    function startNewGame() {
        fetch('tetris?action=newGame')
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    gameOver = false;
                    isPaused = false;
                    pauseBtn.textContent = 'Pause';
                    gameOverOverlay.style.display = 'none';
                    
                    // Start the update interval
                    if (updateInterval) {
                        clearInterval(updateInterval);
                    }
                    updateInterval = setInterval(updateGameState, 100);
                }
            })
            .catch(error => console.error('Error starting new game:', error));
    }
    
    // Toggle pause state
    function togglePause() {
        if (gameOver) return;
        
        const action = isPaused ? 'resume' : 'pause';
        
        fetch(`tetris?action=${action}`)
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    isPaused = !isPaused;
                    pauseBtn.textContent = isPaused ? 'Resume' : 'Pause';
                }
            })
            .catch(error => console.error(`Error ${action}ing game:`, error));
    }
    
    // Show game over overlay
    function showGameOver(score) {
        finalScoreElement.textContent = score;
        gameOverOverlay.style.display = 'flex';
        
        if (updateInterval) {
            clearInterval(updateInterval);
            updateInterval = null;
        }
    }
    
    // Handle keyboard controls
    function handleKeyDown(event) {
        if (gameOver || isPaused) return;
        
        let action = '';
        
        switch (event.key) {
            case 'ArrowLeft':
                action = 'moveLeft';
                break;
            case 'ArrowRight':
                action = 'moveRight';
                break;
            case 'ArrowUp':
                action = 'rotate';
                break;
            case ' ':
                action = 'dropDown';
                break;
            case 'p':
            case 'P':
                togglePause();
                return;
            default:
                return;
        }
        
        if (action) {
            event.preventDefault();
            
            fetch('tetris', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `action=${action}`
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    // Update the game state immediately after an action
                    updateGameState();
                }
            })
            .catch(error => console.error(`Error performing action ${action}:`, error));
        }
    }
    
    // Initialize the game
    function init() {
        initializeBoard();
        
        // Set up event listeners
        newGameBtn.addEventListener('click', startNewGame);
        pauseBtn.addEventListener('click', togglePause);
        restartBtn.addEventListener('click', startNewGame);
        document.addEventListener('keydown', handleKeyDown);
        
        // Start the update interval
        updateInterval = setInterval(updateGameState, 100);
        
        // Initial update
        updateGameState();
    }
    
    // Start the game
    init();
});