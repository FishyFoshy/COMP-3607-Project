package group20.GameActionCommands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import group20.Exceptions.CommandExecutionException;
/**
 * Invoker for the Command design pattern implementation.
 */
public class CommandInvoker {
    /**
     * Command history is stored in this field. It eventually gets iterated through in {@link group20.EventLogging.EventLogBuilder} for process mining.
     */
    private List<Command> commandHistory = new ArrayList<>();
    private Command delayedCommand;

    public void executeCommand(Command command) throws CommandExecutionException {
        command.storeTimestamp();
        command.execute();
        commandHistory.add(command);
    }
    
    public List<Command> getHistory() {
        return Collections.unmodifiableList(commandHistory);
    }

    public void setDelayedCommand(Command command){
        this.delayedCommand = command;
        command.storeTimestamp();
        commandHistory.add(command);
    }

    public void executeDelayedCommand() throws CommandExecutionException {
        this.delayedCommand.execute();
    }
}
