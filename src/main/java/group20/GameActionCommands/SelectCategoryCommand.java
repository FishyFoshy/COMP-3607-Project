package group20.GameActionCommands;

import group20.GameLogic.Category;
import group20.GameLogic.GameState;

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
    };
}
