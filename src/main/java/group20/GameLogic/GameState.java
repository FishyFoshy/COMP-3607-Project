package group20.GameLogic;

import java.util.List;
import java.util.TreeMap;

public class GameState {
    private TreeMap<Integer, Turn> turns;
    private List<Player> players;
    private List<Category> categories;
    private List<Command> commandHistory;
    private Turn currentTurn;
    
    public GameState() {
        this.turns = new TreeMap<>();
        this.players = new java.util.ArrayList<>();
        this.categories = new java.util.ArrayList<>();
        this.commandHistory = new java.util.ArrayList<>();
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

    public TreeMap<Integer, Turn> getTurns() {
        return turns;
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
