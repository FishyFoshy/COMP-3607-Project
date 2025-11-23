package group20.GameLogic;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import group20.GameActionCommands.Command;

public class GameState {
    private Map<Integer, Turn> turns;
    private int playerCount;
    private List<Player> players;
    private List<Category> categories;
    private List<Command> commandHistory;
    private Turn currentTurn;
    
    public GameState() {
        this.turns = new TreeMap<>();
        this.playerCount = 0;
        this.players = new java.util.ArrayList<>();
        this.categories = new java.util.ArrayList<>();
        this.commandHistory = new java.util.ArrayList<>();
    }

    public void setPlayerCount(int count) {
        this.playerCount = count;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void startNewTurn(Player player) {
        currentTurn = new Turn(player);
        turns.put(currentTurn.getTurnNumber(), currentTurn);
    }

    public void recordCommand(Command command) {
        commandHistory.add(command);
    }

    public Map<Integer, Turn> getTurns() {
        return turns;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Command> getCommandHistory() {
        return commandHistory;
    }
}
