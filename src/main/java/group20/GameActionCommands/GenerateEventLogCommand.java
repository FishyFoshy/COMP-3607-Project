package group20.GameActionCommands;

import java.io.IOException;
import java.util.List;

import group20.EventLogging.CSVEventLogWriter;
import group20.EventLogging.EventLogBuilder;
import group20.EventLogging.EventLogEntry;
import group20.Exceptions.CommandExecutionException;
/**Generates the process mining log CSV, given a list of {@link Command}s and a gameID */
public class GenerateEventLogCommand extends Command {
    private CommandInvoker invoker;
    private int gameID;
    private String filename;

    public GenerateEventLogCommand(CommandInvoker invoker, int gameID, String filename){
        this.invoker = invoker;
        this.gameID = gameID;
        this.filename = filename;
    };

    /**Creates a new {@link EventLogBuilder} to gather the required data from the commands in the {@link #invoker}'s command history, and then
     * creates a new {@link CSVEventLogWriter} to write the data to CSV.
     */
    public void execute() throws CommandExecutionException {
        createEventLogEntry();
        List<Command> commandHistory = invoker.getHistory();
        CSVEventLogWriter writer = new CSVEventLogWriter(filename);
        EventLogBuilder builder = new EventLogBuilder();

        try {
            writer.write(builder.buildEventLog(commandHistory, String.valueOf(this.gameID)));
        } catch (IOException e) {
            throw new CommandExecutionException("Failed to generate event log", e);
        }
        
    };

    protected void createEventLogEntry(){
        EventLogEntry entry = new EventLogEntry();
        entry.setPlayerID("System");
        entry.setActivity("Generate Event Log");
        entry.setTimestamp(this.timestamp);
        entry.setResult("N/A");
        this.entry = entry;
    }
}
