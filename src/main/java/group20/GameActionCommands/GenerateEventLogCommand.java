package group20.GameActionCommands;

import java.util.List;

import group20.EventLogging.CSVEventLogWriter;
import group20.EventLogging.EventLogBuilder;
import group20.EventLogging.EventLogEntry;
/**Generates the process mining log CSV, given a list of {@link Command}s and a gameID */
public class GenerateEventLogCommand extends Command {
    private CommandInvoker invoker;
    private int gameID;

    public GenerateEventLogCommand(CommandInvoker invoker, int gameID){
        this.invoker = invoker;
        this.gameID = gameID;
    };

    /**Creates a new {@link EventLogBuilder} to gather the required data from the {@link #commandHistory}, and then
     * creates a new {@link CSVEventLogWriter} to write the data to CSV.
     */
    public void execute(){
        createEventLogEntry();
        List<Command> commandHistory = invoker.getHistory();
        CSVEventLogWriter writer = new CSVEventLogWriter("game_event_log.csv");
        EventLogBuilder builder = new EventLogBuilder();
        writer.write(builder.buildEventLog(commandHistory, String.valueOf(this.gameID)));
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
