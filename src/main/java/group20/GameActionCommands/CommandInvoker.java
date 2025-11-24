package group20.GameActionCommands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Invoker for the Command design pattern implementation.
 */
public class CommandInvoker {
    /**
     * Command history is stored in this field. It eventually gets iterated through in {@link EventLogBuilder} for process mining.
     */
    private List<Command> commandHistory = new ArrayList<>();
    
    public void executeCommand(Command command) throws InvalidCommandException {
        command.storeTimestamp();
        command.execute();
        commandHistory.add(command);
    }
    
    public List<Command> getHistory() {
        return Collections.unmodifiableList(commandHistory);
    }
}
