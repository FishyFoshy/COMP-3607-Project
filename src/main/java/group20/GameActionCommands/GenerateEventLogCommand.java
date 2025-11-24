package group20.GameActionCommands;

import java.util.List;

import group20.EventLogging.CSVEventLogWriter;
import group20.EventLogging.EventLogBuilder;
import group20.EventLogging.EventLogEntry;
/**Generates the process mining log CSV, given a {@link #commandHistory} and {@link #gameID} */
public class GenerateEventLogCommand extends Command {
    private List<Command> commandHistory;
    private int gameID;

    public GenerateEventLogCommand(List<Command> commandHistory, int gameID){
        this.commandHistory = commandHistory;
        this.gameID = gameID;
    };

    /**Creates a new {@link EventLogBuilder} to gather the required data from the {@link #commandHistory}, and then
     * creates a new {@link CSVEventLogWriter} to write the data to CSV.
     */
    public void execute(){
        CSVEventLogWriter writer = new CSVEventLogWriter("sample_game_CSV.csv");
        EventLogBuilder builder = new EventLogBuilder();
        writer.write(builder.buildEventLog(commandHistory, String.valueOf(this.gameID)));
        createEventLogEntry();
    };

    public void createEventLogEntry(){
        EventLogEntry entry = new EventLogEntry();
        entry.setPlayerID("System");
        entry.setActivity("Generate Event Log");
        entry.setTimestamp(this.timestamp);
        entry.setResult("N/A");
        this.entry = entry;
    }
}
