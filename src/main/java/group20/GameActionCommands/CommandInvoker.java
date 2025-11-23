package group20.GameActionCommands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandInvoker {
    private List<Command> commandHistory = new ArrayList<>();
    
    public void executeCommand(Command command) throws InvalidCommandException{
        command.execute(); //Must handle InvalidCommandException in GameController
        command.storeTimestamp();
        commandHistory.add(command);
    }
    
    public List<Command> getHistory() {
        return Collections.unmodifiableList(commandHistory);
    }
}
