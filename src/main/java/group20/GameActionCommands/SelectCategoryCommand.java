package group20.GameActionCommands;

import group20.EventLogging.EventLogEntry;
import group20.GameLogic.Category;
import group20.GameLogic.GameState;
import group20.GameLogic.Player;

public class SelectCategoryCommand extends Command {
    private final String selectedCategory;

    public SelectCategoryCommand(GameState state, String selectedCategory){
        super(state);
        this.selectedCategory = selectedCategory;
    }

    public void execute() throws InvalidCommandException{
        boolean found = false;
        for(Category category : state.getCategories()){
            if(category.getName().equalsIgnoreCase(selectedCategory)){
                state.getCurrentTurn().setTurnCategory(category);
                found = true;
                break;
            }
        }

        if(!found){
            throw new InvalidCommandException("Invalid category:" + selectedCategory);
        }
        createEventLogEntry();
    };

    public void createEventLogEntry(){
        EventLogEntry entry = new EventLogEntry();
        Player player = this.state.getCurrentTurn().getTurnPlayer();
        entry.setPlayerID(player.getName());
        entry.setActivity("Select Category");
        entry.setTimestamp(this.timestamp);
        entry.setCategory(this.selectedCategory);
        entry.setScoreAfterPlay(player.getScore());
        this.entry = entry;
    }
}
