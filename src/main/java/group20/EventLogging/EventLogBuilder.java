package group20.EventLogging;

import java.util.ArrayList;
import java.util.List;

import group20.GameActionCommands.Command;

public class EventLogBuilder {
    public EventLogBuilder(){};

    public List<EventLogEntry> buildEventLog(List<Command> commandHistory, String gameID){
        List<EventLogEntry> rows = new ArrayList<>();
        for(Command command : commandHistory) {
            EventLogEntry entry = command.getEntry();
            entry.setCaseID(gameID);
            rows.add(entry);
        }

        return rows;
    }
}
