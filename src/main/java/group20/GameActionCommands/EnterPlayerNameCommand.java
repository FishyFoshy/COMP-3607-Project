package group20.GameActionCommands;

import group20.EventLogging.EventLogEntry;
import group20.GameLogic.GameState;
import group20.GameLogic.Player;
/** This Command creates a new {@link Player} given a player name.*/
public class EnterPlayerNameCommand extends Command {
    private final String name;

    public EnterPlayerNameCommand(GameState state, String name){
        super(state);
        this.name = name;
    };

    public void execute(){
        Player player = new Player(name);
        state.addPlayer(player);
        createEventLogEntry();
    };

    protected void createEventLogEntry(){
        EventLogEntry entry = new EventLogEntry();
        entry.setPlayerID(this.name);
        entry.setActivity("Enter Player Name");
        entry.setTimestamp(this.timestamp);
        entry.setAnswerGiven(this.name);
        entry.setResult("N/A");
        this.entry = entry;
    }
}
