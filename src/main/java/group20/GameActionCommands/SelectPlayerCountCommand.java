package group20.GameActionCommands;

import group20.GameLogic.GameState;

public class SelectPlayerCountCommand extends Command {
    private final int playerCount;

    public SelectPlayerCountCommand(GameState state, int playerCount){
        super(state);
        this.playerCount = playerCount;
    }

    public void execute() throws InvalidCommandException{
        if(playerCount < 1 || playerCount > 4){
            throw new InvalidCommandException("Player count must be between 1-4");
        }

        state.setPlayerCount(playerCount); 
    }
}
