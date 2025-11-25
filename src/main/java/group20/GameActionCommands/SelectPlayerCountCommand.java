package group20.GameActionCommands;

import group20.EventLogging.EventLogEntry;
import group20.Exceptions.CommandExecutionException;
import group20.GameLogic.GameState;
/**Sets the player count for the {@link GameState}, verifying it is between 1-4. */
public class SelectPlayerCountCommand extends Command {
    private final int playerCount;

    public SelectPlayerCountCommand(GameState state, int playerCount){
        super(state);
        this.playerCount = playerCount;
    }

    public void execute() throws CommandExecutionException{
        state.setPlayerCount(playerCount); 
        createEventLogEntry();
    }

    protected void createEventLogEntry(){
        EventLogEntry entry = new EventLogEntry();
        entry.setPlayerID("System");
        entry.setActivity("Select Player Count");
        entry.setTimestamp(this.timestamp);
        entry.setAnswerGiven(String.valueOf(this.playerCount));
        entry.setResult("N/A");
        this.entry = entry;
    }
}
