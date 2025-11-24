package group20.GameActionCommands;

import java.util.Map;

import group20.EventLogging.EventLogEntry;
import group20.FileParsing.AbstractQuestionParser;
import group20.GameLogic.Category;
import group20.GameLogic.GameState;

public class LoadFileCommand extends Command {
    private final AbstractQuestionParser parser;
    private final String filePath;
    private boolean success;

    public LoadFileCommand(GameState state, AbstractQuestionParser parser, String filePath){
        this.state = state;
        this.parser = parser;
        this.filePath = filePath;
        this.success = false;
    }
    
    public void execute() throws InvalidCommandException {
        if(this.filePath == null || this.filePath.isEmpty()){
            throw new InvalidCommandException("File path not specified.");
        }

        try {
            Map<String, Category> categoriesMap = parser.run(this.filePath);
            for(Category category : categoriesMap.values()){
                this.state.addCategory(category);
            }
            this.success = true;
        } catch (Exception e) {
            System.out.println("Caught " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
        createEventLogEntry();
    };

    public void createEventLogEntry(){
        EventLogEntry entry = new EventLogEntry();
        entry.setPlayerID("System");
        entry.setActivity("Start Game");
        entry.setTimestamp(this.timestamp);
        if(this.success == true) {
            entry.setResult("Success");
        } else {
            entry.setResult("Failed");
        }
        this.entry = entry;
    }
}
