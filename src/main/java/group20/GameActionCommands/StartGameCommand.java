package group20.GameActionCommands;

import group20.EventLogging.EventLogEntry;
import group20.GameLogic.GameState;

public class StartGameCommand extends Command {
    public StartGameCommand(GameState state){
        super(state);
    };

    public void execute(){
        this.state.setIsOver(false);
        createEventLogEntry();
    };

    public void createEventLogEntry(){
        EventLogEntry entry = new EventLogEntry();
        entry.setPlayerID("System");
        entry.setActivity("Load File");
        entry.setTimestamp(this.timestamp);
        this.entry = entry;
    }
}
