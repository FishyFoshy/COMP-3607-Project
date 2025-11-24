package group20.GameActionCommands;

import group20.EventLogging.EventLogEntry;
import group20.GameLogic.GameState;
/**Sets the {@link GameState} to indicate the game is over, by calling {@link GameState#setIsOver} */
public class ExitGameCommand extends Command {
    public ExitGameCommand(GameState state){
        super(state);
    };

    public void execute(){
        this.state.setIsOver(true);
        createEventLogEntry();
    };

    protected void createEventLogEntry(){
        EventLogEntry entry = new EventLogEntry();
        entry.setPlayerID("System");
        entry.setActivity("Exit Game");
        entry.setTimestamp(this.timestamp);
        this.entry = entry;
    }
}
