package group20.GameActionCommands;

import java.util.Map;

import group20.EventLogging.EventLogEntry;
import group20.Exceptions.CommandExecutionException;
import group20.FileParsing.*;
import group20.GameLogic.Category;
import group20.GameLogic.GameState;
/** Loads question data into {@link GameState} */
public class LoadFileCommand extends Command {
    private final String filePath;
    private boolean success;
    private AbstractQuestionParser questionParser;
    
    public LoadFileCommand(GameState state, String filePath, AbstractQuestionParser questionParser){
        this.state = state;
        this.filePath = filePath;
        this.success = false;
        this.questionParser = questionParser;
    }

    /** Loads the given file data into {@link GameState} using an {@link AbstractQuestionParser} */
    public void execute() throws CommandExecutionException {
        try {
            Map<String, Category> categoriesMap = this.questionParser.run(this.filePath);
            for(Category category : categoriesMap.values()){
                this.state.addCategory(category);
            }
            this.success = true;
            
        } catch (Exception e) {
            throw new CommandExecutionException("Error parsing file: " + e.getMessage(), e);
        } finally {
            createEventLogEntry();
        }
    };

    protected void createEventLogEntry(){
        EventLogEntry entry = new EventLogEntry();
        entry.setPlayerID("System");
        entry.setActivity("Load File");
        entry.setTimestamp(this.timestamp);
        entry.setResult(this.success ? "Success" : "Failed");
        this.entry = entry;
    }
}
