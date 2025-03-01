package com.tetris.controller;

import com.tetris.model.Game;
import com.tetris.model.Shape;
import com.tetris.model.Block;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/tetris")
public class TetrisServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private static final String GAME_ATTRIBUTE = "tetrisGame";
    private final Gson gson = new Gson();
    
    // Method to get game state including next shape
    private Map<String, Object> getGameState(Game game) {
        Map<String, Object> gameState = new HashMap<>();
        
        // Add basic game state
        gameState.put("grid", game.getBoard().getGrid());
        gameState.put("score", game.getBoard().getScore());
        gameState.put("level", game.getLevel());
        gameState.put("gameOver", game.isGameOver());
        
        // Add the next shape if available
        Shape nextShape = game.getNextShape();
        if (nextShape != null) {
            List<Map<String, Object>> nextShapeBlocks = new ArrayList<>();
            for (Block block : nextShape.getBlocks()) {
                Map<String, Object> blockData = new HashMap<>();
                blockData.put("x", block.getX());
                blockData.put("y", block.getY());
                blockData.put("color", block.getColor());
                nextShapeBlocks.add(blockData);
            }
            gameState.put("nextShape", nextShapeBlocks);
        }
        
        return gameState;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        Game game = (Game) session.getAttribute(GAME_ATTRIBUTE);
        
        if (game == null) {
            // Create a new game if none exists
            game = new Game(10, 20);
            game.start();
            session.setAttribute(GAME_ATTRIBUTE, game);
        }
        
        String action = request.getParameter("action");
        if (action == null) {
            // Forward to the game page
            request.getRequestDispatcher("/WEB-INF/tetris.jsp").forward(request, response);
            return;
        }
        
        // Handle AJAX requests
        response.setContentType("application/json");
        Map<String, Object> responseData;
        
        switch (action) {
            case "getState":
                responseData = getGameState(game);
                break;
                
            case "newGame":
                game.restart();
                responseData = new HashMap<>();
                responseData.put("success", true);
                break;
                
            case "pause":
                game.pause();
                responseData = new HashMap<>();
                responseData.put("success", true);
                break;
                
            case "resume":
                game.resume();
                responseData = new HashMap<>();
                responseData.put("success", true);
                break;
                
            default:
                responseData = new HashMap<>();
                responseData.put("error", "Unknown action");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        
        response.getWriter().write(gson.toJson(responseData));
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session expired");
            return;
        }
        
        Game game = (Game) session.getAttribute(GAME_ATTRIBUTE);
        if (game == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No game in progress");
            return;
        }
        
        String action = request.getParameter("action");
        response.setContentType("application/json");
        Map<String, Object> responseData = new HashMap<>();
        
        switch (action) {
            case "moveLeft":
                game.moveLeft();
                responseData.put("success", true);
                break;
                
            case "moveRight":
                game.moveRight();
                responseData.put("success", true);
                break;
                
            case "rotate":
                game.rotate();
                responseData.put("success", true);
                break;
                
            case "dropDown":
                game.dropDown();
                responseData.put("success", true);
                break;
                
            default:
                responseData.put("error", "Unknown action");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        
        response.getWriter().write(gson.toJson(responseData));
    }
    
    @Override
    public void destroy() {
        // Clean up any resources
        super.destroy();
    }
}