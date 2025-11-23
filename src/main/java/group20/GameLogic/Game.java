package group20.GameLogic;

import java.util.HashMap;
import java.util.Map;
import group20.GameActionCommands.Command;

public class Game {
    private int id;
    private GameState gameState;
    private Map<String, Command> commands;

    static private int idCounter = 0;

    public Game() {
        this.id = idCounter++;
        this.gameState = new GameState();
        this.commands = new HashMap<>();
    }

    public void startGame() {
        // Implementation for starting the game
    }

    public void playTurn() {
        // Implementation for playing a turn
    }
    
    public void endGame() {
        // Implementation for ending the game
    }
}
