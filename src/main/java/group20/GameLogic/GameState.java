package group20.GameLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**Holds all the info required for the running of a {@link Game}. Modified by {@link Command}s */
public class GameState {
    /**The history of {@link Turn}s, with each turn mapped to its turn number */
    private Map<Integer, Turn> turns;

    private int playerCount;

    /**The list of {@link Player} objects for the current {@link Game} */
    private List<Player> players;

    /**The list of {@link Category} objects being used for the current {@link Game} */
    private List<Category> categories;
    private Turn currentTurn;

    /**Boolan indicating whether the {@link Game} is over or not */
    private boolean isOver;
    
    public GameState() {
        this.turns = new TreeMap<>();
        this.playerCount = 0;
        this.players = new ArrayList<>();
        this.categories = new ArrayList<>();
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

    /**@param player The {@link Player} for the new turn
     * Creates a new {@link Turn} object with the given {@link Player}, updates the turn history({@link #turns} and {@link #currentTurn})
     */
    public void startNewTurn(Player player) {
        currentTurn = new Turn(player, turns.size() + 1);
    }

    public void endTurn(){
        turns.put(currentTurn.getTurnNumber(), currentTurn);
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

    /**Checks if all {@link #categories} are empty, i.e., there are no more available {@link Question}s to be answered */
    public boolean isFinished() {
        boolean allEmpty = true;
        for (Category category : categories) {
            if (!category.getUnansweredQuestions().isEmpty()) {
                allEmpty = false;
                break;
            }
        }

        return allEmpty;
    }

    public boolean getIsOver(){
        return this.isOver;
    }
}