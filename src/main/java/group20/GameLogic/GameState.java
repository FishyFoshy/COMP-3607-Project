package group20.GameLogic;

import java.util.ArrayList;
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
    private boolean isOver;
    
    public GameState() {
        this.turns = new TreeMap<>();
        this.playerCount = 0;
        this.players = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.commandHistory = new ArrayList<>();
        this.isOver = false;
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

    public void setIsOver(boolean isOver){
        this.isOver = isOver;
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

    public Turn getCurrentTurn() {
        return currentTurn;
    }

    public List<Command> getCommandHistory() {
        return commandHistory;
    }

    public boolean isFinished() {
        for (Category category : categories) {
            if (category.getUnansweredQuestions().isEmpty()) {
                categories.remove(category);
            }
        }

        return categories.isEmpty();
    }

    public boolean getIsOver(){
        return this.isOver;
    }
}
