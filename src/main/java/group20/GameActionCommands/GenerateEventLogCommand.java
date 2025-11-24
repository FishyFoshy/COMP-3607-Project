package group20.GameActionCommands;

import java.util.List;

import group20.EventLogging.CSVEventLogWriter;
import group20.EventLogging.EventLogBuilder;
import group20.EventLogging.EventLogEntry;

public class GenerateEventLogCommand extends Command {
    private List<Command> commandHistory;
    private int gameID;

    public GenerateEventLogCommand(List<Command> commandHistory, int gameID){
        this.commandHistory = commandHistory;
        this.gameID = gameID;
    };

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
